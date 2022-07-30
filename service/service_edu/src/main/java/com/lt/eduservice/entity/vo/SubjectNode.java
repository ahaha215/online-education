package com.lt.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SubjectNode
 * @Description 前端展示课程分类树结点
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectNode {
    private String id;
    private String label;
    private List<SubjectNode> children;
}
