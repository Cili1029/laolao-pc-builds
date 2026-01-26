package com.laolao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 定时任务
@EnableAsync // 异步
@SpringBootApplication
public class LaolaoPcBuildsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaolaoPcBuildsApplication.class, args);
    }

}
