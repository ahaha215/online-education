package com.lt.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.commonutils.JwtUtils;
import com.lt.commonutils.R;
import com.lt.orderservice.entity.Order;
import com.lt.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-21
 */
@RestController
@CrossOrigin
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    // 创建订单
    @GetMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){

        // 获取登录用户 id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请先登录!");
        }

        String orderNo = orderService.createOrder(courseId,memberId);

        return R.ok().data("orderNo",orderNo);
    }

    // 查询订单信息
    @GetMapping("getOrder/{orderNo}")
    public R getOrder(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);

        Order order = orderService.getOne(queryWrapper);

        return R.ok().data("order",order);
    }

    // 查询用户对课程的购买情况
    @GetMapping("isBuy/{courseId}")
    public R isBuy(@PathVariable("courseId") String courseId,HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("course_id",courseId);

        Order theOrder  = orderService.getOne(queryWrapper);

        if (theOrder != null){
            return R.ok().data("flag",true);
        }

        return R.ok().data("flag",false);
    }

}

