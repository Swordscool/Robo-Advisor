package com.work.login.service.impl;

import com.work.login.entity.TradeOrder;
import com.work.login.service.TradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 交易服务实现类
 */
@Service
public class TradeServiceImpl implements TradeService {

    // 模拟交易订单数据
    private final List<TradeOrder> orderData = new ArrayList<>();
    private final AtomicInteger orderIdGenerator = new AtomicInteger(10001);

    public TradeServiceImpl() {
        initOrderData();
    }

    /**
     * 初始化交易订单数据
     */
    private void initOrderData() {
        orderData.add(createOrder("000001", "华夏成长混合", "申购", new BigDecimal("10000"), "已确认"));
        orderData.add(createOrder("000002", "易方达消费行业股票", "申购", new BigDecimal("5000"), "处理中"));
        orderData.add(createOrder("000003", "博时信用债券A", "赎回", new BigDecimal("2000"), "已确认"));
        orderData.add(createOrder("000004", "嘉实货币A", "申购", new BigDecimal("15000"), "已确认"));
        orderData.add(createOrder("000005", "南方中证500ETF", "申购", new BigDecimal("8000"), "已取消"));
    }

    private TradeOrder createOrder(String fundCode, String fundName, String orderType, BigDecimal amount, String status) {
        String orderId = "T" + orderIdGenerator.getAndIncrement();
        TradeOrder order = new TradeOrder(orderId, fundCode, fundName, orderType, amount);
        order.setStatus(status);
        order.setUserId("user001");
        order.setOrderTime(LocalDateTime.now().minusDays(new Random().nextInt(30)));
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        if ("已确认".equals(status)) {
            order.setConfirmTime(LocalDateTime.now().minusDays(new Random().nextInt(5)));
            order.setPrice(new BigDecimal("1.2345"));
            order.setShares(amount.divide(order.getPrice(), 2, BigDecimal.ROUND_HALF_UP));
        }
        
        return order;
    }

    @Override
    public List<TradeOrder> getOrderList(int page, int size, String status, String orderType) {
        List<TradeOrder> filteredOrders = orderData.stream()
                .filter(order -> status == null || order.getStatus().equals(status))
                .filter(order -> orderType == null || order.getOrderType().equals(orderType))
                .sorted((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        int start = (page - 1) * size;
        int end = Math.min(start + size, filteredOrders.size());
        
        if (start >= filteredOrders.size()) {
            return new ArrayList<>();
        }
        
        return filteredOrders.subList(start, end);
    }

    @Override
    public int getOrderCount(String status, String orderType) {
        return (int) orderData.stream()
                .filter(order -> status == null || order.getStatus().equals(status))
                .filter(order -> orderType == null || order.getOrderType().equals(orderType))
                .count();
    }

    @Override
    public TradeOrder createOrder(TradeOrder order) {
        order.setOrderId("T" + orderIdGenerator.getAndIncrement());
        order.setStatus("处理中");
        order.setOrderTime(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderData.add(order);
        return order;
    }

    @Override
    public TradeOrder getOrderById(String orderId) {
        return orderData.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean cancelOrder(String orderId) {
        for (TradeOrder order : orderData) {
            if (order.getOrderId().equals(orderId) && "处理中".equals(order.getStatus())) {
                order.setStatus("已取消");
                order.setUpdateTime(LocalDateTime.now());
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getTradeStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 统计总订单数
        long totalOrders = orderData.size();
        statistics.put("totalOrders", totalOrders);
        
        // 统计各状态订单数
        long pendingOrders = orderData.stream().filter(o -> "处理中".equals(o.getStatus())).count();
        long confirmedOrders = orderData.stream().filter(o -> "已确认".equals(o.getStatus())).count();
        long cancelledOrders = orderData.stream().filter(o -> "已取消".equals(o.getStatus())).count();
        
        statistics.put("pendingOrders", pendingOrders);
        statistics.put("confirmedOrders", confirmedOrders);
        statistics.put("cancelledOrders", cancelledOrders);
        
        // 统计交易金额
        BigDecimal totalAmount = orderData.stream()
                .filter(o -> "已确认".equals(o.getStatus()))
                .map(TradeOrder::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        statistics.put("totalAmount", totalAmount);
        
        // 统计今日交易
        long todayOrders = orderData.stream()
                .filter(o -> o.getOrderTime().toLocalDate().equals(LocalDateTime.now().toLocalDate()))
                .count();
        statistics.put("todayOrders", todayOrders);
        
        return statistics;
    }
} 