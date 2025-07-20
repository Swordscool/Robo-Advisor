<template>
  <div class="fund-list">
    <!-- 搜索和筛选区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="基金代码">
          <el-input v-model="searchForm.code" placeholder="请输入基金代码" clearable />
        </el-form-item>
        <el-form-item label="基金名称">
          <el-input v-model="searchForm.name" placeholder="请输入基金名称" clearable />
        </el-form-item>
        <el-form-item label="基金类型">
          <el-select v-model="searchForm.type" placeholder="请选择基金类型" clearable>
            <el-option label="股票型" value="股票型" />
            <el-option label="债券型" value="债券型" />
            <el-option label="混合型" value="混合型" />
            <el-option label="货币型" value="货币型" />
            <el-option label="指数型" value="指数型" />
          </el-select>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-select v-model="searchForm.risk" placeholder="请选择风险等级" clearable>
            <el-option label="低风险" value="低风险" />
            <el-option label="中低风险" value="中低风险" />
            <el-option label="中风险" value="中风险" />
            <el-option label="中高风险" value="中高风险" />
            <el-option label="高风险" value="高风险" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 基金列表 -->
    <el-card class="list-card">
      <template #header>
        <div class="card-header">
          <span>基金列表</span>
          <div class="header-actions">
            <el-button type="primary" @click="handleCreatePortfolio">创建基金组合</el-button>
            <el-button @click="handleExport">导出数据</el-button>
          </div>
        </div>
      </template>

      <el-table :data="fundList" style="width: 100%" v-loading="loading">
        <el-table-column prop="code" label="基金代码" width="120" />
        <el-table-column prop="name" label="基金名称" width="200" />
        <el-table-column prop="type" label="基金类型" width="100" />
        <el-table-column prop="company" label="基金公司" width="150" />
        <el-table-column prop="manager" label="基金经理" width="120" />
        <el-table-column prop="risk" label="风险等级" width="100">
          <template #default="scope">
            <el-tag :type="getRiskTagType(scope.row.risk)">{{ scope.row.risk }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nav" label="最新净值" width="100" />
        <el-table-column prop="navDate" label="净值日期" width="120" />
        <el-table-column prop="yield1m" label="近1月收益率" width="120">
          <template #default="scope">
            <span :class="scope.row.yield1m >= 0 ? 'positive' : 'negative'">
              {{ scope.row.yield1m }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="yield3m" label="近3月收益率" width="120">
          <template #default="scope">
            <span :class="scope.row.yield3m >= 0 ? 'positive' : 'negative'">
              {{ scope.row.yield3m }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="yield1y" label="近1年收益率" width="120">
          <template #default="scope">
            <span :class="scope.row.yield1y >= 0 ? 'positive' : 'negative'">
              {{ scope.row.yield1y }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleViewDetail(scope.row)">详情</el-button>
            <el-button size="small" type="primary" @click="handleAddToPortfolio(scope.row)">加入组合</el-button>
            <el-button size="small" type="success" @click="handleTrack(scope.row)">跟踪</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 创建基金组合对话框 -->
    <el-dialog v-model="portfolioDialogVisible" title="创建基金组合" width="600px">
      <el-form :model="portfolioForm" label-width="100px">
        <el-form-item label="组合名称">
          <el-input v-model="portfolioForm.name" placeholder="请输入组合名称" />
        </el-form-item>
        <el-form-item label="组合描述">
          <el-input v-model="portfolioForm.description" type="textarea" placeholder="请输入组合描述" />
        </el-form-item>
        <el-form-item label="风险等级">
          <el-select v-model="portfolioForm.risk" placeholder="请选择风险等级">
            <el-option label="低风险" value="低风险" />
            <el-option label="中低风险" value="中低风险" />
            <el-option label="中风险" value="中风险" />
            <el-option label="中高风险" value="中高风险" />
            <el-option label="高风险" value="高风险" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="portfolioDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSavePortfolio">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

export default {
  name: 'FundList',
  setup() {
    const loading = ref(false)
    const currentPage = ref(1)
    const pageSize = ref(20)
    const total = ref(1256)
    const portfolioDialogVisible = ref(false)

    const searchForm = reactive({
      code: '',
      name: '',
      type: '',
      risk: ''
    })

    const portfolioForm = reactive({
      name: '',
      description: '',
      risk: ''
    })

    const fundList = ref([
      {
        code: '000001',
        name: '华夏成长混合',
        type: '混合型',
        company: '华夏基金',
        manager: '张经理',
        risk: '中风险',
        nav: '1.2345',
        navDate: '2024-01-15',
        yield1m: 2.35,
        yield3m: 5.67,
        yield1y: 12.45
      },
      {
        code: '000002',
        name: '易方达消费行业股票',
        type: '股票型',
        company: '易方达基金',
        manager: '李经理',
        risk: '中高风险',
        nav: '2.5678',
        navDate: '2024-01-15',
        yield1m: -1.23,
        yield3m: 3.45,
        yield1y: 8.90
      },
      {
        code: '000003',
        name: '博时信用债券A',
        type: '债券型',
        company: '博时基金',
        manager: '王经理',
        risk: '中低风险',
        nav: '1.1234',
        navDate: '2024-01-15',
        yield1m: 0.45,
        yield3m: 1.23,
        yield1y: 4.56
      },
      {
        code: '000004',
        name: '嘉实货币A',
        type: '货币型',
        company: '嘉实基金',
        manager: '赵经理',
        risk: '低风险',
        nav: '1.0000',
        navDate: '2024-01-15',
        yield1m: 0.12,
        yield3m: 0.35,
        yield1y: 1.45
      },
      {
        code: '000005',
        name: '南方中证500ETF',
        type: '指数型',
        company: '南方基金',
        manager: '陈经理',
        risk: '中高风险',
        nav: '3.4567',
        navDate: '2024-01-15',
        yield1m: 3.67,
        yield3m: 7.89,
        yield1y: 15.67
      }
    ])

    const getRiskTagType = (risk) => {
      const riskMap = {
        '低风险': 'success',
        '中低风险': 'info',
        '中风险': 'warning',
        '中高风险': 'danger',
        '高风险': 'danger'
      }
      return riskMap[risk] || 'info'
    }

    const handleSearch = () => {
      loading.value = true
      // 模拟搜索
      setTimeout(() => {
        loading.value = false
        ElMessage.success('搜索完成')
      }, 1000)
    }

    const handleReset = () => {
      Object.keys(searchForm).forEach(key => {
        searchForm[key] = ''
      })
    }

    const handleCreatePortfolio = () => {
      portfolioDialogVisible.value = true
    }

    const handleSavePortfolio = () => {
      ElMessage.success('基金组合创建成功')
      portfolioDialogVisible.value = false
    }

    const handleExport = () => {
      ElMessage.success('数据导出成功')
    }

    const handleViewDetail = (row) => {
      ElMessage.info(`查看基金详情：${row.name}`)
    }

    const handleAddToPortfolio = (row) => {
      ElMessage.success(`已将 ${row.name} 加入组合`)
    }

    const handleTrack = (row) => {
      ElMessage.success(`已开始跟踪 ${row.name}`)
    }

    const handleSizeChange = (val) => {
      pageSize.value = val
      // 重新加载数据
    }

    const handleCurrentChange = (val) => {
      currentPage.value = val
      // 重新加载数据
    }

    return {
      loading,
      currentPage,
      pageSize,
      total,
      searchForm,
      portfolioForm,
      fundList,
      portfolioDialogVisible,
      getRiskTagType,
      handleSearch,
      handleReset,
      handleCreatePortfolio,
      handleSavePortfolio,
      handleExport,
      handleViewDetail,
      handleAddToPortfolio,
      handleTrack,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.fund-list {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.list-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.positive {
  color: #67c23a;
}

.negative {
  color: #f56c6c;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 