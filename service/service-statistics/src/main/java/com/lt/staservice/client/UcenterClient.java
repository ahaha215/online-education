package com.lt.staservice.client;

import com.lt.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户中心远程调用
 *
 * @Author lt
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @GetMapping("/ucenterservice/member/countRegisterSum/{day}")
    public R countRegisterSum(@PathVariable("day") String day);
}
