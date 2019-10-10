package com.lovezz.service.impl;

import com.lovezz.entity.Test;
import com.lovezz.mapper.TestMapper;
import com.lovezz.service.TestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

}
