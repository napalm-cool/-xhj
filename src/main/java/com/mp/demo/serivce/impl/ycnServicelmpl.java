package com.mp.demo.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.demo.dao.ycnInfoDao;
import com.mp.demo.entity.Ycn;
import com.mp.demo.serivce.ycnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 用户业务实现
 * @Author Sans
 * @CreateTime 2019/6/8 16:26
 */
@Service
@Transactional
public class ycnServicelmpl extends ServiceImpl<ycnInfoDao, Ycn> implements ycnService {


}


