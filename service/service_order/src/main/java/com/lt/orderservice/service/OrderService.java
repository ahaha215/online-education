package com.lt.orderservice.service;

import com.lt.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-21
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
