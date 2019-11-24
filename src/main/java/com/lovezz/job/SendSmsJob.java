package com.lovezz.job;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 10:39
 * @Description:
 */

import com.lovezz.dto.SmsResult;
import com.lovezz.service.MailService;
import com.lovezz.service.SendMessageService;
import com.lovezz.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@Component
@Configuration
public class SendSmsJob {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsJob.class);

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private MailService mailService;

    @Value("${sms.mobile}")
    private String MOBILE;
    @Value("${love.year}")
    private String YEAR;
    @Value("${love.month}")
    private String MONTH;
    @Value("${love.day}")
    private String DAY;

    @Value("${tx.templateId}")
    private Integer TEMPLATEID2;

    @Value("${spring.mail.to}")
    private String to;

    @Scheduled(cron = "${sms.cron.workEndAfternoon}")
    public void sendSms() {
        try {
            logger.info("定时任务开始执行：发送短信时间" + LocalDateTime.now());
            SmsResult result = null;

            //==============腾讯云短信==============
            long day = TimeUtils.daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());

            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(day));
            result = sendMessageService.sendMessaage(MOBILE, TEMPLATEID2, params);

            //发送短信失败
            if (result.getResult() != 0 || "ok".equalsIgnoreCase(result.getErrmsg())){
                String context = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        + ": 短信发送失败，错误信息：" + result.getErrmsg() + ",错误码："+result.getResult();

                mailService.sendSimpleMail(to,"定时任务【短信】发送失败",context);
            }

            logger.info("定时任执行结束：发送短信结果" + result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}