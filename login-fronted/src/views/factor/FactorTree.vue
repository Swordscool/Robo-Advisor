<template>
  <div class="factor-tree">
    <el-card class="tree-card">
      <template #header>
        <div class="card-header">
          <span>因子树管理</span>
          <div class="header-actions">
            <el-button type="primary" @click="handleAddTree">新建因子树</el-button>
            <el-button @click="handleExpandAll">展开全部</el-button>
            <el-button @click="handleCollapseAll">折叠全部</el-button>
          </div>
        </div>
      </template>

      <el-row :gutter="20">
        <!-- 左侧因子树 -->
        <el-col :span="8">
          <div class="tree-container">
            <h4>因子树结构</h4>
            <el-tree
              ref="treeRef"
              :data="treeData"
              :props="defaultProps"
              node-key="id"
              default-expand-all
              :expand-on-click-node="false"
              @node-click="handleNodeClick"
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <el-icon v-if="data.type === 'category'"><Folder /></el-icon>
                  <el-icon v-else><Document /></el-icon>
                  <span>{{ node.label }}</span>
                  <span class="node-count" v-if="data.count">({{ data.count }})</span>
                </span>
              </template>
            </el-tree>
          </div>
        </el-col>

        <!-- 右侧因子列表 -->
        <el-col :span="16">
          <div class="factor-list">
            <div class="list-header">
              <h4>{{ currentCategory }} - 因子列表</h4>
              <el-button type="primary" size="small" @click="handleAddFactor">添加因子</el-button>
            </div>
            
            <el-table :data="factorList" style="width: 100%" v-loading="loading">
              <el-table-column prop="name" label="因子名称" width="200" />
              <el-table-column prop="code" label="因子代码" width="150" />
              <el-table-column prop="type" label="因子类型" width="120" />
              <el-table-column prop="description" label="因子描述" />
              <el-table-column prop="updateTime" label="更新时间" width="180" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="scope.row.status === '启用' ? 'success' : 'danger'">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" fixed="right">
                <template #default="scope">
                  <el-button size="small" @click="handleViewFactor(scope.row)">查看</el-button>
                  <el-button size="small" type="primary" @click="handleEditFactor(scope.row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="handleDeleteFactor(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 新建因子树对话框 -->
    <el-dialog v-model="treeDialogVisible" title="新建因子树" width="500px">
      <el-form :model="treeForm" label-width="100px">
        <el-form-item label="树名称">
          <el-input v-model="treeForm.name" placeholder="请输入因子树名称" />
        </el-form-item>
        <el-form-item label="树描述">
          <el-input v-model="treeForm.description" type="textarea" placeholder="请输入因子树描述" />
        </el-form-item>
        <el-form-item label="业务场景">
          <el-select v-model="treeForm.scenario" placeholder="请选择业务场景">
            <el-option label="选基所需因子" value="选基" />
            <el-option label="量化投研所需因子" value="量化投研" />
            <el-option label="特色数据输出" value="特色数据" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="treeDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveTree">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 添加因子对话框 -->
    <el-dialog v-model="factorDialogVisible" title="添加因子" width="600px">
      <el-form :model="factorForm" label-width="100px">
        <el-form-item label="因子名称">
          <el-input v-model="factorForm.name" placeholder="请输入因子名称" />
        </el-form-item>
        <el-form-item label="因子代码">
          <el-input v-model="factorForm.code" placeholder="请输入因子代码" />
        </el-form-item>
        <el-form-item label="因子类型">
          <el-select v-model="factorForm.type" placeholder="请选择因子类型">
            <el-option label="技术因子" value="技术因子" />
            <el-option label="基本面因子" value="基本面因子" />
            <el-option label="宏观因子" value="宏观因子" />
            <el-option label="情绪因子" value="情绪因子" />
            <el-option label="风险因子" value="风险因子" />
          </el-select>
        </el-form-item>
        <el-form-item label="因子描述">
          <el-input v-model="factorForm.description" type="textarea" placeholder="请输入因子描述" />
        </el-form-item>
        <el-form-item label="计算公式">
          <el-input v-model="factorForm.formula" type="textarea" placeholder="请输入计算公式" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="factorDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveFactor">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Folder, Document } from '@element-plus/icons-vue'

