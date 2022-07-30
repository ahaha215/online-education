package com.lt.eduservice.client;

import com.lt.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @ClassName VodClientImpl
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String videoId) {
        return R.error().data("msg","Time Out!");
    }
}
