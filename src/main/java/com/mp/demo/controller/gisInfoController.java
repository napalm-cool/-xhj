package com.mp.demo.controller;

import com.mp.demo.entity.TGisJunction;
import com.mp.demo.serivce.gisInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Description gisInfoController
 * @Author Sans
 * @CreateTime 2019/6/8 16:27
 */
@RestController
public class gisInfoController {

    @Autowired
    private gisInfoService gis;
    private static final Logger LOG = LoggerFactory.getLogger(gisInfoController.class);
     /**
     * 查询全部信息
     * @Author Sans
     * @CreateTime 2019/6/8 16:35
     * @Param  userId  用户ID
     * @Return List<UserInfoEntity> 用户实体集合
     */
    @RequestMapping("/updateName")
    public String updateName(){
        List<TGisJunction> gisInfo = gis.list();
        LOG.info("******************更新路口名******************************");
         Properties pro = new Properties();
        //从配置文件中读入IP
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(
                    new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                    StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LOG.info("读取设备表路径{}/fil.properties", System.getProperty("user.dir"));
        try {
            assert isr != null;
            pro.load(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int endNum = Integer.parseInt(pro.getProperty("endnum"));
        for (int i = 0; i <endNum; i++) {
            //读取字符串数组
            String listStr = pro.getProperty(String.valueOf(i));
            LOG.info("取文件中{}的值:{}",i,listStr);
            if (listStr != null) {
                //按，号分割
                List<String> ipList = Arrays.asList(listStr.split(","));
                for (int j=0;j<gisInfo.size();j++){

                    if (ipList.get(0).trim().equals(gisInfo.get(j).getLkbh().trim())) {
                        LOG.info("找到了相等的路口号====文件中路口号:{},数据库中的路口号:{},更改的路口名:{}",
                                ipList.get(0),
                                gisInfo.get(j).getLkbh(), gisInfo.get(j).getLkmc());
                        ipList.set(1, gisInfo.get(j).getLkmc());
                        break;
                    }
                }
                //去除空格
                for (int m = 0; m < ipList.size(); m++) {
                    ipList.set(m,ipList.get(m).trim());
                }
                //更改名字的结果写入文件,去掉最前的[和最后的]
                int ipListLength = ipList.toString().length() - 1;
                pro.setProperty(String.valueOf(i), ipList.toString().substring(1, ipListLength));
                LOG.info("{}写入文件值:{}", i, ipList.toString().substring(1, ipListLength));
            }
        }
        LOG.info("写入{}/fil.properties", System.getProperty("user.dir"));
        File fi = new File(System.getProperty("user.dir") + "/fil.properties");
        try {
            pro.store(new PrintStream(fi), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("写入完成。");
        return "信号机名已经同步。";
    }

}