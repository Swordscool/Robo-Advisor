-- 智能投顾系统数据库脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS login_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE login_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 基金表
CREATE TABLE IF NOT EXISTS `fund` (
    `code` VARCHAR(10) PRIMARY KEY COMMENT '基金代码',
    `name` VARCHAR(100) NOT NULL COMMENT '基金名称',
    `type` VARCHAR(20) NOT NULL COMMENT '基金类型',
    `company` VARCHAR(50) NOT NULL COMMENT '基金公司',
    `manager` VARCHAR(50) COMMENT '基金经理',
    `risk` VARCHAR(20) COMMENT '风险等级',
    `nav` DECIMAL(10,4) COMMENT '最新净值',
    `nav_date` DATETIME COMMENT '净值日期',
    `yield_1m` DECIMAL(8,2) COMMENT '近1月收益率',
    `yield_3m` DECIMAL(8,2) COMMENT '近3月收益率',
    `yield_1y` DECIMAL(8,2) COMMENT '近1年收益率',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基金表';

-- 策略表
CREATE TABLE IF NOT EXISTS `strategy` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL COMMENT '策略名称',
    `type` VARCHAR(20) NOT NULL COMMENT '策略类型',
    `status` VARCHAR(20) DEFAULT '暂停' COMMENT '状态',
    `description` TEXT COMMENT '策略描述',
    `benchmark` VARCHAR(50) COMMENT '基准指数',
    `stock_ratio` DECIMAL(5,2) COMMENT '股票配置比例',
    `bond_ratio` DECIMAL(5,2) COMMENT '债券配置比例',
    `mixed_ratio` DECIMAL(5,2) COMMENT '混合配置比例',
    `money_ratio` DECIMAL(5,2) COMMENT '货币配置比例',
    `max_drawdown` DECIMAL(5,2) COMMENT '最大回撤限制',
    `volatility` DECIMAL(5,2) COMMENT '年化波动率',
    `sharpe_ratio` DECIMAL(5,2) COMMENT '夏普比率目标',
    `rebalance_freq` VARCHAR(20) COMMENT '再平衡频率',
    `yield` DECIMAL(8,2) COMMENT '累计收益率',
    `risk` VARCHAR(20) COMMENT '风险等级',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略表';

-- 交易订单表
CREATE TABLE IF NOT EXISTS `trade_order` (
    `order_id` VARCHAR(20) PRIMARY KEY COMMENT '订单ID',
    `fund_code` VARCHAR(10) NOT NULL COMMENT '基金代码',
    `fund_name` VARCHAR(100) NOT NULL COMMENT '基金名称',
    `order_type` VARCHAR(10) NOT NULL COMMENT '订单类型',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '交易金额',
    `shares` DECIMAL(12,4) COMMENT '交易份额',
    `price` DECIMAL(10,4) COMMENT '交易价格',
    `status` VARCHAR(20) DEFAULT '处理中' COMMENT '订单状态',
    `user_id` VARCHAR(20) NOT NULL COMMENT '用户ID',
    `order_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    `confirm_time` DATETIME COMMENT '确认时间',
    `remark` VARCHAR(255) COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_fund_code` (`fund_code`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易订单表';

-- 插入测试数据

-- 用户数据
INSERT INTO `user` (`username`, `password`, `email`, `phone`) VALUES
('admin', '123456', 'admin@example.com', '13800138000'),
('user001', '123456', 'user001@example.com', '13800138001'),
('user002', '123456', 'user002@example.com', '13800138002');

-- 基金数据
INSERT INTO `fund` (`code`, `name`, `type`, `company`, `manager`, `risk`, `nav`, `nav_date`, `yield_1m`, `yield_3m`, `yield_1y`) VALUES
('000001', '华夏成长混合', '混合型', '华夏基金', '张经理', '中风险', 1.2345, NOW(), 2.35, 5.67, 12.45),
('000002', '易方达消费行业股票', '股票型', '易方达基金', '李经理', '中高风险', 2.5678, NOW(), -1.23, 3.45, 8.90),
('000003', '博时信用债券A', '债券型', '博时基金', '王经理', '中低风险', 1.1234, NOW(), 0.45, 1.23, 4.56),
('000004', '嘉实货币A', '货币型', '嘉实基金', '赵经理', '低风险', 1.0000, NOW(), 0.12, 0.35, 1.45),
('000005', '南方中证500ETF', '指数型', '南方基金', '陈经理', '中高风险', 3.4567, NOW(), 3.67, 7.89, 15.67),
('000006', '工银瑞信债券A', '债券型', '工银瑞信基金', '刘经理', '中低风险', 1.1234, NOW(), 0.38, 1.15, 4.23),
('000007', '广发稳健增长混合', '混合型', '广发基金', '孙经理', '中风险', 1.4567, NOW(), 1.89, 4.56, 10.23),
('000008', '招商中证白酒指数', '指数型', '招商基金', '周经理', '中高风险', 2.7890, NOW(), 2.45, 6.78, 18.90);

-- 策略数据
INSERT INTO `strategy` (`name`, `type`, `status`, `description`, `benchmark`, `stock_ratio`, `bond_ratio`, `mixed_ratio`, `money_ratio`, `yield`, `risk`) VALUES
('稳健配置策略', '稳健型', '运行中', '适合稳健型投资者的资产配置策略', '沪深300', 40.00, 30.00, 20.00, 10.00, 8.50, '中风险'),
('成长配置策略', '成长型', '运行中', '适合成长型投资者的资产配置策略', '中证500', 60.00, 20.00, 15.00, 5.00, 12.30, '中高风险'),
('保守配置策略', '保守型', '暂停', '适合保守型投资者的资产配置策略', '中债总财富', 20.00, 50.00, 20.00, 10.00, 4.20, '低风险');

-- 交易订单数据
INSERT INTO `trade_order` (`order_id`, `fund_code`, `fund_name`, `order_type`, `amount`, `status`, `user_id`, `order_time`) VALUES
('T10001', '000001', '华夏成长混合', '申购', 10000.00, '已确认', 'user001', DATE_SUB(NOW(), INTERVAL 5 DAY)),
('T10002', '000002', '易方达消费行业股票', '申购', 5000.00, '处理中', 'user001', DATE_SUB(NOW(), INTERVAL 2 DAY)),
('T10003', '000003', '博时信用债券A', '赎回', 2000.00, '已确认', 'user001', DATE_SUB(NOW(), INTERVAL 10 DAY)),
('T10004', '000004', '嘉实货币A', '申购', 15000.00, '已确认', 'user002', DATE_SUB(NOW(), INTERVAL 3 DAY)),
('T10005', '000005', '南方中证500ETF', '申购', 8000.00, '已取消', 'user002', DATE_SUB(NOW(), INTERVAL 1 DAY)); 