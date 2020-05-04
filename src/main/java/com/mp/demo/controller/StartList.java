package com.mp.demo.controller;

import com.mp.demo.entity.UserInfoEntity;
import com.mp.demo.serivce.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author ycn
 * 读出表中的数据返回到网页
 */
//解决跨域
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StartList {
    private static final Logger LOG = LoggerFactory.getLogger(StartList.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/startlist", method = RequestMethod.GET)
    public String getStartList() throws IOException {
        StringBuffer sBuffer = new StringBuffer(2096);
        Properties pro = new Properties();
        //从配置文件中读入表
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                StandardCharsets.UTF_8);
        LOG.info("设备表路径{}/fil.properties", System.getProperty("user.dir"));
        pro.load(isr);
        int endNum = Integer.parseInt(pro.getProperty("endnum"));
        //从数据库中读入表
        List<UserInfoEntity> userInfoEntityList = userInfoService.list();
        LOG.info("读入文件的列数:{}", endNum);
        boolean isno;
        String telnetString;
        for (int i = 0; i <  endNum; i++) {
            //拆分一个数组与userInfoEntityList比对
            List<String> userIdList = Arrays.asList(pro.getProperty(String.valueOf(i)).split(","));
            //查询结果表循环
            isno = true;
            for (int j = 0; j < userInfoEntityList.size(); j++) {
                //数据库中的路口名不足2位前加0
                String noJuncStr=userInfoEntityList.get(j).getNOJUNC().toString();
                if (noJuncStr.length()<2){
                    noJuncStr="0"+noJuncStr;
                }
                //数据库的中区号+路口名=查表用的ID
                String addstr = "0" + userInfoEntityList.get(j).getNOAREA().toString() + noJuncStr;

                if (userIdList.get(0).equals(addstr)) {
                    userIdList.set(10,userInfoEntityList.get(j).getCTRMODE().toString());
                    userIdList.set(11,userInfoEntityList.get(j).getDATETIME().toString());
                    int ipListLength = userIdList.toString().length() - 1;
                    telnetString =i + "," + userIdList.toString().substring(1, ipListLength) + ";";
                    sBuffer.append(telnetString);
                    LOG.info("信号机更新机况和机时:{}",telnetString);
                    isno = false;
                    break;
                }
            }
            if (isno) {
                telnetString = i + "," + pro.getProperty(String.valueOf(i)) + ";";
                sBuffer.append(telnetString);
                LOG.info("非信号机无机况和机时的更新:{}",telnetString);
            }

        }
        return sBuffer.toString();
    }
}
