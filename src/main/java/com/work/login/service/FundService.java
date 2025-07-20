package com.work.login.service;

import com.work.login.entity.Fund;
import java.util.List;

/**
 * 基金服务接口
 */
public interface FundService {
    
    /**
     * 获取基金列表
     */
    List<Fund> getFundList(int page, int size, String code, String name, String type, String risk);
    
    /**
     * 获取基金总数
     */
    int getFundCount(String code, String name, String type, String risk);
    
    /**
     * 根据基金代码获取基金详情
     */
    Fund getFundByCode(String code);
    
    /**
     * 获取基金公司列表
     */
    List<String> getCompanyList();
    
    /**
     * 获取基金经理列表
     */
    List<String> getManagerList();
} 