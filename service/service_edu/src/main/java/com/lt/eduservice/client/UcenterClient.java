package com.lt.eduservice.client;

import com.lt.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @PostMapping("/ucenterservice/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id);
}
