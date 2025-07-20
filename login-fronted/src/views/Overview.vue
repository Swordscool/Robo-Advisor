<template>
  <div class="overview">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon fund-icon">
              <el-icon><TrendCharts /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.fundCount }}</div>
              <div class="stat-label">管理基金数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon strategy-icon">
              <el-icon><Operation /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.strategyCount }}</div>
              <div class="stat-label">运行策略数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon portfolio-icon">
              <el-icon><Collection /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.portfolioCount }}</div>
              <div class="stat-label">组合产品数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon trade-icon">
              <el-icon><Money /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.tradeCount }}</div>
              <div class="stat-label">今日交易笔数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>策略收益走势</span>
            </div>
          </template>
          <div ref="strategyChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>资产配置分布</span>
            </div>
          </template>
          <div ref="assetChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最新动态 -->
    <el-row :gutter="20" class="activity-row">
      <el-col :span="12">
        <el-card class="activity-card">
          <template #header>
            <div class="card-header">
              <span>最新交易记录</span>
            </div>
          </template>
          <el-table :data="recentTrades" style="width: 100%">
            <el-table-column prop="time" label="时间" width="120" />
            <el-table-column prop="strategy" label="策略" width="120" />
            <el-table-column prop="action" label="操作" width="80" />
            <el-table-column prop="amount" label="金额" width="100" />
            <el-table-column prop="status" label="状态">
              <template #default="scope">
                <el-tag :type="scope.row.status === '成功' ? 'success' : 'danger'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="activity-card">
          <template #header>
            <div class="card-header">
              <span>系统通知</span>
            </div>
          </template>
          <div class="notification-list">
            <div v-for="notice in notifications" :key="notice.id" class="notification-item">
              <div class="notification-icon">
                <el-icon :color="notice.type === 'warning' ? '#E6A23C' : '#67C23A'">
                  <component :is="notice.type === 'warning' ? 'Warning' : 'SuccessFilled'" />
                </el-icon>
              </div>
              <div class="notification-content">
                <div class="notification-title">{{ notice.title }}</div>
                <div class="notification-time">{{ notice.time }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { TrendCharts, Operation, Collection, Money, Warning, SuccessFilled } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

export default {
  name: 'Overview',
  components: {
    TrendCharts,
    Operation,
    Collection,
    Money,
    Warning,
    SuccessFilled
  },
  setup() {
    const strategyChart = ref(null)
    const assetChart = ref(null)

    const stats = ref({
      fundCount: 1256,
      strategyCount: 89,
      portfolioCount: 45,
      tradeCount: 234
    })

    const recentTrades = ref([
      { time: '14:30:25', strategy: '稳健配置', action: '买入', amount: '50万', status: '成功' },
      { time: '14:25:18', strategy: '成长精选', action: '卖出', amount: '30万', status: '成功' },
      { time: '14:20:45', strategy: '价值投资', action: '调仓', amount: '20万', status: '处理中' },
      { time: '14:15:32', strategy: '量化对冲', action: '买入', amount: '100万', status: '成功' },
      { time: '14:10:15', strategy: 'FOF组合', action: '卖出', amount: '80万', status: '成功' }
    ])

    const notifications = ref([
      { id: 1, title: '策略"稳健配置"触发调仓信号', time: '2分钟前', type: 'success' },
      { id: 2, title: '基金"华夏成长"净值更新完成', time: '5分钟前', type: 'success' },
      { id: 3, title: '系统将在今晚22:00进行维护', time: '10分钟前', type: 'warning' },
      { id: 4, title: '新因子"动量因子"已接入系统', time: '15分钟前', type: 'success' },
      { id: 5, title: '组合产品"智能配置"审核通过', time: '20分钟前', type: 'success' }
    ])

    const initStrategyChart = () => {
      const chart = echarts.init(strategyChart.value)
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['稳健配置', '成长精选', '价值投资']
        },
        xAxis: {
          type: 'category',
          data: ['1月', '2月', '3月', '4月', '5月', '6月']
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}%'
          }
        },
        series: [
          {
            name: '稳健配置',
            type: 'line',
            data: [2.1, 2.3, 2.8, 3.2, 3.5, 3.8],
            smooth: true
          },
          {
            name: '成长精选',
            type: 'line',
            data: [1.8, 2.5, 3.2, 4.1, 4.8, 5.2],
            smooth: true
          },
          {
            name: '价值投资',
            type: 'line',
            data: [2.5, 2.8, 3.1, 3.4, 3.7, 4.0],
            smooth: true
          }
        ]
      }
      chart.setOption(option)
    }

    const initAssetChart = () => {
      const chart = echarts.init(assetChart.value)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '资产配置',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 35, name: '股票型基金' },
              { value: 25, name: '债券型基金' },
              { value: 20, name: '混合型基金' },
              { value: 15, name: '货币型基金' },
              { value: 5, name: '其他' }
            ]
          }
        ]
      }
      chart.setOption(option)
    }

    onMounted(() => {
      initStrategyChart()
      initAssetChart()
    })

    return {
      stats,
      recentTrades,
      notifications,
      strategyChart,
      assetChart
    }
  }
}
</script>

<style scoped>
.overview {
  padding: 20px;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  height: 120px;
}

.stat-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 24px;
  color: white;
}

.fund-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.strategy-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.portfolio-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.trade-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.charts-row {
  margin-bottom: 20px;
}

.chart-card {
  height: 400px;
}

.chart-container {
  height: 320px;
}

.activity-row {
  margin-bottom: 20px;
}

.activity-card {
  height: 400px;
}

.card-header {
  font-weight: bold;
  color: #333;
}

.notification-list {
  max-height: 320px;
  overflow-y: auto;
}

.notification-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-icon {
  margin-right: 12px;
  font-size: 16px;
}

.notification-content {
  flex: 1;
}

.notification-title {
  font-size: 14px;
  color: #333;
  margin-bottom: 4px;
}

.notification-time {
  font-size: 12px;
  color: #999;
}
</style> 