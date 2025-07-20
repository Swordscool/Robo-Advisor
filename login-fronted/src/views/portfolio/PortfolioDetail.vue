<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>组合产品详情</span>
          <el-button type="primary">新建</el-button>
        </div>
      </template>
      
      <div class="content-area">
        <el-empty description="功能开发中，敬请期待..." />
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: '组合产品详情',
  setup() {
    return {}
  }
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-area {
  min-height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>