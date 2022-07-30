package com.lt.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName OrderApplication
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@SpringBootApplication
@ComponentScan("com.lt")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.lt.orderservice.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
