<template>
  <el-container class="main-container">
    <!-- 侧边导航 -->
    <el-aside width="240px" class="sider">
      <div class="logo-area">
        <img src="@/assets/logo.png" class="logo" />
        <h2>智能投顾系统</h2>
      </div>
      
      <el-menu
        router
        :default-active="$route.path"
        class="nav-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/fund">
          <el-icon><PieChart /></el-icon>
          <span>基金研究</span>
        </el-menu-item>
        <el-menu-item index="/factor">
          <el-icon><DataAnalysis /></el-icon>
          <span>因子管理</span>
        </el-menu-item>
        <!-- 其他三个子系统菜单项 -->
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <el-header class="header">
        <div class="user-info">
          <el-avatar :size="36" />
          <span class="username">{{ userInfo.name }}</span>
        </div>
      </el-header>
      
      <el-main>
        <!-- 仪表盘展示 -->
        <div class="dashboard">
          <el-row :gutter="20">
            <el-col :span="6">
              <metric-card title="总管理资产" value="8,420万" trend="+2.4%"/>
            </el-col>
            <!-- 其他数据指标 -->
          </el-row>
          
          <!-- 组合收益图表 -->
          <div ref="portfolioChart" style="height: 360px; margin-top: 20px;"></div>
        </div>

        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

// 初始化图表
const portfolioChart = ref(null)
onMounted(() => {
  const chart = echarts.init(portfolioChart.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] },
    yAxis: { type: 'value' },
    series: [{
      data: [820, 932, 901, 934, 1290, 1330, 1320],
      type: 'line',
      smooth: true
    }]
  })
})
</script>

<style scoped>
.main-container { height: 100vh; }
.sider { background: #001529; }
.logo-area {
  display: flex;
  align-items: center;
  padding: 16px;
  color: white;
}
.logo { width: 40px; margin-right: 12px; }
.nav-menu {
  border-right: none;
  background: inherit;
  --el-menu-active-color: #1890ff;
}
</style>