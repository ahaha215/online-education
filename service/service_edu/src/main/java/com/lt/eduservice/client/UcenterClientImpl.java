package com.lt.eduservice.client;

import com.lt.commonutils.R;
import com.lt.eduservice.entity.UcenterMember;
import org.springframework.stereotype.Component;



/**
 * @ClassName UcenterClientImpl
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMember getInfo(String id) {
        System.out.println("这里出错了");
        return null;
    }
}
