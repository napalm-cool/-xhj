package com.mp.demo.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mp.demo.entity.TGisJunction;


/**
 * @Description 用户业务接口
 * @Author Sans
 * @CreateTime 2019/6/8 16:26
 */
public interface gisInfoService extends IService<TGisJunction> {

    /**
     IPage<UserInfoEntity> 分页数据
     */
   // IPage<TGisJunction> selectGIsInfoByGtFraction(IPage< TGisJunction> page, Long fraction);
}
