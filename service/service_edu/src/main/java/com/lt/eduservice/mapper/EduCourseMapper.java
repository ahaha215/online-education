package com.lt.eduservice.mapper;

import com.lt.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lt.eduservice.entity.vo.BaseCourseVo;
import com.lt.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectCoursePublishVoById(String id);

    BaseCourseVo selectBaseCourse(String id);
}
