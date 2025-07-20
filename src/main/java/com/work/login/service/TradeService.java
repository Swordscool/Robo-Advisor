package com.work.login.service;

import com.work.login.entity.TradeOrder;
import java.util.List;
import java.util.Map;

/**
 * 交易服务接口
 */
public interface TradeService {
    
    /**
     * 获取交易订单列表
     */
    List<TradeOrder> getOrderList(int page, int size, String status, String orderType);
    
    /**
     * 获取订单总数
     */
    int getOrderCount(String status, String orderType);
    
    /**
     * 创建交易订单
     */
    TradeOrder createOrder(TradeOrder order);
    
    /**
     * 根据订单ID获取订单
     */
    TradeOrder getOrderById(String orderId);
    
    /**
     * 取消订单
     */
    boolean cancelOrder(String orderId);
    
    /**
     * 获取交易统计
     */
    Map<String, Object> getTradeStatistics();
} 