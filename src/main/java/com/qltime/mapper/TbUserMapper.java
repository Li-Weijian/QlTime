package com.qltime.mapper;

import com.qltime.model.entity.TbUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-30
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    /**
     * 获取用户邮箱
     * @param param
     * @return
     */
    List<String> selectUserEmail(Map param);
}
