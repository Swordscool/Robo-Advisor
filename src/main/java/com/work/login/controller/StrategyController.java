package com.work.login.controller;

import com.work.login.entity.Strategy;
import com.work.login.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 策略控制器
 */
@RestController
@RequestMapping("/api/strategy")
@CrossOrigin(origins = "*")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    /**
     * 获取策略列表
     */
    @GetMapping("/list")
    public Map<String, Object> getStrategyList() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Strategy> strategies = strategyService.getStrategyList();
            result.put("success", true);
            result.put("data", strategies);
            result.put("message", "获取策略列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取策略列表失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 创建策略
     */
    @PostMapping("/create")
    public Map<String, Object> createStrategy(@RequestBody Strategy strategy) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (strategy.getName() == null || strategy.getName().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "策略名称不能为空");
                return result;
            }
            
            Strategy createdStrategy = strategyService.createStrategy(strategy);
            result.put("success", true);
            result.put("data", createdStrategy);
            result.put("message", "策略创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "创建策略失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取策略详情
     */
    @GetMapping("/detail/{id}")
    public Map<String, Object> getStrategyDetail(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Strategy strategy = strategyService.getStrategyById(id);
            if (strategy != null) {
                result.put("success", true);
                result.put("data", strategy);
                result.put("message", "获取策略详情成功");
            } else {
                result.put("success", false);
                result.put("message", "策略不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取策略详情失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 更新策略
     */
    @PutMapping("/update/{id}")
    public Map<String, Object> updateStrategy(@PathVariable Integer id, @RequestBody Strategy strategy) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            strategy.setId(id);
            Strategy updatedStrategy = strategyService.updateStrategy(strategy);
            if (updatedStrategy != null) {
                result.put("success", true);
                result.put("data", updatedStrategy);
                result.put("message", "策略更新成功");
            } else {
                result.put("success", false);
                result.put("message", "策略不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新策略失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 删除策略
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteStrategy(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            boolean deleted = strategyService.deleteStrategy(id);
            if (deleted) {
                result.put("success", true);
                result.put("message", "策略删除成功");
            } else {
                result.put("success", false);
                result.put("message", "策略不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除策略失败: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 策略回测
     */
    @PostMapping("/backtest/{id}")
    public Map<String, Object> backtestStrategy(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> backtestResult = strategyService.backtestStrategy(id);
            result.put("success", true);
            result.put("data", backtestResult);
            result.put("message", "策略回测完成");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "策略回测失败: " + e.getMessage());
        }
        
        return result;
    }
} 