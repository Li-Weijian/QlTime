package com.lovezz.job;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 10:39
 * @Description:
 */

import com.lovezz.service.SendMessageService;
import com.lovezz.utils.FileUtils;
import com.lovezz.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Configuration
public class SendSmsJob {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsJob.class);

    @Autowired
    private SendMessageService sendMessageService;

    @Value("${sms.templateid}")
    private String TEMPLATEID;
    @Value("${sms.mobile}")
    private String MOBILE;
    @Value("${love.year}")
    private String YEAR;
    @Value("${love.month}")
    private String MONTH;
    @Value("${love.day}")
    private String DAY;

    @Scheduled(cron = "${sms.cron.workEndAfternoon}")
    public void sendSms(){
        try {
            logger.info("定时任务开始执行：发送短信时间"+LocalDateTime.now());

            long day = TimeUtils.daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());
            List loveList = null;
            loveList = FileUtils.getTextForLove("/static/love.txt");
            int lineNum = new Random().nextInt(loveList.size()+1);
            String param = day+","+loveList.get(lineNum);

            sendMessageService.sendMessaage(MOBILE,TEMPLATEID,param);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
