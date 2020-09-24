package com.lovezz.constant;

import com.lovezz.dto.BaseResult;

/**
 * @auther: liweijian
 * @Date: 2020/9/20 22:46
 * @Description:
 */
public interface MsgCommon {

    BaseResult SUCCESS = new BaseResult(200, "操作成功");

    BaseResult TOKEN_ERROR = new BaseResult(10000, "用户令牌无效");

    BaseResult USER_NULL = new BaseResult(10001, "当前用户不存在");

}
