import { createRouter, createWebHistory } from 'vue-router'

import HomeVue from '@/views/home.vue'

import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import GoodsCategoryVue from '@/views/goods/GoodsCategory.vue'
import GoodsManageVue from '@/views/goods/GoodsManage.vue'
import SupplierManageVue from '@/views/supplier/SupplierManage.vue'
import WarehouseManageVue from '@/views/warehouse/WarehouseManage.vue'
import InboundSummaryManageVue from '@/views/inboundSummary/InboundSummaryManage.vue'

import NoFoundVue from "@/views/404.vue"

// 定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: LayoutVue, redirect: '/home', children: [
            { path: '/home', component: HomeVue },
            { path: '/goods/category', component: GoodsCategoryVue },
            { path: '/goods/manage', component: GoodsManageVue },
            { path: '/supplier/manage', component: SupplierManageVue },
            { path: '/warehouse/manage', component: WarehouseManageVue },
            { path: '/inboundSummary/manage', component: InboundSummaryManageVue },
        ]
    },
    { path: '/:catchAll(.*)', component: NoFoundVue },
]

// 创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

// 导出路由
export default router;