package com.lt.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.commonutils.R;
import com.lt.eduservice.entity.EduCourse;
import com.lt.eduservice.entity.EduTeacher;
import com.lt.eduservice.service.EduCourseService;
import com.lt.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @Cacheable(value = "index", key = "'hotCourseAndTeacher'")
    @GetMapping("getNewCourseAndTeacher")
    public R getNewCourseAndTeacher() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);
        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("courseList",eduList).data("teacherList",teacherList);
    }
}
