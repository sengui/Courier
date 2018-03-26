package org.scnydx.huliang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author: CSG
 * @Description: 启动spring boot
 * @Date: Create in 9:18 2018/3/26
 * @Modify by:
 */
@SpringBootApplication
@MapperScan("org.scnydx.huliang.dao")
@ServletComponentScan("org.scnydx.huliang.*")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
