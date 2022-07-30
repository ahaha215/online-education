package com.lt.eduservice.service.impl;

import com.lt.eduservice.entity.EduCourse;
import com.lt.eduservice.entity.EduCourseDescription;
import com.lt.eduservice.entity.vo.BaseCourseVo;
import com.lt.eduservice.entity.vo.CourseInfoVo;
import com.lt.eduservice.entity.vo.CoursePublishVo;
import com.lt.eduservice.mapper.EduCourseMapper;
import com.lt.eduservice.service.EduCourseDescriptionService;
import com.lt.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduCourseMapper eduCourseMapper;

    // 添加课程
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表中添加课程基本信息
        // 将CourseInfoVo 转换到 EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0){
            throw new GuliException(20001,"添加课程信息失败！");
        }

        // 获取添加课程的id
        String courseId = eduCourse.getId();

        // 向课程描述表中添加课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(courseId);
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public BaseCourseVo getBaseCourseById(String id) {
        BaseCourseVo baseCourseVo = eduCourseMapper.selectBaseCourse(id);
        return baseCourseVo;
    }
}
