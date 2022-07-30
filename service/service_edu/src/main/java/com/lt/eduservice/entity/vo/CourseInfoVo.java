package com.lt.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName CourseInfoVo
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseInfoVo {

    private String id;
    private String teacherId;
    private String subjectId;
    private String subjectParentId;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private String description;
}
