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

const suppliers = ref([])

import {
    supplierListService,
    supplierAddService,
    supplierUpdateService,
    supplierDeleteService
} from '@/api/supplier'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(0)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    supplierList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    supplierList();
}

const supplierName = ref('')

const supplierList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        supplierName: supplierName.value ? supplierName.value : null
    }
    let result = await supplierListService(params);
    total.value = result.data.total;
    suppliers.value = result.data.items;
}
supplierList();

//清除搜索框
const clear = () => {
    supplierName.value = '';
    supplierList();
}

//控制弹窗
const addDialogVisible = ref(false)
const updateDialogVisible = ref(false)

const supplierModel = ref({
    supplierName: '',
    supplierAddr: '',
    contacts: '',
    phone: '',
    remarks: ''
})
//添加分类表单校验
const rules = {
    supplierName: [
        { required: true, message: '请输入供应商名称', trigger: 'blur' },
    ],
    phone: [
        { required: true, message: '请输入联系电话', trigger: 'blur' },
    ]
}
// 调用接口添加表单
const addSupplier = async () => {
    let result = await supplierAddService(supplierModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    addDialogVisible.value = false;
    supplierList();
}

const editDialog = (row) => {
    updateDialogVisible.value = true;

    supplierModel.value.supplierName = row.supplierName;
    supplierModel.value.supplierAddr = row.supplierAddr;
    supplierModel.value.contacts = row.contacts;
    supplierModel.value.phone = row.phone;
    supplierModel.value.remarks = row.remarks;
    supplierModel.value.id = row.id;
}

const updateSupplier = async () => {
    let result = await supplierUpdateService(supplierModel.value);
    ElMessage.success(result.msg ? result.msg : '更新成功');

    updateDialogVisible.value = false;
    supplierList();
}

const clearData = () => {
    supplierModel.value.supplierName = '';
    supplierModel.value.supplierAddr = '';
    supplierModel.value.contacts = '';
    supplierModel.value.phone = '';
    supplierModel.value.remarks = '';
}

const deleteSupplier = (row) => {
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
            let result = await supplierDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: result.msg ? result.msg : '删除成功',
            });
            supplierList();

        })
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span>供应商管理</span>
                <div class="extra">
                    <el-button type="primary" @click="addDialogVisible = true, clearData()">添加供应商</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <div class="form" ref="formRef">
            <el-form :inline="true" class="form-inline">
                <el-form-item label="供应商名称：">
                    <el-input v-model="supplierName" />
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="supplierList">搜索</el-button>
                    <el-button @click="clear">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table :data="suppliers" style="width: 100%" :height="getTableHeight()" border>
            <el-table-column label="序号" type="index" width="80" align="center"></el-table-column>
            <el-table-column label="名称" prop="supplierName" width="300"></el-table-column>
            <el-table-column label="地址" prop="supplierAddr" width="300"></el-table-column>
            <el-table-column label="联系人" prop="contacts" width="150"></el-table-column>
            <el-table-column label="联系电话" prop="phone" width="200"></el-table-column>
            <el-table-column label="备注" prop="remarks" align="left"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteSupplier(row)"></el-button>
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
        <el-dialog v-model="addDialogVisible" title="添加供应商" width="30%">
            <el-form :model="supplierModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="名称" prop="supplierName">
                    <el-input v-model="supplierModel.supplierName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="supplierAddr">
                    <el-input v-model="supplierModel.supplierAddr" minlength="1" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="联系人" prop="contacts">
                    <el-input v-model="supplierModel.contacts" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="supplierModel.phone" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="supplierModel.remarks" minlength="1" maxlength="200"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addSupplier()">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 更新分类弹窗 -->
        <el-dialog v-model="updateDialogVisible" title="更新供应商" width="30%">
            <el-form :model="supplierModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="名称" prop="supplierName">
                    <el-input v-model="supplierModel.supplierName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="supplierAddr">
                    <el-input v-model="supplierModel.supplierAddr" minlength="1" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="联系人" prop="contacts">
                    <el-input v-model="supplierModel.contacts" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="supplierModel.phone" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="supplierModel.remarks" minlength="1" maxlength="200"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="updateDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateSupplier()">确认</el-button>
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