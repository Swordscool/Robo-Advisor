<template>
  <div class="strategy-asset">
    <!-- 策略列表 -->
    <el-card class="strategy-card">
      <template #header>
        <div class="card-header">
          <span>大类资产配置策略</span>
          <el-button type="primary" @click="handleCreateStrategy">创建策略</el-button>
        </div>
      </template>

      <el-table :data="strategyList" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="策略名称" width="200" />
        <el-table-column prop="type" label="策略类型" width="150" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="yield" label="累计收益率" width="120">
          <template #default="scope">
            <span :class="scope.row.yield >= 0 ? 'positive' : 'negative'">
              {{ scope.row.yield }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="risk" label="风险等级" width="100">
          <template #default="scope">
            <el-tag :type="getRiskType(scope.row.risk)">{{ scope.row.risk }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="assets" label="资产配置" width="300">
          <template #default="scope">
            <div class="asset-allocation">
              <span v-for="asset in scope.row.assets" :key="asset.name" class="asset-item">
                {{ asset.name }}: {{ asset.ratio }}%
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleViewDetail(scope.row)">详情</el-button>
            <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="success" @click="handleBacktest(scope.row)">回测</el-button>
            <el-button size="small" type="warning" @click="handleMonitor(scope.row)">监控</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 创建策略对话框 -->
    <el-dialog v-model="createDialogVisible" title="创建大类资产配置策略" width="800px">
      <el-steps :active="currentStep" finish-status="success" class="steps">
        <el-step title="基本信息" />
        <el-step title="资产配置" />
        <el-step title="风险参数" />
        <el-step title="确认创建" />
      </el-steps>

      <!-- 步骤1：基本信息 -->
      <div v-if="currentStep === 0" class="step-content">
        <el-form :model="strategyForm" label-width="120px">
          <el-form-item label="策略名称">
            <el-input v-model="strategyForm.name" placeholder="请输入策略名称" />
          </el-form-item>
          <el-form-item label="策略描述">
            <el-input v-model="strategyForm.description" type="textarea" placeholder="请输入策略描述" />
          </el-form-item>
          <el-form-item label="策略类型">
            <el-select v-model="strategyForm.type" placeholder="请选择策略类型">
              <el-option label="保守型" value="保守型" />
              <el-option label="稳健型" value="稳健型" />
              <el-option label="平衡型" value="平衡型" />
              <el-option label="成长型" value="成长型" />
              <el-option label="激进型" value="激进型" />
            </el-select>
          </el-form-item>
          <el-form-item label="基准指数">
            <el-select v-model="strategyForm.benchmark" placeholder="请选择基准指数">
              <el-option label="沪深300" value="沪深300" />
              <el-option label="中证500" value="中证500" />
              <el-option label="上证50" value="上证50" />
              <el-option label="创业板指" value="创业板指" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤2：资产配置 -->
      <div v-if="currentStep === 1" class="step-content">
        <div class="asset-config">
          <h4>资产配置比例</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="股票型基金">
                <el-input-number v-model="strategyForm.stockRatio" :min="0" :max="100" :precision="1" />
                <span class="unit">%</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="债券型基金">
                <el-input-number v-model="strategyForm.bondRatio" :min="0" :max="100" :precision="1" />
                <span class="unit">%</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="混合型基金">
                <el-input-number v-model="strategyForm.mixedRatio" :min="0" :max="100" :precision="1" />
                <span class="unit">%</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="货币型基金">
                <el-input-number v-model="strategyForm.moneyRatio" :min="0" :max="100" :precision="1" />
                <span class="unit">%</span>
              </el-form-item>
            </el-col>
          </el-row>
          <div class="ratio-sum">
            配置比例总和: <span :class="ratioSum === 100 ? 'correct' : 'error'">{{ ratioSum }}%</span>
            {{ ratioSum === 100 ? '✓' : '✗' }}
          </div>
        </div>
      </div>

      <!-- 步骤3：风险参数 -->
      <div v-if="currentStep === 2" class="step-content">
        <el-form :model="strategyForm" label-width="120px">
          <el-form-item label="最大回撤限制">
            <el-input-number v-model="strategyForm.maxDrawdown" :min="0" :max="50" :precision="1" />
            <span class="unit">%</span>
          </el-form-item>
          <el-form-item label="年化波动率">
            <el-input-number v-model="strategyForm.volatility" :min="0" :max="50" :precision="1" />
            <span class="unit">%</span>
          </el-form-item>
          <el-form-item label="夏普比率目标">
            <el-input-number v-model="strategyForm.sharpeRatio" :min="0" :max="5" :precision="2" />
          </el-form-item>
          <el-form-item label="再平衡频率">
            <el-select v-model="strategyForm.rebalanceFreq" placeholder="请选择再平衡频率">
              <el-option label="每日" value="daily" />
              <el-option label="每周" value="weekly" />
              <el-option label="每月" value="monthly" />
              <el-option label="每季度" value="quarterly" />
              <el-option label="每年" value="yearly" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤4：确认创建 -->
      <div v-if="currentStep === 3" class="step-content">
        <div class="confirm-info">
          <h4>策略信息确认</h4>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="策略名称">{{ strategyForm.name }}</el-descriptions-item>
            <el-descriptions-item label="策略类型">{{ strategyForm.type }}</el-descriptions-item>
            <el-descriptions-item label="基准指数">{{ strategyForm.benchmark }}</el-descriptions-item>
            <el-descriptions-item label="最大回撤">{{ strategyForm.maxDrawdown }}%</el-descriptions-item>
            <el-descriptions-item label="股票配置">{{ strategyForm.stockRatio }}%</el-descriptions-item>
            <el-descriptions-item label="债券配置">{{ strategyForm.bondRatio }}%</el-descriptions-item>
            <el-descriptions-item label="混合配置">{{ strategyForm.mixedRatio }}%</el-descriptions-item>
            <el-descriptions-item label="货币配置">{{ strategyForm.moneyRatio }}%</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handlePrevStep" :disabled="currentStep === 0">上一步</el-button>
          <el-button v-if="currentStep < 3" type="primary" @click="handleNextStep">下一步</el-button>
          <el-button v-else type="primary" @click="handleConfirmCreate">确认创建</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'

export default {
  name: 'StrategyAsset',
  setup() {
    const loading = ref(false)
    const createDialogVisible = ref(false)
    const currentStep = ref(0)

    const strategyForm = reactive({
      name: '',
      description: '',
      type: '',
      benchmark: '',
      stockRatio: 40,
      bondRatio: 30,
      mixedRatio: 20,
      moneyRatio: 10,
      maxDrawdown: 15,
      volatility: 12,
      sharpeRatio: 1.2,
      rebalanceFreq: 'monthly'
    })

    const strategyList = ref([
      {
        name: '稳健配置策略',
        type: '稳健型',
        status: '运行中',
        createTime: '2024-01-01 10:00:00',
        yield: 8.5,
        risk: '中风险',
        assets: [
          { name: '股票', ratio: 40 },
          { name: '债券', ratio: 30 },
          { name: '混合', ratio: 20 },
          { name: '货币', ratio: 10 }
        ]
      },
      {
        name: '成长配置策略',
        type: '成长型',
        status: '运行中',
        createTime: '2024-01-05 14:30:00',
        yield: 12.3,
        risk: '中高风险',
        assets: [
          { name: '股票', ratio: 60 },
          { name: '债券', ratio: 20 },
          { name: '混合', ratio: 15 },
          { name: '货币', ratio: 5 }
        ]
      },
      {
        name: '保守配置策略',
        type: '保守型',
        status: '暂停',
        createTime: '2024-01-10 09:15:00',
        yield: 4.2,
        risk: '低风险',
        assets: [
          { name: '股票', ratio: 20 },
          { name: '债券', ratio: 50 },
          { name: '混合', ratio: 20 },
          { name: '货币', ratio: 10 }
        ]
      }
    ])

    const ratioSum = computed(() => {
      return strategyForm.stockRatio + strategyForm.bondRatio + strategyForm.mixedRatio + strategyForm.moneyRatio
    })

    const getStatusType = (status) => {
      const statusMap = {
        '运行中': 'success',
        '暂停': 'warning',
        '停止': 'danger'
      }
      return statusMap[status] || 'info'
    }

    const getRiskType = (risk) => {
      const riskMap = {
        '低风险': 'success',
        '中风险': 'warning',
        '中高风险': 'danger',
        '高风险': 'danger'
      }
      return riskMap[risk] || 'info'
    }

    const handleCreateStrategy = () => {
      createDialogVisible.value = true
      currentStep.value = 0
    }

    const handleNextStep = () => {
      if (currentStep.value < 3) {
        currentStep.value++
      }
    }

    const handlePrevStep = () => {
      if (currentStep.value > 0) {
        currentStep.value--
      }
    }

    const handleConfirmCreate = () => {
      ElMessage.success('策略创建成功')
      createDialogVisible.value = false
    }

    const handleViewDetail = (row) => {
      ElMessage.info(`查看策略详情：${row.name}`)
    }

    const handleEdit = (row) => {
      ElMessage.info(`编辑策略：${row.name}`)
    }

    const handleBacktest = (row) => {
      ElMessage.info(`开始回测：${row.name}`)
    }

    const handleMonitor = (row) => {
      ElMessage.info(`查看监控：${row.name}`)
    }

    return {
      loading,
      createDialogVisible,
      currentStep,
      strategyForm,
      strategyList,
      ratioSum,
      getStatusType,
      getRiskType,
      handleCreateStrategy,
      handleNextStep,
      handlePrevStep,
      handleConfirmCreate,
      handleViewDetail,
      handleEdit,
      handleBacktest,
      handleMonitor
    }
  }
}
</script>

<style scoped>
.strategy-asset {
  padding: 20px;
}

.strategy-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.positive {
  color: #67c23a;
}

.negative {
  color: #f56c6c;
}

.asset-allocation {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.asset-item {
  background: #f0f2f5;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.steps {
  margin-bottom: 30px;
}

.step-content {
  min-height: 300px;
  padding: 20px 0;
}

.asset-config h4 {
  margin-bottom: 20px;
  color: #333;
}

.unit {
  margin-left: 8px;
  color: #666;
}

.ratio-sum {
  margin-top: 20px;
  padding: 10px;
  background: #f8f9fa;
  border-radius: 4px;
  text-align: center;
  font-weight: bold;
}

.ratio-sum .correct {
  color: #67c23a;
}

.ratio-sum .error {
  color: #f56c6c;
}

.confirm-info h4 {
  margin-bottom: 20px;
  color: #333;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 