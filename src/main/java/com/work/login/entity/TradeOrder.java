package com.work.login.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易订单实体类
 */
public class TradeOrder {
    private String orderId;           // 订单ID
    private String fundCode;          // 基金代码
    private String fundName;          // 基金名称
    private String orderType;         // 订单类型（申购/赎回）
    private BigDecimal amount;        // 交易金额
    private BigDecimal shares;        // 交易份额
    private BigDecimal price;         // 交易价格
    private String status;            // 订单状态
    private String userId;            // 用户ID
    private LocalDateTime orderTime;  // 下单时间
    private LocalDateTime confirmTime; // 确认时间
    private String remark;            // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间

    // 构造函数
    public TradeOrder() {}

    public TradeOrder(String orderId, String fundCode, String fundName, String orderType, BigDecimal amount) {
        this.orderId = orderId;
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.orderType = orderType;
        this.amount = amount;
    }

    // Getter和Setter方法
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getShares() {
        return shares;
    }

    public void setShares(BigDecimal shares) {
        this.shares = shares;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "TradeOrder{" +
                "orderId='" + orderId + '\'' +
                ", fundCode='" + fundCode + '\'' +
                ", fundName='" + fundName + '\'' +
                ", orderType='" + orderType + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", orderTime=" + orderTime +
                '}';
    }
} 