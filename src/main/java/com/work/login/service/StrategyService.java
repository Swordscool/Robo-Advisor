package com.work.login.service;

import com.work.login.entity.Strategy;
import java.util.List;
import java.util.Map;

/**
 * 策略服务接口
 */
public interface StrategyService {
    
    /**
     * 获取策略列表
     */
    List<Strategy> getStrategyList();
    
    /**
     * 创建策略
     */
    Strategy createStrategy(Strategy strategy);
    
    /**
     * 根据ID获取策略
     */
    Strategy getStrategyById(Integer id);
    
    /**
     * 更新策略
     */
    Strategy updateStrategy(Strategy strategy);
    
    /**
     * 删除策略
     */
    boolean deleteStrategy(Integer id);
    
    /**
     * 策略回测
     */
    Map<String, Object> backtestStrategy(Integer id);
} 