package com.lt.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName CmsApplication
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@SpringBootApplication
@ComponentScan("com.lt")
@MapperScan("com.lt.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
