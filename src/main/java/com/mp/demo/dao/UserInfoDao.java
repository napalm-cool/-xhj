package com.mp.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mp.demo.entity.UserInfoEntity;

/**
 * @Description 用户信息DAO
 * @Author Sans
 * @CreateTime 2019/6/8 16:24
 */
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    /**
IPage<UserInfoEntity> 分页数据
     */
    //IPage<UserInfoEntity> selectUserInfoByGtFraction(IPage<UserInfoEntity> page, Long fraction);
}
