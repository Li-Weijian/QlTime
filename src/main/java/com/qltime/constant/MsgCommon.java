package com.qltime.constant;

import com.qltime.model.dto.BaseResult;

/**
 * @auther: liweijian
 * @Date: 2020/9/20 22:46
 * @Description:
 */
public interface MsgCommon {

    BaseResult SUCCESS = new BaseResult(200, "操作成功");
    BaseResult ERROR = new BaseResult(500, "操作失败");

    BaseResult TOKEN_ERROR = new BaseResult(10000, "用户令牌无效");

    BaseResult USER_NULL = new BaseResult(10001, "当前用户不存在");

    BaseResult URL_ERROR = new BaseResult(10002, "URL出现错误");

}
