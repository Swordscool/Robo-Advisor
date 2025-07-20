package com.work.login.service.impl;

import com.work.login.entity.Strategy;
import com.work.login.service.StrategyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 策略服务实现类
 */
@Service
public class StrategyServiceImpl implements StrategyService {

    // 模拟策略数据
    private final List<Strategy> strategyData = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public StrategyServiceImpl() {
        initStrategyData();
    }

    /**
     * 初始化策略数据
     */
    private void initStrategyData() {
        strategyData.add(createStrategy("稳健配置策略", "稳健型", "运行中", 
                new BigDecimal("8.5"), "中风险", 
                new BigDecimal("40"), new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("10")));
        strategyData.add(createStrategy("成长配置策略", "成长型", "运行中", 
                new BigDecimal("12.3"), "中高风险", 
                new BigDecimal("60"), new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("5")));
        strategyData.add(createStrategy("保守配置策略", "保守型", "暂停", 
                new BigDecimal("4.2"), "低风险", 
                new BigDecimal("20"), new BigDecimal("50"), new BigDecimal("20"), new BigDecimal("10")));
    }

    private Strategy createStrategy(String name, String type, String status, 
                                   BigDecimal yield, String risk,
                                   BigDecimal stockRatio, BigDecimal bondRatio, 
                                   BigDecimal mixedRatio, BigDecimal moneyRatio) {
        Strategy strategy = new Strategy(name, type, status);
        strategy.setId(idGenerator.getAndIncrement());
        strategy.setYield(yield);
        strategy.setRisk(risk);
        strategy.setStockRatio(stockRatio);
        strategy.setBondRatio(bondRatio);
        strategy.setMixedRatio(mixedRatio);
        strategy.setMoneyRatio(moneyRatio);
        strategy.setCreateTime(LocalDateTime.now());
        strategy.setUpdateTime(LocalDateTime.now());
        
        // 设置资产配置列表
        List<Strategy.StrategyAsset> assets = new ArrayList<>();
        assets.add(new Strategy.StrategyAsset("股票", stockRatio));
        assets.add(new Strategy.StrategyAsset("债券", bondRatio));
        assets.add(new Strategy.StrategyAsset("混合", mixedRatio));
        assets.add(new Strategy.StrategyAsset("货币", moneyRatio));
        strategy.setAssets(assets);
        
        return strategy;
    }

    @Override
    public List<Strategy> getStrategyList() {
        return new ArrayList<>(strategyData);
    }

    @Override
    public Strategy createStrategy(Strategy strategy) {
        strategy.setId(idGenerator.getAndIncrement());
        strategy.setCreateTime(LocalDateTime.now());
        strategy.setUpdateTime(LocalDateTime.now());
        strategyData.add(strategy);
        return strategy;
    }

    @Override
    public Strategy getStrategyById(Integer id) {
        return strategyData.stream()
                .filter(strategy -> strategy.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Strategy updateStrategy(Strategy strategy) {
        for (int i = 0; i < strategyData.size(); i++) {
            if (strategyData.get(i).getId().equals(strategy.getId())) {
                strategy.setUpdateTime(LocalDateTime.now());
                strategyData.set(i, strategy);
                return strategy;
            }
        }
        return null;
    }

    @Override
    public boolean deleteStrategy(Integer id) {
        return strategyData.removeIf(strategy -> strategy.getId().equals(id));
    }

    @Override
    public Map<String, Object> backtestStrategy(Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        Strategy strategy = getStrategyById(id);
        if (strategy == null) {
            throw new RuntimeException("策略不存在");
        }
        
        // 模拟回测结果
        result.put("strategyName", strategy.getName());
        result.put("totalReturn", strategy.getYield());
        result.put("annualizedReturn", new BigDecimal("10.5"));
        result.put("maxDrawdown", new BigDecimal("8.2"));
        result.put("sharpeRatio", new BigDecimal("1.25"));
        result.put("volatility", new BigDecimal("12.3"));
        result.put("winRate", new BigDecimal("65.8"));
        result.put("backtestPeriod", "2023-01-01 至 2024-01-15");
        
        return result;
    }
} 