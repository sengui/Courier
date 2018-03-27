package org.scnydx.huliang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Author: CSG
 * @Description: 启动spring boot
 * @Date: Create in 9:18 2018/3/26
 * @Modify by:
 */
@SpringBootApplication
@MapperScan("org.scnydx.huliang.dao")
@ServletComponentScan("org.scnydx.huliang.*")
@EnableScheduling
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //设置线程池大小
        taskScheduler.setPoolSize(10);
        //设置线程前置名称
        taskScheduler.setThreadNamePrefix("task-getLogistics");
        return taskScheduler;
    }
}
