package com.mp.demo.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.demo.dao.gisInfoDao;
import com.mp.demo.entity.TGisJunction;
import com.mp.demo.serivce.gisInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 用户业务实现
 * @Author Sans
 * @CreateTime 2019/6/8 16:26
 */
@Service
@Transactional
public class gisInfoServiceImpl extends ServiceImpl<gisInfoDao, TGisJunction> implements gisInfoService {

    /**
     IPage<UserInfoEntity> 分页数据
    }

    @Override
    public IPage<TGisJunction> selectGIsInfoByGtFraction(IPage<TGisJunction> page, Long fraction) {
        return null;
    }*/
}
