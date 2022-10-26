package com.qltime.service.impl;

import com.qltime.entity.TbLovetext;
import com.qltime.mapper.TbLovetextMapper;
import com.qltime.service.TbLovetextService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-19
 */
@Service
public class TbLovetextServiceImpl extends ServiceImpl<TbLovetextMapper, TbLovetext> implements TbLovetextService {

    @Autowired
    private TbLovetextMapper lovetextMapper;


    @Override
    public String getOneTextRandom(){
        List<TbLovetext> lovetextList = lovetextMapper.selectList(null);
        int lineNum = new Random().nextInt(lovetextList.size());
        TbLovetext lovetext = lovetextList.get(lineNum);

        //增加读取次数
        lovetext.setCount(lovetext.getCount()+1);
        lovetextMapper.updateById(lovetext);
        return lovetext.getText();
    }




}
