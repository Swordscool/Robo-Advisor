<template>
  <div class="dashboard">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h2>智能投顾系统</h2>
      </div>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            <span class="username">{{ username }}</span>
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人信息</el-dropdown-item>
              <el-dropdown-item command="settings">系统设置</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <div class="main-container">
      <!-- 侧边栏 -->
      <el-aside width="250px" class="sidebar">
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          @select="handleMenuSelect"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
        >
          <el-menu-item index="/dashboard">
            <el-icon><House /></el-icon>
            <span>系统概览</span>
          </el-menu-item>
          
          <el-sub-menu index="fund">
            <template #title>
              <el-icon><TrendCharts /></el-icon>
              <span>基金研究</span>
            </template>
            <el-menu-item index="/dashboard/fund/list">全部公募基金</el-menu-item>
            <el-menu-item index="/dashboard/fund/company">基金公司</el-menu-item>
            <el-menu-item index="/dashboard/fund/manager">基金经理</el-menu-item>
            <el-menu-item index="/dashboard/fund/profile">基金画像</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="factor">
            <template #title>
              <el-icon><DataAnalysis /></el-icon>
              <span>因子管理</span>
            </template>
            <el-menu-item index="/dashboard/factor/access">因子接入</el-menu-item>
            <el-menu-item index="/dashboard/factor/tree">因子树管理</el-menu-item>
            <el-menu-item index="/dashboard/factor/derivative">创建衍生因子</el-menu-item>
            <el-menu-item index="/dashboard/factor/style">风格投资因子</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="strategy">
            <template #title>
              <el-icon><Operation /></el-icon>
              <span>策略管理</span>
            </template>
            <el-menu-item index="/dashboard/strategy/asset">大类资产配置</el-menu-item>
            <el-menu-item index="/dashboard/strategy/fof">FOF组合管理</el-menu-item>
            <el-menu-item index="/dashboard/strategy/index">基金指数组合</el-menu-item>
            <el-menu-item index="/dashboard/strategy/timing">择时组合</el-menu-item>
            <el-menu-item index="/dashboard/strategy/detail">策略详情</el-menu-item>
            <el-menu-item index="/dashboard/strategy/backtest">策略回测</el-menu-item>
            <el-menu-item index="/dashboard/strategy/balance">策略再平衡</el-menu-item>
            <el-menu-item index="/dashboard/strategy/monitor">策略监控</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="portfolio">
            <template #title>
              <el-icon><Collection /></el-icon>
              <span>组合产品管理</span>
            </template>
            <el-menu-item index="/dashboard/portfolio/audit">策略组合上架审核</el-menu-item>
            <el-menu-item index="/dashboard/portfolio/create">创建组合产品</el-menu-item>
            <el-menu-item index="/dashboard/portfolio/detail">组合产品详情</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="trade">
            <template #title>
              <el-icon><Money /></el-icon>
              <span>交易管理</span>
            </template>
            <el-menu-item index="/dashboard/trade/rebalance">组合调仓</el-menu-item>
            <el-menu-item index="/dashboard/trade/error">差错处理</el-menu-item>
            <el-menu-item index="/dashboard/trade/account">账户调仓</el-menu-item>
            <el-menu-item index="/dashboard/trade/order">交易单管理</el-menu-item>
            <el-menu-item index="/dashboard/trade/settlement">交割单管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- 主内容区域 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { 
  House, 
  TrendCharts, 
  DataAnalysis, 
  Operation, 
  Collection, 
  Money, 
  ArrowDown 
} from '@element-plus/icons-vue'

export default {
  name: 'Dashboard',
  components: {
    House,
    TrendCharts,
    DataAnalysis,
    Operation,
    Collection,
    Money,
    ArrowDown
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const username = ref('管理员')

    const activeMenu = computed(() => route.path)

    const handleMenuSelect = (index) => {
      router.push(index)
    }

    const handleCommand = (command) => {
      if (command === 'logout') {
        router.push('/login')
      }
    }

    return {
      username,
      activeMenu,
      handleMenuSelect,
      handleCommand
    }
  }
}
</script>

<style scoped>
.dashboard {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-left h2 {
  color: #333;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f5f5;
}

.username {
  margin: 0 8px;
  color: #333;
}

.main-container {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar {
  background: #304156;
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
}

.sidebar-menu {
  border: none;
  height: 100%;
}

.main-content {
  background: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}

:deep(.el-menu) {
  border-right: none;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
}

:deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
}

:deep(.el-menu-item:hover) {
  background-color: #263445 !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #1890ff !important;
  color: #fff !important;
}

:deep(.el-sub-menu__title:hover) {
  background-color: #263445 !important;
}
</style> 