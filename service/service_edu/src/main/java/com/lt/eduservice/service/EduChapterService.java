package com.lt.eduservice.service;

import com.lt.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lt.eduservice.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> findCourseChapterAndVideo(String courseId);
}
