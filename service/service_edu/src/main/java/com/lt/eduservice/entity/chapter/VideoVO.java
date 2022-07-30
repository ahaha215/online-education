package com.lt.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName videoVO
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoVO {
    private String id;
    private String title;
    private String videoSourceId;
}
