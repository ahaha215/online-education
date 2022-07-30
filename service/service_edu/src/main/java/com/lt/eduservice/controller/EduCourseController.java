package com.lt.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.commonutils.R;
import com.lt.eduservice.client.VodClient;
import com.lt.eduservice.entity.EduChapter;
import com.lt.eduservice.entity.EduCourse;
import com.lt.eduservice.entity.EduCourseDescription;
import com.lt.eduservice.entity.EduVideo;
import com.lt.eduservice.entity.request.CourseQueryRequest;
import com.lt.eduservice.entity.request.FrontCourseQueryRequest;
import com.lt.eduservice.entity.vo.BaseCourseVo;
import com.lt.eduservice.entity.vo.CourseInfoVo;
import com.lt.eduservice.entity.vo.CoursePublishVo;
import com.lt.eduservice.service.EduChapterService;
import com.lt.eduservice.service.EduCourseDescriptionService;
import com.lt.eduservice.service.EduCourseService;
import com.lt.eduservice.service.EduVideoService;
import com.lt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    VodClient vodClient;

    // 添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody  CourseInfoVo courseInfoVo){

        String id = eduCourseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId",id);
    }

    // 按照课程id 获取基本课程信息
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable("courseId") String courseId){

        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("id",courseId);
        EduCourse course = eduCourseService.getOne(courseWrapper);

        QueryWrapper<EduCourseDescription> descriptionWrapper = new QueryWrapper<>();
        descriptionWrapper.eq("id",courseId);
        EduCourseDescription description = eduCourseDescriptionService.getOne(descriptionWrapper);

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(course,courseInfoVo);
        BeanUtils.copyProperties(description,courseInfoVo);

        return R.ok().data("list",courseInfoVo);
    }

    // 修改课程基本信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        boolean flag = eduCourseService.updateById(eduCourse);

        if (!flag){
            throw new GuliException(20001,"课程信息修改失败！");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);

        return R.ok();
    }

    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishVoById = eduCourseService.getCoursePublishVoById(courseId);
        return R.ok().data("course",coursePublishVoById);
    }

    @PostMapping("updateCourseStatus/{courseId}")
    public R updateCourseStatus(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    // 按照条件分页查找课程信息
    @PostMapping("getCourseInfoConditions/{current}/{limit}")
    public R getCourseInfoConditions(@PathVariable("current") long current,
                                     @PathVariable ("limit") long limit,
                                     @RequestBody(required = false) CourseQueryRequest courseQueryRequest){

        Page<EduCourse> page = new Page<>(current,limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper();

        // 课程查询属性
        String title = courseQueryRequest.getTitle();
        String teacherId = courseQueryRequest.getTeacherId();
        String subjectParentId = courseQueryRequest.getSubjectParentId();
        String subjectId = courseQueryRequest.getSubjectId();


        // 设置课程查询值
        if (!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }

        if (!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }

        Page list = (Page) eduCourseService.page(page,wrapper);

        return R.ok().data("list",list);
    }

    // 删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId){

        // 根据课程id 获取所有的视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> list = eduVideoService.list(wrapper);
        // 遍历删除所有的视频
        for (EduVideo eduVideo : list) {
           vodClient.deleteVideo(eduVideo.getVideoSourceId());
        }

        // 删除课程的所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        eduVideoService.remove(videoWrapper);

        // 删除课程的所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        eduChapterService.remove(chapterWrapper);

        // 删除课程的描述
        QueryWrapper<EduCourseDescription> descriptionWrapper = new QueryWrapper<>();
        descriptionWrapper.eq("id",courseId);
        eduCourseDescriptionService.remove(descriptionWrapper);

        // 删除课程的基本信息（逻辑删除）
        eduCourseService.removeById(courseId);

        return R.ok();
    }


    /**
     * 按照前端进行条件分页查询
     */
    @PostMapping("pageCourseListByConditions/{current}/{limit}")
    public R pageCourseListByConditions(@PathVariable("current") long current,
                                        @PathVariable("limit") long limit,
                                        @RequestBody(required = false)FrontCourseQueryRequest frontCourseQueryRequest){

        // 分页条件
        Page<EduCourse> page = new Page<>(current,limit);

        // 查询条件
        String subjectId = frontCourseQueryRequest.getSubjectId();
        String orderByType = frontCourseQueryRequest.getOrderByType();

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        // 设置查询条件
        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(orderByType)){
            queryWrapper.orderByDesc(orderByType);
        }

        Page<EduCourse> courses = (Page<EduCourse>) eduCourseService.page(page, queryWrapper);


        return R.ok().data("total",courses.getTotal())
                .data("pages",courses.getPages())
                .data("hasPreviousgetBaseCourseById",courses.hasPrevious())
                .data("hasNext",courses.hasNext())
                .data("courseList",courses.getRecords());
    }

    /**
     * @MethodName getBaseCourseById
     * @Description 按照id获取课程基本信息
     * @Author lt
     * @Param [id]
     * @return com.lt.commonutils.R
     **/
    @GetMapping("getBaseCourseById/{id}")
    public R getBaseCourseById(@PathVariable("id") String id){
        BaseCourseVo baseCourseVo = eduCourseService.getBaseCourseById(id);

        return R.ok().data("baseCourse", baseCourseVo);
    }

    @GetMapping("getCourseAndTeacher/{id}")
    public BaseCourseVo getCourseAndTeacher(@PathVariable("id") String id){
        BaseCourseVo baseCourseVo = eduCourseService.getBaseCourseById(id);

        return baseCourseVo;
    }

}

