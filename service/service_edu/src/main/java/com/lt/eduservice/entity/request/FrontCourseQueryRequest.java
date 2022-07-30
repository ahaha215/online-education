package com.lt.eduservice.entity.request;

import lombok.Data;

/**
 * @ClassName FrontCourseQueryRequest
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Data
public class FrontCourseQueryRequest {
    // 学科id
    private String subjectId;
    // 排序依据类型
    private String orderByType;
}
