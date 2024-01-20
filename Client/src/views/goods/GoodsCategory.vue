<script setup>

import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'

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

const categorys = ref([])

import {
    goodsCategoryListService,
    goodsCategoryAddService,
    goodsCategoryUpdateService,
    goodsCategoryDeleteService
} from '@/api/goods'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(0)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    goodsCategoryList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    goodsCategoryList();
}

const categoryName = ref('')
const categoryAcronyms = ref('')

const goodsCategoryList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryName: categoryName.value ? categoryName.value : null,
        categoryAcronyms: categoryAcronyms.value ? categoryAcronyms.value : null
    }
    let result = await goodsCategoryListService(params);
    total.value = result.data.total;
    categorys.value = result.data.items;
}
goodsCategoryList();

//清除搜索框
const clear = () => {
    categoryName.value = '',
        categoryAcronyms.value = '',
        goodsCategoryList()
}

//控制分类弹窗
const addDialogVisible = ref(false)
const updateDialogVisible = ref(false)

const categoryModel = ref({
    categoryName: '',
    categoryAcronyms: ''
})
//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
    ],
    categoryAcronyms: [
        { required: true, message: '请输入分类简拼', trigger: 'blur' },
    ]
}
// 调用接口添加表单
const addCategory = async () => {
    let result = await goodsCategoryAddService(categoryModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    addDialogVisible.value = false;
    goodsCategoryList();
}

const editDialog = (row) => {
    updateDialogVisible.value = true;

    categoryModel.value.categoryName = row.categoryName;
    categoryModel.value.categoryAcronyms = row.categoryAcronyms;
    categoryModel.value.id = row.id;
}

const updateCategory = async () => {
    let result = await goodsCategoryUpdateService(categoryModel.value);
    ElMessage.success(result.msg ? result.msg : '更新成功');

    updateDialogVisible.value = false;
    goodsCategoryList();
}

const clearData = () => {
    categoryModel.value.categoryName = '';
    categoryModel.value.categoryAcronyms = ''
}

const deleteCategory = (row) => {
    ElMessageBox.confirm(
        '确认删除？',
        '警告',
        {
            confirmButtonText: '是的',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            console.log(row.id);
            let result = await goodsCategoryDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: result.msg ? result.msg : '删除成功',
            });
            goodsCategoryList();

        })
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span>商品分类</span>
                <div class="extra">
                    <el-button type="primary" @click="addDialogVisible = true, clearData()">添加分类</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <div class="form" ref="formRef">
            <el-form :inline="true" class="form-inline">
                <el-form-item label="分类名称：">
                    <el-input v-model="categoryName" />
                </el-form-item>

                <el-form-item label="分类简拼：">
                    <el-input v-model="categoryAcronyms" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="goodsCategoryList">搜索</el-button>
                    <el-button @click="clear">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table :data="categorys" style="width: 100%" :height="getTableHeight()" border>
            <el-table-column label="序号" type="index" width="80" align="center"> </el-table-column>
            <el-table-column label="分类名称" prop="categoryName" width="200"></el-table-column>
            <el-table-column label="分类简拼" prop="categoryAcronyms" width="200"></el-table-column>
            <el-table-column label="现有商品数" prop="goodsNum" align="left"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
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

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="addDialogVisible" title="添加分类" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="分类简拼" prop="categoryAcronyms">
                    <el-input v-model="categoryModel.categoryAcronyms" minlength="1" maxlength="20"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addCategory()">确认</el-button>
                </span>
            </template>
        </el-dialog>
        
        <!-- 更新分类弹窗 -->
        <el-dialog v-model="updateDialogVisible" title="更新分类" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="分类简拼" prop="categoryAcronyms">
                    <el-input v-model="categoryModel.categoryAcronyms" minlength="1" maxlength="10"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="updateDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateCategory()">确认</el-button>
                </span>
            </template>
        </el-dialog>
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
    }
}
</style>