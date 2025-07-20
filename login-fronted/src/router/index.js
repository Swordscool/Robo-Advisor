import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/dashboard',
      component: () => import('../views/Dashboard.vue'),
      children: [
        {
          path: '',
          component: () => import('../views/Overview.vue')
        },
        // 基金研究模块
        {
          path: 'fund/list',
          component: () => import('../views/fund/FundList.vue')
        },
        {
          path: 'fund/company',
          component: () => import('../views/fund/FundCompany.vue')
        },
        {
          path: 'fund/manager',
          component: () => import('../views/fund/FundManager.vue')
        },
        {
          path: 'fund/profile',
          component: () => import('../views/fund/FundProfile.vue')
        },
        // 因子管理模块
        {
          path: 'factor/access',
          component: () => import('../views/factor/FactorAccess.vue')
        },
        {
          path: 'factor/tree',
          component: () => import('../views/factor/FactorTree.vue')
        },
        {
          path: 'factor/derivative',
          component: () => import('../views/factor/FactorDerivative.vue')
        },
        {
          path: 'factor/style',
          component: () => import('../views/factor/FactorStyle.vue')
        },
        // 策略管理模块
        {
          path: 'strategy/asset',
          component: () => import('../views/strategy/StrategyAsset.vue')
        },
        {
          path: 'strategy/fof',
          component: () => import('../views/strategy/StrategyFOF.vue')
        },
        {
          path: 'strategy/index',
          component: () => import('../views/strategy/StrategyIndex.vue')
        },
        {
          path: 'strategy/timing',
          component: () => import('../views/strategy/StrategyTiming.vue')
        },
        {
          path: 'strategy/detail',
          component: () => import('../views/strategy/StrategyDetail.vue')
        },
        {
          path: 'strategy/backtest',
          component: () => import('../views/strategy/StrategyBacktest.vue')
        },
        {
          path: 'strategy/balance',
          component: () => import('../views/strategy/StrategyBalance.vue')
        },
        {
          path: 'strategy/monitor',
          component: () => import('../views/strategy/StrategyMonitor.vue')
        },
        // 组合产品管理模块
        {
          path: 'portfolio/audit',
          component: () => import('../views/portfolio/PortfolioAudit.vue')
        },
        {
          path: 'portfolio/create',
          component: () => import('../views/portfolio/PortfolioCreate.vue')
        },
        {
          path: 'portfolio/detail',
          component: () => import('../views/portfolio/PortfolioDetail.vue')
        },
        // 交易管理模块
        {
          path: 'trade/rebalance',
          component: () => import('../views/trade/TradeRebalance.vue')
        },
        {
          path: 'trade/error',
          component: () => import('../views/trade/TradeError.vue')
        },
        {
          path: 'trade/account',
          component: () => import('../views/trade/TradeAccount.vue')
        },
        {
          path: 'trade/order',
          component: () => import('../views/trade/TradeOrder.vue')
        },
        {
          path: 'trade/settlement',
          component: () => import('../views/trade/TradeSettlement.vue')
        }
      ]
    }
  ]
})

export default router