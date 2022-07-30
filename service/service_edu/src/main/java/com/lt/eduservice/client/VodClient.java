package com.lt.eduservice.client;

import com.lt.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
public interface VodClient {

    // 定义要调用的方法
    @DeleteMapping("/eduvod/video/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId);
}
