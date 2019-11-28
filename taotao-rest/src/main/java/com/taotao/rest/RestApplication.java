package com.taotao.rest;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.taotao.mapper")
@EnableDubbo(scanBasePackages = "com.taotao.rest.service")
public class RestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
