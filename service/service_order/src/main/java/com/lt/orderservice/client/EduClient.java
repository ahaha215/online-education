package com.lt.orderservice.client;

import com.lt.orderservice.entity.vo.BaseCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {
    @GetMapping("/eduservice/course/getCourseAndTeacher/{id}")
    public BaseCourseVo getBaseCourse(@PathVariable("id") String id);
}
