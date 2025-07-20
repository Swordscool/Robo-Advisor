package com.work.login.controller;

import com.work.login.entity.Fund;
import com.work.login.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基金控制器
 */
@RestController
@RequestMapping("/api/fund")
@CrossOrigin(origins = "*")
public class FundController {

    @Autowired
    private FundService fundService;

    /**
     * 获取基金列表
     */
    @GetMapping("/list")
    public Map<String, Object> getFundList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String risk) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Fund> funds = fundService.getFundList(page, size, code, name, type, risk);
            int total = fundService.getFundCount(code, name, type, risk);
            
            result.put("success", true);
            result.put("data", funds);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
            result.put("message", "获取基金列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取基金列表失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取基金详情
     */
    @GetMapping("/detail/{code}")
    public Map<String, Object> getFundDetail(@PathVariable String code) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Fund fund = fundService.getFundByCode(code);
            if (fund != null) {
                result.put("success", true);
                result.put("data", fund);
                result.put("message", "获取基金详情成功");
            } else {
                result.put("success", false);
                result.put("message", "基金不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取基金详情失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取基金公司列表
     */
    @GetMapping("/company/list")
    public Map<String, Object> getCompanyList() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<String> companies = fundService.getCompanyList();
            result.put("success", true);
            result.put("data", companies);
            result.put("message", "获取基金公司列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取基金公司列表失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取基金经理列表
     */
    @GetMapping("/manager/list")
    public Map<String, Object> getManagerList() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<String> managers = fundService.getManagerList();
            result.put("success", true);
            result.put("data", managers);
            result.put("message", "获取基金经理列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取基金经理列表失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 创建基金组合
     */
    @PostMapping("/portfolio/create")
    public Map<String, Object> createPortfolio(@RequestBody Map<String, Object> portfolioData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String name = (String) portfolioData.get("name");
            String description = (String) portfolioData.get("description");
            String risk = (String) portfolioData.get("risk");
            
            if (name == null || name.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "组合名称不能为空");
                return result;
            }
            
            // 这里可以调用服务创建基金组合
            result.put("success", true);
            result.put("message", "基金组合创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "创建基金组合失败: " + e.getMessage());
        }
        
        return result;
    }
} 