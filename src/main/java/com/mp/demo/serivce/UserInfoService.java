package com.mp.demo.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mp.demo.entity.UserInfoEntity;


/**
 * @Description 用户业务接口
 * @Author Sans
 * @CreateTime 2019/6/8 16:26
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    /**
     IPage<UserInfoEntity> 分页数据
     */
    //IPage<UserInfoEntity> selectUserInfoByGtFraction(IPage<UserInfoEntity> page,Long fraction);
}
