package com.qltime.job;

/**
 * @Auther: liweijian
 * @Description: 微信推送通知定时任务
 */

import com.qltime.service.PushDailyWechat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author liweijian
 */
@Component
@Configuration
public class PushWxJob {

    private static final Logger logger = LoggerFactory.getLogger(PushWxJob.class);

    private final PushDailyWechat pushDailyWechat;

    public PushWxJob(PushDailyWechat pushDailyWechat) {
        this.pushDailyWechat = pushDailyWechat;
    }


    @Scheduled(cron = "0 30 6 * * ? ")
    public void pushWx() {
        try {
            logger.info("定时任务开始执行：推送时间" + LocalDateTime.now());
            pushDailyWechat.pushWechat();
            logger.info("定时任执行结束");
        } catch (Exception e) {
            logger.error("定时任务执行失败. ", e);
        }
    }
}
