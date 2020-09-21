package com.lovezz.exception;

import com.lovezz.dto.BaseResult;
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
        BaseResult result = BaseResult.fail(response.getStatus(), "GlobalExceptionHandler:" + ex.getMessage());
        return result;
    }
}
