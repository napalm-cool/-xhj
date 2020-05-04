package com.mp.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mp.demo.entity.TGisJunction;

/**
 * @Description 用户信息DAO
 * @Author Sans
 * @CreateTime 2019/6/8 16:24
 */
public interface gisInfoDao extends BaseMapper<TGisJunction> {

    /**
IPage<UserInfoEntity> 分页数据
     */
    IPage<TGisJunction> selectGisInfoByGtFraction(IPage<TGisJunction> page, Long fraction);
}
