package com.lt.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.eduservice.entity.EduSubject;
import com.lt.eduservice.entity.excel.SubjectData;
import com.lt.eduservice.entity.vo.SubjectNode;
import com.lt.eduservice.listener.SubjectExcelListener;
import com.lt.eduservice.mapper.EduSubjectMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Autowired(required = false)
    EduSubjectMapper eduSubjectMapper;

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectNode> subjectList() {

        List<SubjectNode> list = new ArrayList<>();

        QueryWrapper<EduSubject> wrapper = new QueryWrapper();
        wrapper.eq("parent_id","0");

        List<EduSubject> oneList = eduSubjectMapper.selectList(wrapper);

        for (EduSubject subject : oneList) {

            SubjectNode subjectNode = new SubjectNode();
            subjectNode.setId(subject.getId());
            subjectNode.setLabel(subject.getTitle());

            QueryWrapper<EduSubject> queryWrapper = new QueryWrapper();
            queryWrapper.eq("parent_id", subject.getId());

            List<EduSubject> twoSubject = eduSubjectMapper.selectList(queryWrapper);
            List<SubjectNode> twoList = new ArrayList<>();
            for (EduSubject eduSubject : twoSubject) {
                SubjectNode twoNode = new SubjectNode();
                twoNode.setId(eduSubject.getId());
                twoNode.setLabel(eduSubject.getTitle());

                twoList.add(twoNode);
            }

            subjectNode.setChildren(twoList);

            list.add(subjectNode);

        }

        return list;
    }

    @Override
    public List<EduSubject> oneSubjectList() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id","0");
        List list = baseMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<EduSubject> twoSubjectList(String parentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parentId);
        List list = baseMapper.selectList(queryWrapper);

        return list;
    }
}
