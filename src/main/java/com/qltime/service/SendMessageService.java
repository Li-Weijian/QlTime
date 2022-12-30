package com.qltime.service;

import com.github.qcloudsms.httpclient.HTTPException;
import com.qltime.model.dto.SmsResult;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:07
 * @Description:
 */
public interface SendMessageService {

    String sendMessaage(String mobile, String templateid,String content) throws IOException;

    SmsResult sendMessaage(String mobile, Integer templateid, ArrayList<String> params) throws IOException, HTTPException;


}
