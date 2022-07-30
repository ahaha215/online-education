package com.lt.eduservice.entity.vo;

import lombok.Data;


/**
 * @ClassName CoursePublishVo
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Data
public class CoursePublishVo{

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
