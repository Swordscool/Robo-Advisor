package com.work.login.controller;

import com.work.login.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统概览控制器
 */
@RestController
@RequestMapping("/api/overview")
@CrossOrigin(origins = "*")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    /**
     * 获取系统概览数据
     */
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> dashboardData = overviewService.getDashboardData();
            result.put("success", true);
            result.put("data", dashboardData);
            result.put("message", "获取系统概览数据成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取系统概览数据失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取资产配置统计
     */
    @GetMapping("/asset-allocation")
    public Map<String, Object> getAssetAllocation() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> assetData = overviewService.getAssetAllocation();
            result.put("success", true);
            result.put("data", assetData);
            result.put("message", "获取资产配置统计成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取资产配置统计失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取收益趋势数据
     */
    @GetMapping("/yield-trend")
    public Map<String, Object> getYieldTrend() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> trendData = overviewService.getYieldTrend();
            result.put("success", true);
            result.put("data", trendData);
            result.put("message", "获取收益趋势数据成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取收益趋势数据失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取风险指标数据
     */
    @GetMapping("/risk-metrics")
    public Map<String, Object> getRiskMetrics() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> riskData = overviewService.getRiskMetrics();
            result.put("success", true);
            result.put("data", riskData);
            result.put("message", "获取风险指标数据成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取风险指标数据失败: " + e.getMessage());
        }
        
        return result;
    }
} 