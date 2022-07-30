package com.lt.eduservice.service;

import com.lt.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lt.eduservice.entity.vo.BaseCourseVo;
import com.lt.eduservice.entity.vo.CourseInfoVo;
import com.lt.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
    CoursePublishVo getCoursePublishVoById(String id);

    BaseCourseVo getBaseCourseById(String id);
}
