package com.work.login.controller;

import com.work.login.entity.TradeOrder;
import com.work.login.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易控制器
 */
@RestController
@RequestMapping("/api/trade")
@CrossOrigin(origins = "*")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    /**
     * 获取交易订单列表
     */
    @GetMapping("/order/list")
    public Map<String, Object> getOrderList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderType) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<TradeOrder> orders = tradeService.getOrderList(page, size, status, orderType);
            int total = tradeService.getOrderCount(status, orderType);
            
            result.put("success", true);
            result.put("data", orders);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
            result.put("message", "获取交易订单列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取交易订单列表失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 创建交易订单
     */
    @PostMapping("/order/create")
    public Map<String, Object> createOrder(@RequestBody TradeOrder order) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (order.getFundCode() == null || order.getFundCode().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "基金代码不能为空");
                return result;
            }
            
            if (order.getAmount() == null || order.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                result.put("success", false);
                result.put("message", "交易金额必须大于0");
                return result;
            }
            
            TradeOrder createdOrder = tradeService.createOrder(order);
            result.put("success", true);
            result.put("data", createdOrder);
            result.put("message", "交易订单创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "创建交易订单失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/order/detail/{orderId}")
    public Map<String, Object> getOrderDetail(@PathVariable String orderId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            TradeOrder order = tradeService.getOrderById(orderId);
            if (order != null) {
                result.put("success", true);
                result.put("data", order);
                result.put("message", "获取订单详情成功");
            } else {
                result.put("success", false);
                result.put("message", "订单不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取订单详情失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 取消订单
     */
    @PostMapping("/order/cancel/{orderId}")
    public Map<String, Object> cancelOrder(@PathVariable String orderId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            boolean cancelled = tradeService.cancelOrder(orderId);
            if (cancelled) {
                result.put("success", true);
                result.put("message", "订单取消成功");
            } else {
                result.put("success", false);
                result.put("message", "订单取消失败或订单不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "取消订单失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取交易统计
     */
    @GetMapping("/statistics")
    public Map<String, Object> getTradeStatistics() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> statistics = tradeService.getTradeStatistics();
            result.put("success", true);
            result.put("data", statistics);
            result.put("message", "获取交易统计成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取交易统计失败: " + e.getMessage());
        }
        
        return result;
    }
} 