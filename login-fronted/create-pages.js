import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// 需要创建的页面列表
const pages = [
  { path: 'fund/FundManager.vue', title: '基金经理管理' },
  { path: 'fund/FundProfile.vue', title: '基金画像' },
  { path: 'factor/FactorAccess.vue', title: '因子接入' },
  { path: 'factor/FactorDerivative.vue', title: '创建衍生因子' },
  { path: 'factor/FactorStyle.vue', title: '风格投资因子管理' },
  { path: 'strategy/StrategyFOF.vue', title: 'FOF组合管理' },
  { path: 'strategy/StrategyIndex.vue', title: '基金指数组合' },
  { path: 'strategy/StrategyTiming.vue', title: '择时组合' },
  { path: 'strategy/StrategyDetail.vue', title: '策略详情' },
  { path: 'strategy/StrategyBacktest.vue', title: '策略回测' },
  { path: 'strategy/StrategyBalance.vue', title: '策略再平衡' },
  { path: 'strategy/StrategyMonitor.vue', title: '策略监控' },
  { path: 'portfolio/PortfolioAudit.vue', title: '策略组合上架审核' },
  { path: 'portfolio/PortfolioCreate.vue', title: '创建组合产品' },
  { path: 'portfolio/PortfolioDetail.vue', title: '组合产品详情' },
  { path: 'trade/TradeRebalance.vue', title: '组合调仓' },
  { path: 'trade/TradeError.vue', title: '差错处理' },
  { path: 'trade/TradeAccount.vue', title: '账户调仓' },
  { path: 'trade/TradeSettlement.vue', title: '交割单管理' }
];

// 页面模板
const pageTemplate = (title) => `<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>${title}</span>
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
  name: '${title.replace(/\s+/g, '')}',
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
</style>`;

// 创建目录和文件
pages.forEach(page => {
  const dir = path.join(__dirname, 'src', 'views', path.dirname(page.path));
  const filePath = path.join(__dirname, 'src', 'views', page.path);
  
  // 创建目录
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
  }
  
  // 创建文件
  fs.writeFileSync(filePath, pageTemplate(page.title));
  console.log(`Created: ${filePath}`);
});

console.log('All pages created successfully!'); 