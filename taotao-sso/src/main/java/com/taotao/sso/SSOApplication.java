package com.taotao.sso;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@MapperScan("com.taotao.mapper")
@EnableDubbo(scanBasePackages = "com.taotao.sso.service")
public class SSOApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class, args);
    }
}



