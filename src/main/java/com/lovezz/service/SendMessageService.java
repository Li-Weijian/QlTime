package com.lovezz.service;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:07
 * @Description:
 */
public interface SendMessageService {

    String sendMessaage(String mobile, String templateid,String content) throws IOException;

    String sendMessaage(String mobile, Integer templateid,ArrayList<String> params) throws IOException, HTTPException;


}
