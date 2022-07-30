package com.lt.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.eduservice.entity.EduChapter;
import com.lt.eduservice.entity.EduVideo;
import com.lt.eduservice.entity.chapter.ChapterVO;
import com.lt.eduservice.entity.chapter.VideoVO;
import com.lt.eduservice.mapper.EduChapterMapper;
import com.lt.eduservice.mapper.EduVideoMapper;
import com.lt.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired(required = false)
    EduVideoMapper eduVideoMapper;


    @Override
    public List<ChapterVO> findCourseChapterAndVideo(String courseId) {

        List<ChapterVO> list = new ArrayList<>();

        // 查询章节目录
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper();
        eduChapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterQueryWrapper);

        for (EduChapter eduChapter : eduChapters) {

            String chapterId = eduChapter.getId();

            ChapterVO chapterVO = new ChapterVO();
            chapterVO.setId(chapterId);
            chapterVO.setTitle(eduChapter.getTitle());
            List<VideoVO> videoVOS = new ArrayList<>();

            // 查询对应章节的小节
            QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper();
            eduVideoQueryWrapper.eq("chapter_id",chapterId);
            List<EduVideo> eduVideos = eduVideoMapper.selectList(eduVideoQueryWrapper);

            for (EduVideo eduVideo : eduVideos) {
                VideoVO videoVO = new VideoVO();
                videoVO.setId(eduVideo.getId());
                videoVO.setTitle(eduVideo.getTitle());
                videoVO.setVideoSourceId(eduVideo.getVideoSourceId());

                videoVOS.add(videoVO);
            }
            chapterVO.setVideoList(videoVOS);

            list.add(chapterVO);

        }

        return list;
    }
}
