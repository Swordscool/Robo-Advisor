<template>
  <div class="fund-company">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>基金公司管理</span>
          <el-button type="primary">添加基金公司</el-button>
        </div>
      </template>
      
      <el-table :data="companyList" style="width: 100%">
        <el-table-column prop="name" label="公司名称" width="200" />
        <el-table-column prop="code" label="公司代码" width="120" />
        <el-table-column prop="fundCount" label="基金数量" width="100" />
        <el-table-column prop="totalAssets" label="管理规模(亿)" width="150" />
        <el-table-column prop="establishDate" label="成立时间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '正常' ? 'success' : 'danger'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small">查看</el-button>
            <el-button size="small" type="primary">编辑</el-button>
            <el-button size="small" type="success">基金列表</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'FundCompany',
  setup() {
    const companyList = ref([
      { name: '华夏基金', code: 'HX', fundCount: 156, totalAssets: 8500, establishDate: '1998-04-09', status: '正常' },
      { name: '易方达基金', code: 'YFD', fundCount: 142, totalAssets: 7200, establishDate: '2001-04-17', status: '正常' },
      { name: '博时基金', code: 'BS', fundCount: 98, totalAssets: 5600, establishDate: '1998-07-13', status: '正常' },
      { name: '嘉实基金', code: 'JS', fundCount: 134, totalAssets: 6800, establishDate: '1999-03-25', status: '正常' },
      { name: '南方基金', code: 'NF', fundCount: 167, totalAssets: 9200, establishDate: '1998-03-06', status: '正常' }
    ])

    return {
      companyList
    }
  }
}
</script>

<style scoped>
.fund-company {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 