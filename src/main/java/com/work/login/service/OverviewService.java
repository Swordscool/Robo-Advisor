package com.work.login.service;

import java.util.Map;

/**
 * 系统概览服务接口
 */
public interface OverviewService {
    
    /**
     * 获取系统概览数据
     */
    Map<String, Object> getDashboardData();
    
    /**
     * 获取资产配置统计
     */
    Map<String, Object> getAssetAllocation();
    
    /**
     * 获取收益趋势数据
     */
    Map<String, Object> getYieldTrend();
    
    /**
     * 获取风险指标数据
     */
    Map<String, Object> getRiskMetrics();
} 