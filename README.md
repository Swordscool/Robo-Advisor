# 智能投顾系统

## 项目简介

智能投顾系统是一个基于Vue3 + SpringBoot + MyBatis的现代化投资顾问平台，为用户提供智能化的基金投资管理服务。

## 开发成员及分工
- 张怡佳：前端开发
- 李奕涵：后端开发
- 于向前：系统设计
- 邵熠轲：测试

## 开源仓库完整地址
[https://github.com/Swordscool/Robo-Advisor]

## 系统功能

### 1. 基金研究模块
- **基金列表管理**：展示基金基本信息、净值、收益率等
- **基金详情分析**：提供基金详细信息和历史表现
- **基金公司管理**：管理基金公司信息
- **基金经理管理**：跟踪基金经理业绩

### 2. 因子管理模块
- **因子树管理**：构建和管理投资因子体系
- **因子风格分析**：分析不同因子风格的表现
- **因子衍生管理**：创建和管理衍生因子
- **因子接入管理**：管理因子数据接入

### 3. 策略管理模块
- **大类资产配置策略**：股票、债券、混合、货币配置
- **策略回测**：历史数据回测验证
- **策略平衡**：动态再平衡管理
- **策略监控**：实时策略运行监控
- **FOF策略**：基金中基金策略
- **指数策略**：指数化投资策略
- **择时策略**：市场择时策略

### 4. 组合产品管理模块
- **组合创建**：创建个性化投资组合
- **组合详情**：查看组合详细信息和表现
- **组合审计**：组合合规性审计

### 5. 交易管理模块
- **交易订单管理**：申购、赎回订单处理
- **交易账户管理**：账户资金管理
- **交易结算**：交易结算处理
- **交易错误处理**：异常交易处理
- **交易再平衡**：自动再平衡交易

## 技术架构

### 前端技术栈
- **Vue 3**：渐进式JavaScript框架
- **Vue Router**：官方路由管理器
- **Vite**：下一代前端构建工具
- **Element Plus**：Vue 3组件库

### 后端技术栈
- **Spring Boot 2.7**：快速开发框架
- **MyBatis**：持久层框架
- **MySQL 8.0**：关系型数据库
- **Maven**：项目构建工具

## 项目结构

```
login/
├── login-fronted/          # 前端项目
│   ├── src/
│   │   ├── components/     # 组件
│   │   ├── views/          # 页面
│   │   ├── router/         # 路由配置
│   │   └── main.js         # 入口文件
│   ├── public/             # 静态资源
│   └── package.json        # 依赖配置
├── src/main/java/          # 后端源码
│   └── com/work/login/
│       ├── controller/     # 控制器层
│       ├── service/        # 服务层
│       ├── entity/         # 实体类
│       └── mapper/         # 数据访问层
├── src/main/resources/     # 配置文件
├── database.sql            # 数据库脚本
└── pom.xml                 # Maven配置
```

## 快速开始

### 环境要求
- Node.js 16+
- Java 8+
- MySQL 8.0+
- Maven 3.6+

### 数据库配置
1. 创建MySQL数据库
2. 执行 `database.sql` 脚本创建表结构和测试数据

### 后端启动
```bash
# 进入项目根目录
cd login

# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

### 前端启动
```bash
# 进入前端目录
cd login-fronted

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

### 访问地址
- 前端：http://localhost:5173
- 后端API：http://localhost:8080

## API接口

### 用户管理
- `POST /api/user/login` - 用户登录

### 基金管理
- `GET /api/fund/list` - 获取基金列表
- `GET /api/fund/detail/{code}` - 获取基金详情
- `GET /api/fund/company/list` - 获取基金公司列表
- `GET /api/fund/manager/list` - 获取基金经理列表

### 策略管理
- `GET /api/strategy/list` - 获取策略列表
- `POST /api/strategy/create` - 创建策略
- `GET /api/strategy/detail/{id}` - 获取策略详情
- `PUT /api/strategy/update/{id}` - 更新策略
- `DELETE /api/strategy/delete/{id}` - 删除策略
- `POST /api/strategy/backtest/{id}` - 策略回测

### 交易管理
- `GET /api/trade/order/list` - 获取交易订单列表
- `POST /api/trade/order/create` - 创建交易订单
- `GET /api/trade/order/detail/{orderId}` - 获取订单详情
- `POST /api/trade/order/cancel/{orderId}` - 取消订单
- `GET /api/trade/statistics` - 获取交易统计

### 系统概览
- `GET /api/overview/dashboard` - 获取系统概览数据
- `GET /api/overview/asset-allocation` - 获取资产配置统计
- `GET /api/overview/yield-trend` - 获取收益趋势数据
- `GET /api/overview/risk-metrics` - 获取风险指标数据

## 测试账号

系统预设了以下测试账号：
- 用户名：admin，密码：123456
- 用户名：user001，密码：123456
- 用户名：user002，密码：123456

## 开发说明

### 前端开发
- 使用Vue 3 Composition API
- 采用Element Plus组件库
- 支持响应式设计

### 后端开发
- 采用分层架构设计
- 使用MyBatis进行数据访问
- 提供RESTful API接口
- 支持跨域请求

### 数据库设计
- 用户表：存储用户基本信息
- 基金表：存储基金详细信息
- 策略表：存储投资策略配置
- 交易订单表：存储交易记录

## 部署说明

### 生产环境部署
1. 修改 `application.properties` 中的数据库配置
2. 打包前端项目：`npm run build`
3. 打包后端项目：`mvn clean package`
4. 部署到服务器
