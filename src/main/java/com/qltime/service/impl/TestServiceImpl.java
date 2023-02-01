package com.qltime.service.impl;

import com.qltime.model.entity.Test;
import com.qltime.mapper.TestMapper;
import com.qltime.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Autowired
    private TestMapper testMapper;


    @Override
    public Test getTest(){

        return  testMapper.selectById(1);
    }

}
