package com.lt.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.commonutils.R;
import com.lt.eduservice.entity.EduChapter;
import com.lt.eduservice.entity.EduVideo;
import com.lt.eduservice.entity.chapter.ChapterVO;
import com.lt.eduservice.service.EduChapterService;
import com.lt.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    @GetMapping("getCourseChapterAndVideo/{courseId}")
    public R getCourseChapterAndVideo(@PathVariable("courseId") String courseId){
        List<ChapterVO> list = eduChapterService.findCourseChapterAndVideo(courseId);
        return R.ok().data("list",list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable("chapterId") String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        // 先删除章节
        eduChapterService.removeById(chapterId);
        // 删除章节下面的小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        eduVideoService.remove(wrapper);
        return R.ok();
    }
}

