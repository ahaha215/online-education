package com.lt.eduservice.controller;


import com.lt.commonutils.R;
import com.lt.eduservice.client.VodClient;
import com.lt.eduservice.entity.EduVideo;
import com.lt.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-01
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

   @Autowired
   EduVideoService eduVideoService;

   @Autowired
   VodClient vodClient;

   @PostMapping("addVideo")
   public R addVideo(@RequestBody EduVideo eduVideo){
      eduVideoService.save(eduVideo);
      return R.ok();
   }

   @DeleteMapping("deleteVideo/{videoId}")
   public R deleteVideo(@PathVariable("videoId") String videoId){
      // 根据小节id获取视频id
      String videoSourceId = eduVideoService.getById(videoId).getVideoSourceId();
      // 删除小节中的视频
      if (!StringUtils.isEmpty(videoSourceId)){
         vodClient.deleteVideo(videoSourceId);
      }

      // 删除小节基本信息
      eduVideoService.removeById(videoId);
      return R.ok();
   }

   @GetMapping("getVideoById/{videoId}")
   public R getVideoById(@PathVariable("videoId") String videoId){
      EduVideo video = eduVideoService.getById(videoId);
      return R.ok().data("video",video);
   }

   @PostMapping("updateVideo")
   public R updateVideo(@RequestBody EduVideo eduVideo){
      eduVideoService.updateById(eduVideo);
      return R.ok();
   }

}