export default {
  name: 'FactorTree',
  components: {
    Folder,
    Document
  },
  setup() {
    const loading = ref(false)
    const treeRef = ref(null)
    const treeDialogVisible = ref(false)
    const factorDialogVisible = ref(false)
    const currentCategory = ref('选基所需因子')

    const treeForm = reactive({
      name: '',
      description: '',
      scenario: ''
    })

    const factorForm = reactive({
      name: '',
      code: '',
      type: '',
      description: '',
      formula: ''
    })

    const defaultProps = {
      children: 'children',
      label: 'name'
    }

    const treeData = ref([
      {
        id: 1,
        name: '选基所需因子',
        type: 'category',
        count: 45,
        children: [
          {
            id: 11,
            name: '技术因子',
            type: 'category',
            count: 15,
            children: [
              { id: 111, name: '动量因子', type: 'factor' },
              { id: 112, name: '反转因子', type: 'factor' },
              { id: 113, name: '波动率因子', type: 'factor' }
            ]
          },
          {
            id: 12,
            name: '基本面因子',
            type: 'category',
            count: 20,
            children: [
              { id: 121, name: '估值因子', type: 'factor' },
              { id: 122, name: '盈利因子', type: 'factor' },
              { id: 123, name: '成长因子', type: 'factor' }
            ]
          },
          {
            id: 13,
            name: '风险因子',
            type: 'category',
            count: 10,
            children: [
              { id: 131, name: '贝塔因子', type: 'factor' },
              { id: 132, name: '夏普比率因子', type: 'factor' }
            ]
          }
        ]
      },
      {
        id: 2,
        name: '量化投研所需因子',
        type: 'category',
        count: 32,
        children: [
          {
            id: 21,
            name: '宏观因子',
            type: 'category',
            count: 12,
            children: [
              { id: 211, name: '利率因子', type: 'factor' },
              { id: 212, name: '通胀因子', type: 'factor' }
            ]
          },
          {
            id: 22,
            name: '情绪因子',
            type: 'category',
            count: 20,
            children: [
              { id: 221, name: '市场情绪因子', type: 'factor' },
              { id: 222, name: '投资者情绪因子', type: 'factor' }
            ]
          }
        ]
      }
    ])

    const factorList = ref([
      {
        name: '动量因子',
        code: 'MOMENTUM',
        type: '技术因子',
        description: '衡量基金价格变动趋势的因子',
        updateTime: '2024-01-15 10:30:00',
        status: '启用'
      },
      {
        name: '估值因子',
        code: 'VALUATION',
        type: '基本面因子',
        description: '基于基金净值与基本面指标的估值因子',
        updateTime: '2024-01-14 16:20:00',
        status: '启用'
      },
      {
        name: '贝塔因子',
        code: 'BETA',
        type: '风险因子',
        description: '衡量基金相对于市场的系统性风险',
        updateTime: '2024-01-13 09:15:00',
        status: '启用'
      }
    ])

    const handleNodeClick = (data) => {
      if (data.type === 'category') {
        currentCategory.value = data.name
        // 模拟加载该分类下的因子
        loading.value = true
        setTimeout(() => {
          loading.value = false
        }, 500)
      }
    }

    const handleAddTree = () => {
      treeDialogVisible.value = true
    }

    const handleSaveTree = () => {
      ElMessage.success('因子树创建成功')
      treeDialogVisible.value = false
    }

    const handleAddFactor = () => {
      factorDialogVisible.value = true
    }

    const handleSaveFactor = () => {
      ElMessage.success('因子添加成功')
      factorDialogVisible.value = false
    }

    const handleExpandAll = () => {
      treeRef.value?.expandAll()
    }

    const handleCollapseAll = () => {
      treeRef.value?.collapseAll()
    }

    const handleViewFactor = (row) => {
      ElMessage.info(`查看因子：${row.name}`)
    }

    const handleEditFactor = (row) => {
      ElMessage.info(`编辑因子：${row.name}`)
    }

    const handleDeleteFactor = (row) => {
      ElMessageBox.confirm(`确定要删除因子"${row.name}"吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ElMessage.success('删除成功')
      })
    }

    return {
      loading,
      treeRef,
      treeDialogVisible,
      factorDialogVisible,
      currentCategory,
      treeForm,
      factorForm,
      defaultProps,
      treeData,
      factorList,
      handleNodeClick,
      handleAddTree,
      handleSaveTree,
      handleAddFactor,
      handleSaveFactor,
      handleExpandAll,
      handleCollapseAll,
      handleViewFactor,
      handleEditFactor,
      handleDeleteFactor
    }
  }
}
</script>

<style scoped>
.factor-tree {
  padding: 20px;
}

.tree-card {
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

.tree-container {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 15px;
  height: 600px;
  overflow-y: auto;
}

.tree-container h4 {
  margin-bottom: 15px;
  color: #333;
}

.factor-list {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 15px;
  height: 600px;
  overflow-y: auto;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.list-header h4 {
  margin: 0;
  color: #333;
}

.custom-tree-node {
  display: flex;
  align-items: center;
  gap: 5px;
}

.node-count {
  color: #999;
  font-size: 12px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 