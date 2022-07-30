package com.lt.eduservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName EduApplication
 * @Description 启动类
 * @Author lt
 * @Version 1.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.lt"})
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
       SpringApplication.run(EduApplication.class,args);
    }
}
