package com.lt.eduservice.service;

import com.lt.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lt.eduservice.entity.vo.SubjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<SubjectNode> subjectList();

    List<EduSubject> oneSubjectList();

    List<EduSubject> twoSubjectList(String parentId);
}
