package com.lt.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.commonutils.JwtUtils;
import com.lt.commonutils.R;

import com.lt.eduservice.client.UcenterClient;
import com.lt.eduservice.entity.EduComment;
import com.lt.eduservice.entity.UcenterMember;
import com.lt.eduservice.service.EduCommentService;
import com.lt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-20
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    UcenterClient ucenterClient;

    @Autowired
    EduCommentService eduCommentService;

    // 发布评论
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request){

        String id = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember ucenterMember =  ucenterClient.getInfo(id);

        if(StringUtils.isEmpty(ucenterMember)) {
            return R.error().code(28004).message("请先登录!");
        }

        EduComment comment = new EduComment();

        BeanUtils.copyProperties(ucenterMember,comment);

        comment.setMemberId(ucenterMember.getId());
        comment.setId(null);

        comment.setContent(eduComment.getContent());
        comment.setCourseId(eduComment.getCourseId());
        comment.setTeacherId(eduComment.getTeacherId());

        eduCommentService.save(comment);

        return R.ok();
    }

    // 分页获取评论信息
    @GetMapping("pageCourseComment/{current}/{limit}")
    public R pageCourseComment(@PathVariable("current") long current,
                               @PathVariable("limit") long limit){

        Page<EduComment> page = new Page<>(current,limit);

        Page<EduComment> list = (Page<EduComment>) eduCommentService.page(page, null);

        long total = list.getTotal();
        long pages = list.getPages();
        boolean hasPrevious = list.hasPrevious();
        boolean hasNext = list.hasNext();
        List<EduComment> records = list.getRecords();


        return R.ok().data("total",total)
                .data("pages",pages)
                .data("hasPrevious",hasPrevious)
                .data("hasNext",hasNext)
                .data("records",records);
    }

}

