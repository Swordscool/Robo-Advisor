package com.work.login.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 策略实体类
 */
public class Strategy {
    private Integer id;                    // 策略ID
    private String name;                   // 策略名称
    private String type;                   // 策略类型
    private String status;                 // 状态
    private String description;            // 策略描述
    private String benchmark;              // 基准指数
    private BigDecimal stockRatio;         // 股票配置比例
    private BigDecimal bondRatio;          // 债券配置比例
    private BigDecimal mixedRatio;         // 混合配置比例
    private BigDecimal moneyRatio;         // 货币配置比例
    private BigDecimal maxDrawdown;        // 最大回撤限制
    private BigDecimal volatility;         // 年化波动率
    private BigDecimal sharpeRatio;        // 夏普比率目标
    private String rebalanceFreq;          // 再平衡频率
    private BigDecimal yield;              // 累计收益率
    private String risk;                   // 风险等级
    private LocalDateTime createTime;      // 创建时间
    private LocalDateTime updateTime;      // 更新时间
    private List<StrategyAsset> assets;    // 资产配置列表

    // 构造函数
    public Strategy() {}

    public Strategy(String name, String type, String status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public BigDecimal getStockRatio() {
        return stockRatio;
    }

    public void setStockRatio(BigDecimal stockRatio) {
        this.stockRatio = stockRatio;
    }

    public BigDecimal getBondRatio() {
        return bondRatio;
    }

    public void setBondRatio(BigDecimal bondRatio) {
        this.bondRatio = bondRatio;
    }

    public BigDecimal getMixedRatio() {
        return mixedRatio;
    }

    public void setMixedRatio(BigDecimal mixedRatio) {
        this.mixedRatio = mixedRatio;
    }

    public BigDecimal getMoneyRatio() {
        return moneyRatio;
    }

    public void setMoneyRatio(BigDecimal moneyRatio) {
        this.moneyRatio = moneyRatio;
    }

    public BigDecimal getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(BigDecimal maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public BigDecimal getVolatility() {
        return volatility;
    }

    public void setVolatility(BigDecimal volatility) {
        this.volatility = volatility;
    }

    public BigDecimal getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(BigDecimal sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public String getRebalanceFreq() {
        return rebalanceFreq;
    }

    public void setRebalanceFreq(String rebalanceFreq) {
        this.rebalanceFreq = rebalanceFreq;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<StrategyAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<StrategyAsset> assets) {
        this.assets = assets;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", yield=" + yield +
                ", risk='" + risk + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    /**
     * 策略资产配置类
     */
    public static class StrategyAsset {
        private String name;       // 资产名称
        private BigDecimal ratio;  // 配置比例

        public StrategyAsset() {}

        public StrategyAsset(String name, BigDecimal ratio) {
            this.name = name;
            this.ratio = ratio;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getRatio() {
            return ratio;
        }

        public void setRatio(BigDecimal ratio) {
            this.ratio = ratio;
        }
    }
} 