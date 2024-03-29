package com.taotao.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@MapperScan("com.taotao.mapper")
public class SearchApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}



