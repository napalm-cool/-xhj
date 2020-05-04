package com.mp.demo.controller;

import com.mp.demo.TelnetClient1;
import com.mp.demo.entity.UserInfoEntity;
import com.mp.demo.serivce.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * @author ycn
 * 网页发送的数据给SOCK测试，返回最新的测试结果，有普通和重启两种
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/hellostate", method = RequestMethod.GET)
    public String helloWorld(HttpServletRequest request) throws IOException {
        String userId = request.getParameter("stateId");
        String isReboot = request.getParameter("reboot");
        LOG.info("#####################Get数据{}==>开始查询######################", userId);
        StringBuilder stateReturn = new StringBuilder();
        String telnetReturn = "ok";
        String telnetRecode = "ok";
        //默认端口号23
        int telnetPort = 23;
        //从数据库中读入表
        List<UserInfoEntity> userInfoEntityList = userInfoService.list();
        TelnetClient1 tc = new TelnetClient1();
        Properties pro = new Properties();
        //从配置文件中读入IP
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                StandardCharsets.UTF_8);
        LOG.info("读取设备表路径{}/fil.properties", System.getProperty("user.dir"));
        pro.load(isr);
        //网页发送来的路口号组按，号分割
        List<String> userIdList = Arrays.asList(userId.split(","));
        for (int i = 0; i < userIdList.size(); i++) {
            LOG.info("++++++++++++++++测试选择的设备++++++++++++++++++++++");
            //按顺序取出数组中的字符串，作为Key值到fil.properties中取对应值
            String listStr = pro.getProperty(userIdList.get(i));
            LOG.info("{}读取值:{}", userIdList.get(i), listStr);
            //按，号分割，1是Id点号,2是点位名
            List<String> ipList = Arrays.asList(listStr.split(","));
            String telnetIP = ipList.get(5).trim();
            if (!"".equals(ipList.get(6).trim())) {
                telnetPort = Integer.parseInt(ipList.get(6).trim());
            }
            int ne4110Reboot = Integer.parseInt(isReboot);

            if ((telnetIP != null && telnetIP.length() != 0)) {
                telnetReturn = tc.telnetClientStart(telnetIP, telnetPort, ne4110Reboot);
                telnetRecode = telnetReturn;
                LOG.info("{}测试结果:{}", i, telnetReturn);
                if (telnetReturn.contains("Connection timed out") || telnetReturn.contains("connect timed out")) {
                    telnetRecode = "超时";
                }
                if (telnetReturn.contains("Read timed out")) {
                    telnetRecode = "无字";
                }
                if (telnetReturn.contains("4110S")) {
                    telnetRecode = "4110S";
                }
                if (telnetReturn.contains("No route to host")) {
                    telnetRecode = "断网";
                }
                LOG.info("{}==>{}状态:{}", i, telnetIP, telnetRecode);
                ipList.set(3, telnetRecode);
                //写入时间
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                ipList.set(4, df.format(day));
                //比对数据库
                for (UserInfoEntity userInfoEntity : userInfoEntityList) {
                    //数据库中的路口名不足2位前加0
                    String noJuncStr = userInfoEntity.getNOJUNC().toString();
                    if (noJuncStr.length() < 2) {
                        noJuncStr = "0" + noJuncStr;
                    }
                    //数据库的中区号+路口名=查表用的ID
                    String addstr = "0" + userInfoEntity.getNOAREA().toString() + noJuncStr;
                    //在数据库中找到
                    if (addstr.equals(ipList.get(0))) {
                        ipList.set(10, userInfoEntity.getCTRMODE().toString());
                        ipList.set(11, userInfoEntity.getDATETIME().toString());
                        LOG.info("查库后序号:{}的信号机路口号:{}==>状态:{},时间:{}", i, ipList.get(0),ipList.get(10),ipList.get(11));
                        break;
                    }
                }
                //去除空格
                for (int j = 0; j < ipList.size(); j++) {
                    ipList.set(j, ipList.get(j).trim());
                }
                //telnet 结果写入,去掉最前的[和最后的]
                int ipListLength = ipList.toString().length() - 1;
                pro.setProperty(userIdList.get(i), ipList.toString().substring(1, ipListLength));

                LOG.info("写入设备表路径{}/fil.properties", System.getProperty("user.dir"));
                LOG.info("{}写入值:{}", userIdList.get(i), ipList.toString().substring(1, ipListLength));
                //加入key返回
                stateReturn.append(userIdList.get(i)).append(",").append(ipList.toString().substring(1, ipListLength)).append(";");

                File fi = new File(System.getProperty("user.dir") + "/fil.properties");
                pro.store(new PrintStream(fi), "utf-8");
            }
        }
        LOG.info("测试返回值:{}", stateReturn.toString());
        return stateReturn.toString();
    }
}