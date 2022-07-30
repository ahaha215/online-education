package com.lt.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.commonutils.R;
import com.lt.eduservice.entity.EduCourse;
import com.lt.eduservice.entity.EduTeacher;
import com.lt.eduservice.entity.vo.TeacherQuery;
import com.lt.eduservice.service.EduCourseService;
import com.lt.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-21
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * @MethodName findAllTeacher
     * @Description 查询讲师所有数据
     * @Author lt
     * @Param []
     * @return java.util.List<com.lt.eduservice.entity.EduTeacher>
     **/
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * @MethodName removeTeacher
     * @Description 按照id删除教师信息
     * @Author lt
     * @Param [id]
     * @return com.lt.commonutils.R
     **/
    @DeleteMapping("/delTeacher/{id}")
    public R removeTeacher(@PathVariable("id") String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * @MethodName pageListTeacher
     * @Description 分页查询讲师信息
     * @Author lt
     * @Param [current, limit]
     * @return com.lt.commonutils.R
     **/
    @GetMapping("/page/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit){


        Page<EduTeacher> page = new Page<>(current,limit);

        Page<EduTeacher> eduTeacherPage = (Page<EduTeacher>) eduTeacherService.page(page, null);
        // 获取分页信息
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        boolean hasPrevious = eduTeacherPage.hasPrevious();
        boolean hasNext = eduTeacherPage.hasNext();


        return R.ok().data("total",total).data("hasPrevious",hasPrevious).data("hasNext",hasNext).data("rows",records);
    }

    /**
     * @MethodName pageTeacherCondition
     * @Description 按照条件分页查询
     * @Author lt
     * @Param [current, limit, teacherQuery]
     * @return com.lt.commonutils.R
     **/
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> page = new Page<>(current,limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 判断条件之是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();


        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        // 按照创建时间进行排序
        wrapper.orderByDesc("gmt_create");

        IPage<EduTeacher> pageTeacher = eduTeacherService.page(page, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();


        return R.ok().data("total",total).data("records",records);
    }

    /**
     * @MethodName addTeacher
     * @Description 添加讲师信息
     * @Author lt
     * @Param [eduTeacher]
     * @return com.lt.commonutils.R
     **/
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * @MethodName getTeacherById
     * @Description 更具id查询教师信息
     * @Author lt
     * @Param [id]
     * @return com.lt.commonutils.R
     **/
    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    /**
     * @MethodName UpdateTeacher
     * @Description 修改讲师信息
     * @Author lt
     * @Param [eduTeacher]
     * @return com.lt.commonutils.R
     **/
    @PostMapping("/updateTeacher")
    public R UpdateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * @MethodName getCoursesOfTeacherId
     * @Description 获取讲师主讲课程
     * @Author lt
     * @Param [teacherId]
     * @return com.lt.commonutils.R
     **/
   @GetMapping("getCoursesOfTeacherId/{teacherId}")
   public R getCoursesOfTeacherId(@PathVariable("teacherId") String teacherId){

       QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("teacher_id",teacherId);

       List<EduCourse> list = eduCourseService.list(queryWrapper);

       return R.ok().data("courseList",list);
   }
}