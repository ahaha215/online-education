package com.lt.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ChapterVO
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChapterVO {
    private String id;
    private String title;

    private List<VideoVO> videoList;
}
