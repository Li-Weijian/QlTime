package com.qltime;

import com.qltime.job.SendSmsJob;
import com.qltime.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LovezzApplicationTests {
    @Autowired
    private MailService mailService;

    @Autowired
    private SendSmsJob sendSmsJob;


    @Test
    public void contextLoads() {
    }


    //测试发送邮件
    @Test
    public void testSendMail(){
        sendSmsJob.sendSms();
    }

}
