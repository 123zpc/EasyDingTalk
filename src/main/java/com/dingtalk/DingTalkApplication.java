package com.dingtalk;

import com.dingtalk.config.DingTalkConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class DingTalkApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(DingTalkApplication.class, args);
        //注册DingTalk Stream长连接
        context.getBean(DingTalkConfig.class).initDingTalkConnection();
    }
}