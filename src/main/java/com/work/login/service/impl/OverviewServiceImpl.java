package com.work.login.service.impl;

import com.work.login.service.OverviewService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 系统概览服务实现类
 */
@Service
public class OverviewServiceImpl implements OverviewService {

    @Override
    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        
        // 总资产
        data.put("totalAssets", new BigDecimal("1250000.00"));
        
        // 今日收益
        data.put("todayYield", new BigDecimal("1250.50"));
        
        // 累计收益
        data.put("totalYield", new BigDecimal("45678.90"));
        
        // 收益率
        data.put("yieldRate", new BigDecimal("3.78"));
        
        // 风险等级
        data.put("riskLevel", "中风险");
        
        // 持仓基金数
        data.put("fundCount", 8);
        
        // 运行策略数
        data.put("strategyCount", 3);
        
        // 今日交易数
        data.put("todayTrades", 5);
        
        return data;
    }

    @Override
    public Map<String, Object> getAssetAllocation() {
        Map<String, Object> data = new HashMap<>();
        
        // 资产配置数据
        List<Map<String, Object>> allocation = new ArrayList<>();
        
        Map<String, Object> stock = new HashMap<>();
        stock.put("name", "股票型基金");
        stock.put("value", new BigDecimal("500000"));
        stock.put("percentage", new BigDecimal("40.0"));
        allocation.add(stock);
        
        Map<String, Object> bond = new HashMap<>();
        bond.put("name", "债券型基金");
        bond.put("value", new BigDecimal("375000"));
        bond.put("percentage", new BigDecimal("30.0"));
        allocation.add(bond);
        
        Map<String, Object> mixed = new HashMap<>();
        mixed.put("name", "混合型基金");
        mixed.put("value", new BigDecimal("250000"));
        mixed.put("percentage", new BigDecimal("20.0"));
        allocation.add(mixed);
        
        Map<String, Object> money = new HashMap<>();
        money.put("name", "货币型基金");
        money.put("value", new BigDecimal("125000"));
        money.put("percentage", new BigDecimal("10.0"));
        allocation.add(money);
        
        data.put("allocation", allocation);
        data.put("totalValue", new BigDecimal("1250000"));
        
        return data;
    }

    @Override
    public Map<String, Object> getYieldTrend() {
        Map<String, Object> data = new HashMap<>();
        
        // 收益趋势数据（最近30天）
        List<Map<String, Object>> trend = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<BigDecimal> yields = new ArrayList<>();
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        
        for (int i = 0; i < 30; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String date = String.format("%02d-%02d", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            dates.add(date);
            
            // 模拟收益数据
            BigDecimal yield = new BigDecimal("1000")
                    .add(new BigDecimal(Math.random() * 200 - 100))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            yields.add(yield);
        }
        
        data.put("dates", dates);
        data.put("yields", yields);
        
        return data;
    }

    @Override
    public Map<String, Object> getRiskMetrics() {
        Map<String, Object> data = new HashMap<>();
        
        // 风险指标
        data.put("volatility", new BigDecimal("12.5"));        // 年化波动率
        data.put("maxDrawdown", new BigDecimal("8.2"));        // 最大回撤
        data.put("sharpeRatio", new BigDecimal("1.25"));       // 夏普比率
        data.put("var95", new BigDecimal("2.1"));             // 95% VaR
        data.put("beta", new BigDecimal("0.85"));             // Beta系数
        data.put("correlation", new BigDecimal("0.72"));      // 相关系数
        
        // 风险等级分布
        List<Map<String, Object>> riskDistribution = new ArrayList<>();
        
        Map<String, Object> lowRisk = new HashMap<>();
        lowRisk.put("level", "低风险");
        lowRisk.put("count", 2);
        lowRisk.put("percentage", new BigDecimal("25.0"));
        riskDistribution.add(lowRisk);
        
        Map<String, Object> mediumRisk = new HashMap<>();
        mediumRisk.put("level", "中风险");
        mediumRisk.put("count", 4);
        mediumRisk.put("percentage", new BigDecimal("50.0"));
        riskDistribution.add(mediumRisk);
        
        Map<String, Object> highRisk = new HashMap<>();
        highRisk.put("level", "中高风险");
        highRisk.put("count", 2);
        highRisk.put("percentage", new BigDecimal("25.0"));
        riskDistribution.add(highRisk);
        
        data.put("riskDistribution", riskDistribution);
        
        return data;
    }
} 