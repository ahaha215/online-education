package com.lt.eduservice.controller;


import com.lt.commonutils.R;
import com.lt.eduservice.entity.EduSubject;
import com.lt.eduservice.entity.vo.SubjectNode;
import com.lt.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-28
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired(required = false)
    EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        subjectService.saveSubject(file,subjectService);

        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){

        List<SubjectNode> list = subjectService.subjectList();

        return R.ok().data("list",list);
    }

    // 获取所有的一级分类
    @GetMapping("getAllOneSubject")
    public R getAllOneSubject(){
        List<EduSubject> list = subjectService.oneSubjectList();
        return R.ok().data("oneSubjectList",list);
    }

    // 获取一级分类下所有的二级分类
    @GetMapping("getAllTwoSubject/{parentId}")
    public R getAllTwoSubject(@PathVariable("parentId") String parentId){
        List<EduSubject> list = subjectService.twoSubjectList(parentId);
        return R.ok().data("twoSubjectList",list);
    }
}

