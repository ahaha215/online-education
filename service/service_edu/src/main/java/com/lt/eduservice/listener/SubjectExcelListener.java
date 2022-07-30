package com.lt.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.eduservice.entity.EduSubject;
import com.lt.eduservice.entity.excel.SubjectData;
import com.lt.eduservice.service.EduSubjectService;
import com.lt.servicebase.exceptionhandler.GuliException;

/**
 * @ClassName SubjectExcelListener
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if (subjectData == null){
            throw new GuliException(20001,"文件为空！");
        }

        EduSubject eduOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(subjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            subjectService.save(eduOneSubject);
        }

        //获取一级分类id值
        String pid = eduOneSubject.getId();

        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

}
