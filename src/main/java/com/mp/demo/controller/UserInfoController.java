package com.mp.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mp.demo.dao.UserInfoDao;
import com.mp.demo.entity.TGisJunction;
import com.mp.demo.entity.UserInfoEntity;
import com.mp.demo.entity.Ycn;
import com.mp.demo.serivce.UserInfoService;
import com.mp.demo.serivce.gisInfoService;
import com.mp.demo.serivce.ycnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description UserInfoController, 生成纪录表
 * @Author Sans
 * @CreateTime 2019/6/8 16:27
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService isnoService;

    @Autowired
    private gisInfoService gis;

    @Autowired
    private ycnService getRecod;

    @Autowired
    private UserInfoDao mapperDao;

    private static final Logger LOG = LoggerFactory.getLogger(UserInfoController.class);

    /**
     * 实体集合
     */
    @RequestMapping(value = "/jsonList", method = RequestMethod.GET)
    //@RequestBody String postData
    public String jsonList()  {
        //转换成json,直接用@RequestBody JSONObject postData也可以，但如果POST来的数据不是JSON可能有问题。
        //JSONObject jsonData = JSONObject.parseObject(postData);
        //LOG.info("post数据:{}######################", jsonData.toJSONString());
        //设备表加Key后,subGis
        List<TGisJunction> gisInfo = gis.list();
        Map<String, TGisJunction> subGis = new HashMap<>();
        for (TGisJunction g : gisInfo) {
            subGis.put(g.getLkbh(), g);
        }
        //故障表并加Key,后subMap
        List<UserInfoEntity> isNo = isnoService.list();
        Map<String, UserInfoEntity> subMap = new HashMap<>();
        for (UserInfoEntity n : isNo) {
            //数据库中的路口名不足2位前加0
            String noJuncStr = n.getNOJUNC().toString();
            if (noJuncStr.length() < 2) {
                noJuncStr = "0" + noJuncStr;
            }
            //数据库的中区号+路口名=查表用的ID
            String idString = "0" + n.getNOAREA().toString() + noJuncStr;
            subMap.put(idString, n);
        }

        //读纪录表
        List<Ycn> recode = getRecod.list();
        //遍历记录表
        for (Ycn y : recode) {
            if (y.getTime() == null) {
                //找到没修好的，看看现在修好了吗？
                if ("20".equals(subMap.get(y.getId().trim()).getCTRMODE().toString())) {
                    //没修好，从故障表删除，等会不用再加了
                    LOG.info("发现没修好的getId:{},值{}", y.getId(), subMap.get(y.getId().trim()).toString());
                    subMap.remove(y.getId().trim());
                } else {
                    //不是20，修好了，存入当前机况
                    y.setIsno(subMap.get(y.getId().trim()).getCTRMODE().toString());
                    // 存入修复时间
                    Timestamp time = Timestamp.valueOf(subMap.get(y.getId().trim()).getDATETIME());
                    long time1=time.getTime();
                    y.setTime(String.valueOf(time1));
                    LOG.info("已经修好{}", y.getTime());
                }
            }
        }
        //遍历修复表
        for (String key : subMap.keySet()) {
            if ("20".equals(subMap.get(key).getCTRMODE().toString())) {
                LOG.info("剩下的故障点{}", key);
                Ycn device = new Ycn();
                if (subGis.get(key) != null) {
                    //存入Id
                    LOG.info("key：{}", key);
                    device.setId(key);
                    //存入名字
                    LOG.info("名字：{}", subGis.get(key).getLkmc());
                    device.setName(subGis.get(key).getLkmc());
                    //存入机况
                    LOG.info("机况：{}", subMap.get(key).getCTRMODE().toString());
                    device.setIsno("脱机");
                    //存入故障时间
                    Timestamp time = Timestamp.valueOf(subMap.get(key).getDATETIME());
                    long time1=time.getTime();
                    device.setNotime(String.valueOf(time1));
                    LOG.info("故障时间：{}",device.getNotime());
                    //存入记录表
                    recode.add(device);
                }
            }
            ;
        }
        //传入的实体类userInfoEntity中ID为null就会新增(ID自增)
        //实体类ID值存在,如果数据库存在ID就会更新,如果不存在就会新增
        for (Ycn r :recode) {
            getRecod.saveOrUpdate(r);
        }

        return JSON.toJSONString(recode, SerializerFeature.WriteMapNullValue);
    }

}