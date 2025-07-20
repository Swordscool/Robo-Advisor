<template>
  <div class="trade-order">
    <!-- 搜索和筛选区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="交易单号">
          <el-input v-model="searchForm.orderNo" placeholder="请输入交易单号" clearable />
        </el-form-item>
        <el-form-item label="组合名称">
          <el-input v-model="searchForm.portfolio" placeholder="请输入组合名称" clearable />
        </el-form-item>
        <el-form-item label="交易类型">
          <el-select v-model="searchForm.type" placeholder="请选择交易类型" clearable>
            <el-option label="建仓交易单" value="建仓" />
            <el-option label="调仓交易单" value="调仓" />
            <el-option label="差错处理交易单" value="差错处理" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="待审核" value="待审核" />
            <el-option label="已审核" value="已审核" />
            <el-option label="已执行" value="已执行" />
            <el-option label="已驳回" value="已驳回" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 交易单列表 -->
    <el-card class="list-card">
      <template #header>
        <div class="card-header">
          <span>交易单管理</span>
          <div class="header-actions">
            <el-button type="success" @click="handleBatchExecute">批量执行</el-button>
            <el-button type="danger" @click="handleBatchReject">批量驳回</el-button>
            <el-button @click="handleExport">导出数据</el-button>
          </div>
        </div>
      </template>

      <el-table :data="orderList" style="width: 100%" v-loading="loading" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="orderNo" label="交易单号" width="180" />
        <el-table-column prop="portfolio" label="组合名称" width="150" />
        <el-table-column prop="type" label="交易类型" width="120">
          <template #default="scope">
            <el-tag :type="getTypeTagType(scope.row.type)">{{ scope.row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fundCount" label="基金数量" width="100" />
        <el-table-column prop="totalAmount" label="交易金额" width="120">
          <template #default="scope">
            {{ scope.row.totalAmount }}万
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reviewer" label="审核人" width="120" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleViewDetail(scope.row)">详情</el-button>
            <el-button 
              size="small" 
              type="success" 
              @click="handleExecute(scope.row)"
              :disabled="scope.row.status !== '已审核'"
            >
              执行
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleReject(scope.row)"
              :disabled="scope.row.status !== '已审核'"
            >
              驳回
            </el-button>
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

    <!-- 交易单详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="交易单详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="交易单号">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="组合名称">{{ currentOrder.portfolio }}</el-descriptions-item>
        <el-descriptions-item label="交易类型">{{ currentOrder.type }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ currentOrder.status }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ currentOrder.createTime }}</el-descriptions-item>
        <el-descriptions-item label="审核人">{{ currentOrder.reviewer }}</el-descriptions-item>
      </el-descriptions>

      <div class="detail-section">
        <h4>交易明细</h4>
        <el-table :data="currentOrder.details" style="width: 100%">
          <el-table-column prop="fundCode" label="基金代码" width="120" />
          <el-table-column prop="fundName" label="基金名称" width="200" />
          <el-table-column prop="action" label="操作" width="80">
            <template #default="scope">
              <el-tag :type="scope.row.action === '买入' ? 'success' : 'danger'">
                {{ scope.row.action }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="金额(万)" width="120" />
          <el-table-column prop="shares" label="份额" width="120" />
          <el-table-column prop="price" label="价格" width="100" />
        </el-table>
      </div>

      <div class="detail-section" v-if="currentOrder.remark">
        <h4>备注</h4>
        <p>{{ currentOrder.remark }}</p>
      </div>
    </el-dialog>

    <!-- 驳回原因对话框 -->
    <el-dialog v-model="rejectDialogVisible" title="驳回原因" width="500px">
      <el-form :model="rejectForm" label-width="100px">
        <el-form-item label="驳回原因">
          <el-input v-model="rejectForm.reason" type="textarea" placeholder="请输入驳回原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="rejectDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleConfirmReject">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'TradeOrder',
  setup() {
    const loading = ref(false)
    const currentPage = ref(1)
    const pageSize = ref(20)
    const total = ref(156)
    const detailDialogVisible = ref(false)
    const rejectDialogVisible = ref(false)
    const selectedOrders = ref([])

    const searchForm = reactive({
      orderNo: '',
      portfolio: '',
      type: '',
      status: ''
    })

    const rejectForm = reactive({
      reason: ''
    })

    const currentOrder = ref({})

    const orderList = ref([
      {
        orderNo: 'TO202401150001',
        portfolio: '稳健配置组合',
        type: '建仓',
        fundCount: 5,
        totalAmount: 100,
        createTime: '2024-01-15 10:30:00',
        status: '已审核',
        reviewer: '张经理',
        details: [
          { fundCode: '000001', fundName: '华夏成长混合', action: '买入', amount: 20, shares: 16200, price: 1.2345 },
          { fundCode: '000002', fundName: '易方达消费行业', action: '买入', amount: 20, shares: 7800, price: 2.5678 },
          { fundCode: '000003', fundName: '博时信用债券A', action: '买入', amount: 30, shares: 26700, price: 1.1234 },
          { fundCode: '000004', fundName: '嘉实货币A', action: '买入', amount: 20, shares: 200000, price: 1.0000 },
          { fundCode: '000005', fundName: '南方中证500ETF', action: '买入', amount: 10, shares: 2900, price: 3.4567 }
        ],
        remark: '新建组合建仓交易'
      },
      {
        orderNo: 'TO202401150002',
        portfolio: '成长精选组合',
        type: '调仓',
        fundCount: 3,
        totalAmount: 50,
        createTime: '2024-01-15 14:20:00',
        status: '待审核',
        reviewer: '',
        details: [
          { fundCode: '000001', fundName: '华夏成长混合', action: '卖出', amount: 15, shares: 12150, price: 1.2345 },
          { fundCode: '000002', fundName: '易方达消费行业', action: '买入', amount: 20, shares: 7800, price: 2.5678 },
          { fundCode: '000005', fundName: '南方中证500ETF', action: '买入', amount: 15, shares: 4350, price: 3.4567 }
        ],
        remark: '根据市场情况调整配置比例'
      },
      {
        orderNo: 'TO202401150003',
        portfolio: '价值投资组合',
        type: '差错处理',
        fundCount: 2,
        totalAmount: 30,
        createTime: '2024-01-15 16:45:00',
        status: '已执行',
        reviewer: '李经理',
        details: [
          { fundCode: '000003', fundName: '博时信用债券A', action: '买入', amount: 20, shares: 17800, price: 1.1234 },
          { fundCode: '000006', fundName: '工银瑞信债券A', action: '买入', amount: 10, shares: 8900, price: 1.1234 }
        ],
        remark: '处理上次交易失败的情况'
      }
    ])

    const getTypeTagType = (type) => {
      const typeMap = {
        '建仓': 'primary',
        '调仓': 'warning',
        '差错处理': 'danger'
      }
      return typeMap[type] || 'info'
    }

    const getStatusTagType = (status) => {
      const statusMap = {
        '待审核': 'info',
        '已审核': 'warning',
        '已执行': 'success',
        '已驳回': 'danger'
      }
      return statusMap[status] || 'info'
    }

    const handleSearch = () => {
      loading.value = true
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

    const handleSelectionChange = (selection) => {
      selectedOrders.value = selection
    }

    const handleBatchExecute = () => {
      if (selectedOrders.value.length === 0) {
        ElMessage.warning('请选择要执行的交易单')
        return
      }
      ElMessageBox.confirm(`确定要执行选中的 ${selectedOrders.value.length} 个交易单吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ElMessage.success('批量执行成功')
      })
    }

    const handleBatchReject = () => {
      if (selectedOrders.value.length === 0) {
        ElMessage.warning('请选择要驳回的交易单')
        return
      }
      rejectDialogVisible.value = true
    }

    const handleExport = () => {
      ElMessage.success('数据导出成功')
    }

    const handleViewDetail = (row) => {
      currentOrder.value = row
      detailDialogVisible.value = true
    }

    const handleExecute = (row) => {
      ElMessageBox.confirm(`确定要执行交易单"${row.orderNo}"吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ElMessage.success('交易单执行成功')
      })
    }

    const handleReject = (row) => {
      currentOrder.value = row
      rejectDialogVisible.value = true
    }

    const handleConfirmReject = () => {
      if (!rejectForm.reason) {
        ElMessage.warning('请输入驳回原因')
        return
      }
      ElMessage.success('交易单驳回成功')
      rejectDialogVisible.value = false
      rejectForm.reason = ''
    }

    const handleSizeChange = (val) => {
      pageSize.value = val
    }

    const handleCurrentChange = (val) => {
      currentPage.value = val
    }

    return {
      loading,
      currentPage,
      pageSize,
      total,
      searchForm,
      rejectForm,
      orderList,
      currentOrder,
      selectedOrders,
      detailDialogVisible,
      rejectDialogVisible,
      getTypeTagType,
      getStatusTagType,
      handleSearch,
      handleReset,
      handleSelectionChange,
      handleBatchExecute,
      handleBatchReject,
      handleExport,
      handleViewDetail,
      handleExecute,
      handleReject,
      handleConfirmReject,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.trade-order {
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

.detail-section {
  margin-top: 20px;
}

.detail-section h4 {
  margin-bottom: 15px;
  color: #333;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 