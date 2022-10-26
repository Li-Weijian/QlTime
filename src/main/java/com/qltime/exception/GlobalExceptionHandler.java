package com.qltime.exception;

import com.qltime.constant.MsgCommon;
import com.qltime.dto.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResult globalException(HttpServletResponse response, Exception ex) {
        LOGGER.error("throw Exception-" + ex.getMessage(), ex);
        BaseResult result = BaseResult.fail(MsgCommon.ERROR.getStatus(), "GlobalExceptionHandler:" + ex.getMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public BaseResult commonException(HttpServletResponse response, CommonException ex) {
        LOGGER.error("throw CommonException-" + ex.getMessage(), ex);
        BaseResult result = BaseResult.fail(response.getStatus(), ex.getMessage());
        return result;
    }

}
