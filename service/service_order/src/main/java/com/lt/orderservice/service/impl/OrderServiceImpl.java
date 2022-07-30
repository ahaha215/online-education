package com.lt.orderservice.service.impl;

import com.lt.orderservice.client.EduClient;
import com.lt.orderservice.client.UcenterClient;
import com.lt.orderservice.entity.Order;
import com.lt.orderservice.entity.UcenterMember;
import com.lt.orderservice.entity.vo.BaseCourseVo;
import com.lt.orderservice.mapper.OrderMapper;
import com.lt.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.orderservice.utils.OrderNoUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {

        // 获取课程基本信息
        BaseCourseVo baseCourse = eduClient.getBaseCourse(courseId);

        // 获取登录购买用户基本信息
        UcenterMember userInfo = ucenterClient.getInfo(memberId);

        Order order = new Order();

        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        order.setCourseId(baseCourse.getId());
        order.setCourseTitle(baseCourse.getTitle());
        order.setCourseCover(baseCourse.getCover());
        order.setTeacherName(baseCourse.getTeacherName());

        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());

        order.setTotalFee(baseCourse.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        baseMapper.insert(order);


        return orderNo;
    }
}
