package com.work.login.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 基金实体类
 */
public class Fund {
    private String code;           // 基金代码
    private String name;           // 基金名称
    private String type;           // 基金类型
    private String company;        // 基金公司
    private String manager;        // 基金经理
    private String risk;           // 风险等级
    private BigDecimal nav;        // 最新净值
    private LocalDateTime navDate; // 净值日期
    private BigDecimal yield1m;    // 近1月收益率
    private BigDecimal yield3m;    // 近3月收益率
    private BigDecimal yield1y;    // 近1年收益率
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 构造函数
    public Fund() {}

    public Fund(String code, String name, String type, String company, String manager, String risk) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.company = company;
        this.manager = manager;
        this.risk = risk;
    }

    // Getter和Setter方法
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public BigDecimal getNav() {
        return nav;
    }

    public void setNav(BigDecimal nav) {
        this.nav = nav;
    }

    public LocalDateTime getNavDate() {
        return navDate;
    }

    public void setNavDate(LocalDateTime navDate) {
        this.navDate = navDate;
    }

    public BigDecimal getYield1m() {
        return yield1m;
    }

    public void setYield1m(BigDecimal yield1m) {
        this.yield1m = yield1m;
    }

    public BigDecimal getYield3m() {
        return yield3m;
    }

    public void setYield3m(BigDecimal yield3m) {
        this.yield3m = yield3m;
    }

    public BigDecimal getYield1y() {
        return yield1y;
    }

    public void setYield1y(BigDecimal yield1y) {
        this.yield1y = yield1y;
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
        return "Fund{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", company='" + company + '\'' +
                ", manager='" + manager + '\'' +
                ", risk='" + risk + '\'' +
                ", nav=" + nav +
                ", navDate=" + navDate +
                ", yield1m=" + yield1m +
                ", yield3m=" + yield3m +
                ", yield1y=" + yield1y +
                '}';
    }
} 