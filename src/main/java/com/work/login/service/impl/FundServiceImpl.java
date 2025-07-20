package com.work.login.service.impl;

import com.work.login.entity.Fund;
import com.work.login.service.FundService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基金服务实现类
 */
@Service
public class FundServiceImpl implements FundService {

    // 模拟基金数据
    private final List<Fund> fundData = new ArrayList<>();

    public FundServiceImpl() {
        initFundData();
    }

    /**
     * 初始化基金数据
     */
    private void initFundData() {
        fundData.add(createFund("000001", "华夏成长混合", "混合型", "华夏基金", "张经理", "中风险", 
                new BigDecimal("1.2345"), new BigDecimal("2.35"), new BigDecimal("5.67"), new BigDecimal("12.45")));
        fundData.add(createFund("000002", "易方达消费行业股票", "股票型", "易方达基金", "李经理", "中高风险", 
                new BigDecimal("2.5678"), new BigDecimal("-1.23"), new BigDecimal("3.45"), new BigDecimal("8.90")));
        fundData.add(createFund("000003", "博时信用债券A", "债券型", "博时基金", "王经理", "中低风险", 
                new BigDecimal("1.1234"), new BigDecimal("0.45"), new BigDecimal("1.23"), new BigDecimal("4.56")));
        fundData.add(createFund("000004", "嘉实货币A", "货币型", "嘉实基金", "赵经理", "低风险", 
                new BigDecimal("1.0000"), new BigDecimal("0.12"), new BigDecimal("0.35"), new BigDecimal("1.45")));
        fundData.add(createFund("000005", "南方中证500ETF", "指数型", "南方基金", "陈经理", "中高风险", 
                new BigDecimal("3.4567"), new BigDecimal("3.67"), new BigDecimal("7.89"), new BigDecimal("15.67")));
        fundData.add(createFund("000006", "工银瑞信债券A", "债券型", "工银瑞信基金", "刘经理", "中低风险", 
                new BigDecimal("1.1234"), new BigDecimal("0.38"), new BigDecimal("1.15"), new BigDecimal("4.23")));
        fundData.add(createFund("000007", "广发稳健增长混合", "混合型", "广发基金", "孙经理", "中风险", 
                new BigDecimal("1.4567"), new BigDecimal("1.89"), new BigDecimal("4.56"), new BigDecimal("10.23")));
        fundData.add(createFund("000008", "招商中证白酒指数", "指数型", "招商基金", "周经理", "中高风险", 
                new BigDecimal("2.7890"), new BigDecimal("2.45"), new BigDecimal("6.78"), new BigDecimal("18.90")));
    }

    private Fund createFund(String code, String name, String type, String company, String manager, String risk,
                           BigDecimal nav, BigDecimal yield1m, BigDecimal yield3m, BigDecimal yield1y) {
        Fund fund = new Fund(code, name, type, company, manager, risk);
        fund.setNav(nav);
        fund.setNavDate(LocalDateTime.now());
        fund.setYield1m(yield1m);
        fund.setYield3m(yield3m);
        fund.setYield1y(yield1y);
        fund.setCreateTime(LocalDateTime.now());
        fund.setUpdateTime(LocalDateTime.now());
        return fund;
    }

    @Override
    public List<Fund> getFundList(int page, int size, String code, String name, String type, String risk) {
        List<Fund> filteredFunds = fundData.stream()
                .filter(fund -> code == null || fund.getCode().contains(code))
                .filter(fund -> name == null || fund.getName().contains(name))
                .filter(fund -> type == null || fund.getType().equals(type))
                .filter(fund -> risk == null || fund.getRisk().equals(risk))
                .collect(Collectors.toList());

        int start = (page - 1) * size;
        int end = Math.min(start + size, filteredFunds.size());
        
        if (start >= filteredFunds.size()) {
            return new ArrayList<>();
        }
        
        return filteredFunds.subList(start, end);
    }

    @Override
    public int getFundCount(String code, String name, String type, String risk) {
        return (int) fundData.stream()
                .filter(fund -> code == null || fund.getCode().contains(code))
                .filter(fund -> name == null || fund.getName().contains(name))
                .filter(fund -> type == null || fund.getType().equals(type))
                .filter(fund -> risk == null || fund.getRisk().equals(risk))
                .count();
    }

    @Override
    public Fund getFundByCode(String code) {
        return fundData.stream()
                .filter(fund -> fund.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> getCompanyList() {
        return fundData.stream()
                .map(Fund::getCompany)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getManagerList() {
        return fundData.stream()
                .map(Fund::getManager)
                .distinct()
                .collect(Collectors.toList());
    }
} 