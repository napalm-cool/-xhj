package com.mp.demo;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ycn
 */
public class Mytask extends TimerTask {

    private static final Logger LOG = LoggerFactory.getLogger(Mytask.class);

    @SneakyThrows
    @Override
    public void run() {
        LOG.info("*******新的轮询******");
        String telnetReturn = "ok";
        String telnetRecode = "ok";
        int telnetPort = 23;
        List<String> ipList;
        TelnetClient1 tc = new TelnetClient1();
        Properties pro = new Properties();
        //从配置文件中读入IP
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                StandardCharsets.UTF_8);
        LOG.info("读取设备表路径{}/fil.properties", System.getProperty("user.dir"));
        pro.load(isr);
        int endNum = Integer.parseInt(pro.getProperty("endnum"));
        String[][] recode34 = new String[endNum][2];
        for (int i = 0; i < endNum; i++) {
            LOG.info("=======================测试设备=======================");
            //读取字符串数组
            String listStr = pro.getProperty(String.valueOf(i));
            if (listStr != null) {
                LOG.info("读{}取值:{}", i, listStr);
                //按，号分割
                ipList = Arrays.asList(listStr.split(","));
                String telnetIP = ipList.get(5).trim();
                if (!"".equals(ipList.get(6).trim())) {
                    telnetPort = Integer.parseInt(ipList.get(6).trim());
                }

                int ne4110Reboot = Integer.parseInt(ipList.get(9).trim());

                //>>>>>>>>>>>>>>访问设备
                telnetReturn = tc.telnetClientStart(telnetIP, telnetPort, ne4110Reboot);
                telnetRecode = telnetReturn;
                LOG.info("读{}结果原文:{}", i, telnetReturn);
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
                //记录
                recode34[i][0] = telnetRecode;

                //写入时间
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                recode34[i][1] = df.format(day);
                LOG.info("原文{}转换后结果:{}", i, telnetRecode);
                LOG.info("记录下 {}==>{}检测后得到的状态:{},检测时间：{}", i, telnetIP, recode34[i][0], recode34[i][1]);

            }
        }
        //重新读再写
        //从配置文件中读入IP
        InputStreamReader isr2 = new InputStreamReader(
                new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                StandardCharsets.UTF_8);
        LOG.info("重新读设备表,路径是{}/fil.properties以防设备表被修改.", System.getProperty("user.dir"));
        pro.load(isr2);
        //重新读表长度
        int endNum2 = Integer.parseInt(pro.getProperty("endnum"));
        LOG.info("=======================修正数据=======================");
        if (endNum2 == endNum) {
            for (int i = 0; i < endNum2; i++) {
                //读取字符串数组
                String listStr2 = pro.getProperty(String.valueOf(i));
                LOG.info("取文件中{}的值:{}", i, listStr2);
                if (listStr2 != null) {
                    //按，号分割
                    List<String> ipList2 = Arrays.asList(listStr2.split(","));
                    ipList2.set(3, recode34[i][0]);
                    ipList2.set(4, recode34[i][1]);
                    LOG.info("子串长度:{}", ipList2.size());
                    //去除空格
                    for (int j = 0; j < ipList2.size(); j++) {
                        ipList2.set(j, ipList2.get(j).trim());
                    }
                    //telnet 结果写入,去掉最前的[和最后的]
                    int ipListLength = ipList2.toString().length() - 1;
                    pro.setProperty(String.valueOf(i), ipList2.toString().substring(1, ipListLength));
                    LOG.info("{}写入文件值:{}", i, ipList2.toString().substring(1, ipListLength));
                }
            }
            LOG.info("写入{}/fil.properties", System.getProperty("user.dir"));
            File fi = new File(System.getProperty("user.dir") + "/fil.properties");
            pro.store(new PrintStream(fi), "utf-8");
            LOG.info("写入完成。");
        } else {
            LOG.info("表长度被修改，本次检测结果不保存。");
        }
        //调用记录器
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/spring-boot-mp-demo-0.1.1-SNAPSHOT/jsonList", String.class);
    }
}
