package com.lt.eduservice.entity.vo;

import lombok.Data;

/**
 * @ClassName TeacherQuery
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Data
public class TeacherQuery {

    private String name;

    private Integer level;

    private String begin; // 注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    private String end;
}
