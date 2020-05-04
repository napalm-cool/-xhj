package com.mp.demo.controller;

import com.mp.demo.serivce.UserInfoService;
import com.mp.demo.serivce.gisInfoService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * @author ycn
 * 按收到的数据修改表的内容,设备表，状态表合并
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UpdataList {

    private static final Logger LOG = LoggerFactory.getLogger(UpdataList.class);

    //状态服务
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private gisInfoService gis;

    @RequestMapping(value = "/updadtalist", method = RequestMethod.GET)
    public String helloWorld(HttpServletRequest request) throws IOException {
        String userstr = request.getParameter("statestr");
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据{}==>更新开始>>>>>>>>>>>>>>>>>>>>>>>>>>>", userstr);
        Properties pro = new Properties();
        //从配置文件中读入IP
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(System.getProperty("user.dir") + "/fil.properties"),
                StandardCharsets.UTF_8);
        LOG.info("读取设备表路径{}/fil.properties", System.getProperty("user.dir"));
        pro.load(isr);
        //按;号拆开userstr
        List<String> userIdList = null;
        if (userstr != null) {
            userIdList = Arrays.asList(userstr.split(";"));
            for (int i = 0; i < userIdList.size(); i++) {
                String strList = userIdList.get(i);
                LOG.info("++++++++++++++++要写入文件的字符串:{}++++++++++++++++++++++", strList);
                //按顺序取出第一个,号前的数字为Key
                int listNum = strList.indexOf(",");
                int listLen = strList.length();
                String listKey = strList.substring(0, listNum);
                String listStr = strList.substring(listNum + 1, listLen);
                LOG.info("读取KEY值:{}  内容:{}", listKey, listStr);
                //加入key返回
                pro.setProperty(listKey, listStr);

            }
        }

        LOG.info("写入设备表路径{}/fil.properties",System.getProperty("user.dir"));
        File fi = new File(System.getProperty("user.dir")+"/fil.properties");
        pro.store(new PrintStream(fi), "utf-8");

        return "修改成功！";
    }
}