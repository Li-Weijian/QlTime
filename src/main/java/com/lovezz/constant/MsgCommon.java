package com.lovezz.constant;

import com.lovezz.dto.BaseResult;

/**
 * @auther: liweijian
 * @Date: 2020/9/20 22:46
 * @Description:
 */
public interface MsgCommon {

    // 成功
    BaseResult SUCCESS = new BaseResult(200, "操作成功");

    // 登录失败
    BaseResult TOKEN_ERROR = new BaseResult(10000, "用户令牌无效");

}
