<script setup>

import {
    Edit,
    Delete,
    DocumentDelete
} from '@element-plus/icons-vue'

import { ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

const headerRef = ref();
const formRef = ref();
const paginationRef = ref();
const getTableHeight = () => {
    var pageHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    let headerHeight = headerRef.value ? headerRef.value.offsetHeight : 0;
    let formHeight = formRef.value ? formRef.value.offsetHeight : 0;
    let paginationHeight = paginationRef.value ? paginationRef.value.offsetHeight : 0;
    let tableHeight = pageHeight - headerHeight - formHeight - paginationHeight - 260;
    return tableHeight;
}

const isImageUrl = (url) => {
    if (!url) {
        return false;
    }
    // 忽略大小写
    // 获取 URL 的文件扩展名
    const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp'];
    const extension = url.slice(((url.lastIndexOf(".") - 1) >>> 0) + 2);
    // 判断扩展名是否为图片扩展名之一
    return imageExtensions.includes(`.${extension.toLowerCase()}`);
}

const goodss = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    goodsList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    goodsList();
}

import {
    goodsCategoryFindAllService,
    goodsListService,
    goodsAddService,
    goodsUpdateService,
    goodsDeleteService
} from '@/api/goods'

const categorys = ref([])

const goodsCategoryList = async () => {
    let result = await goodsCategoryFindAllService();
    categorys.value = result.data;
    console.log(categorys.value);
}
goodsCategoryList();

const goodsCode = ref('')
const goodsName = ref('')
const categoryId = ref('')
const state = ref('')

const goodsList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        goodsCode: goodsCode.value ? goodsCode.value : null,
        goodsName: goodsName.value ? goodsName.value : null,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await goodsListService(params);
    total.value = result.data.total;
    goodss.value = result.data.items;

    for (let i = 0; i < goodss.value.length; i++) {
        let goods = goodss.value[i];
        for (let j = 0; j < categorys.value.length; j++) {
            if (goods.categoryId == categorys.value[j].id) {
                goods.categoryName = categorys.value[j].categoryName;
            }
        }
    }
}
goodsList();

const clear = () => {
    goodsCode.value = '';
    goodsName.value = '';
    categoryId.value = '';
    state.value = '';
    goodsList();
}

</script>



<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span style="font-size: 14px;">商品管理</span>
                <div class="extra">
                    <el-button type="primary">添加商品</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <div class="form" ref="formRef">
            <el-form :inline="true" class="form-inline">
                <el-form-item label="商品编码：">
                    <el-input v-model="goodsCode" />
                </el-form-item>

                <el-form-item label="商品名称：">
                    <el-input v-model="goodsName" />
                </el-form-item>
                <br />
                <el-form-item label="商品分类：">
                    <el-select placeholder="请选择" v-model="categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="商品状态：">
                    <el-select placeholder="请选择" v-model="state">
                        <el-option label="在售中" value=true />
                        <el-option label="已下架" value=false />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="goodsList">搜索</el-button>
                    <el-button @click="clear">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <!-- 商品列表 -->
        <el-table :data="goodss" style="width: 100%" :height="getTableHeight()" border>
            <el-table-column prop="goodsCode" label="编码" width="100" align="center"></el-table-column>
            <el-table-column prop="goodsName" label="名称" width="200" align="center"></el-table-column>
            <el-table-column prop="goodsSpec" label="规格" width="100" align="center"></el-table-column>
            <el-table-column prop="goodsUnit" label="单位" width="80" align="center"></el-table-column>
            <el-table-column prop="categoryName" label="分类" width="120" align="center"></el-table-column>

            <el-table-column label="图片" width="100" align="center">
                <template #default="{ row }">
                    <el-popover v-if="isImageUrl(row.goodsPic)" placement="right" :width="400" trigger="click">
                        <template style="display: flex; align-items: center;">
                            <el-image :src=row.goodsPic style="max-width: 400px; max-height: 400px;" />
                        </template>
                        <template #reference>
                            <el-image :src=row.goodsPic style="width: 40px; height: 40px;" />
                        </template>
                    </el-popover>
                    <div v-else style="font-size: 20px;">
                        <el-icon>
                            <DocumentDelete />
                        </el-icon>
                    </div>
                </template>
            </el-table-column>

            <el-table-column prop="manufacturer" label="生产厂家" align="center"></el-table-column>
            <el-table-column prop="minInventory" label="库存预警" width="100" align="center"></el-table-column>
            <el-table-column label="状态" width="100" align="center">
                <template #default="{ row }">
                    <el-switch v-model="row.state" :active-value=true :inactive-value=false></el-switch>
                </template>
            </el-table-column>
            <el-table-column prop="remarks" label="备注" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <div class="pagination" ref="paginationRef">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[10, 20, 30, 50]"
                layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                @current-change="onCurrentChange" style=" margin-top: 20px;justify-content: flex-end" />
        </div>
    </el-card>
</template>



<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .form-inline {
        .el-input {
            width: 220px;
        }

        .el-select {
            width: 220px;
        }

        .el-switch {
            width: 220px;
        }
    }
}
</style>
