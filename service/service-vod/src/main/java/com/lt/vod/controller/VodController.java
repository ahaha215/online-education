package com.lt.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lt.commonutils.R;
import com.lt.vod.service.VodService;
import com.lt.vod.util.ConstantVodUtils;
import com.lt.vod.util.InitVodClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName VodController
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频
    @PostMapping("uploadAliYunVideo")
    public R uploadAliYunVideo(MultipartFile file){

        String videoId = vodService.uploadVideo(file);

        return R.ok().data("videoId",videoId);
    }

    // 删除视频
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        vodService.deleteVideo(videoId);
        return R.ok();
    }

    @GetMapping("getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = InitVodClientUtils.initVodClient(accessKeyId,
                accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }

}
