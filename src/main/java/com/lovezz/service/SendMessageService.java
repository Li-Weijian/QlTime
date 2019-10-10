package com.lovezz.service;

import java.io.IOException;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:07
 * @Description:
 */
public interface SendMessageService {

    String sendMessaage(String mobile, String templateid,String content) throws IOException;


}
