<script setup>

import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();

import {
    Edit,
    Delete,
    DocumentDelete,
    Plus
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

const warehouses = ref([])

import {
    warehouseListService,
    warehouseAddService,
    warehouseUpdateService,
    warehouseDeleteService
} from '@/api/warehouse'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(0)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    warehouseList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    warehouseList();
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

const warehouseCode = ref('')
const warehouseName = ref('')
const state = ref('')

const warehouseList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        warehouseCode: warehouseCode.value ? warehouseCode.value : null,
        warehouseName: warehouseName.value ? warehouseName.value : null,
        state: state.value ? state.value : null
    }
    let result = await warehouseListService(params);
    total.value = result.data.total;
    warehouses.value = result.data.items;
}
warehouseList();

//清除搜索框
const clear = () => {
    warehouseCode.value = '';
    warehouseName.value = '';
    state.value = '';
    warehouseList();
}

//控制弹窗
const addDrawerVisible = ref(false)
const updateDrawerVisible = ref(false)

const warehouseModel = ref({
    warehouseCode: '',
    warehouseName: '',
    warehouseSize: '',
    warehousePic: '',
    warehouseLocation: '',
    state: '',
    remarks: ''
})
//添加分类表单校验
const rules = {
    warehouseCode: [
        { required: true, message: '请输入仓库编码', trigger: 'blur' },
    ],
    warehouseName: [
        { required: true, message: '请输入仓库名称', trigger: 'blur' },
    ],
    state: [
        { required: true, message: '请确认仓库状态', trigger: 'blur' },
    ]
}
// 调用接口添加表单
const addWarehouse = async () => {
    let result = await warehouseAddService(warehouseModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    addDrawerVisible.value = false;
    warehouseList();
}

// 上传图片成功后的回调函数
const uploadSuccess = (result) => {
    warehouseModel.value.warehousePic = result.data;
    console.log(result.data);
}

const editDrawer = (row) => {
    updateDrawerVisible.value = true;

    warehouseModel.value.warehouseCode = row.warehouseCode;
    warehouseModel.value.warehouseName = row.warehouseName;
    warehouseModel.value.warehouseSize = row.warehouseSize;
    warehouseModel.value.warehousePic = row.warehousePic;
    warehouseModel.value.warehouseLocation = row.warehouseLocation;
    warehouseModel.value.state = row.state;
    warehouseModel.value.remarks = row.remarks;
    warehouseModel.value.id = row.id;
}

const updateWarehouse = async () => {
    let result = await warehouseUpdateService(warehouseModel.value);
    ElMessage.success(result.msg ? result.msg : '更新成功');

    updateDrawerVisible.value = false;
    warehouseList();
}

const clearData = () => {
    warehouseModel.value.warehouseCode = '';
    warehouseModel.value.warehouseName = '';
    warehouseModel.value.warehouseSize = '';
    warehouseModel.value.warehousePic = '';
    warehouseModel.value.warehouseLocation = '';
    warehouseModel.value.state = '';
    warehouseModel.value.remarks = '';
}

const deleteWarehouse = (row) => {
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
            let result = await warehouseDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: result.msg ? result.msg : '删除成功',
            });
            warehouseList();

        })
}

const setWarehouseState = async (row) => {
    warehouseModel.value.warehouseCode = row.warehouseCode;
    warehouseModel.value.warehouseName = row.warehouseName;
    warehouseModel.value.warehouseSize = row.warehouseSize;
    warehouseModel.value.warehousePic = row.warehousePic;
    warehouseModel.value.warehouseLocation = row.warehouseLocation;
    warehouseModel.value.state = row.state;
    warehouseModel.value.remarks = row.remarks;
    warehouseModel.value.id = row.id;

    let result = await warehouseUpdateService(warehouseModel.value);
    ElMessage.success(result.msg ? result.msg : '状态已更新');

    warehouseList();
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span>仓库管理</span>
                <div class="extra">
                    <el-button type="primary" @click="addDrawerVisible = true, clearData()">添加仓库</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <div class="form" ref="formRef">
            <el-form :inline="true" class="form-inline">
                <el-form-item label="仓库编码：">
                    <el-input v-model="warehouseCode" />
                </el-form-item>
                <el-form-item label="仓库名称：">
                    <el-input v-model="warehouseName" />
                </el-form-item>
                <el-form-item label="仓库状态：">
                    <el-select placeholder="请选择" v-model="state">
                        <el-option label="正常" value="true" />
                        <el-option label="停用" value="false" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="warehouseList">搜索</el-button>
                    <el-button @click="clear">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table :data="warehouses" style="width: 100%" :height="getTableHeight()" border>
            <el-table-column label="序号" type="index" width="80" align="center"> </el-table-column>
            <el-table-column label="编码" prop="warehouseCode" width="100"></el-table-column>
            <el-table-column label="名称" prop="warehouseName" width="200"></el-table-column>
            <el-table-column label="规格" prop="warehouseSize" width="200"></el-table-column>

            <el-table-column label="图片" width="100" align="center">
                <template #default="{ row }">
                    <el-popover v-if="isImageUrl(row.warehousePic)" placement="right"
                        popper-style="width: 600px; height: 600px; padding: 0;" trigger="click">
                        <template style="display: flex; align-items: center; justify-content: center;">
                            <el-image :src=row.warehousePic style="min-width: 600px; min-height: 600px;" />
                        </template>
                        <template #reference style="display: flex; align-items: center; justify-content: center;">
                            <el-image :src=row.warehousePic style="width: 40px; height: 40px;" />
                        </template>
                    </el-popover>
                    <div v-else style="font-size: 20px;">
                        <el-icon>
                            <DocumentDelete />
                        </el-icon>
                    </div>
                </template>
            </el-table-column>

            <el-table-column label="位置" prop="warehouseLocation" width="400"></el-table-column>
            <el-table-column label="状态" width="100" align="center">
                <template #default="{ row }">
                    <el-switch v-model="row.state" size="large" :active-value="true" :inactive-value="false" inline-prompt
                        active-text="正常" inactive-text="停用" @change="setWarehouseState(row)" />
                </template>
            </el-table-column>
            <el-table-column label="备注" prop="remarks" align="left"></el-table-column>

            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteWarehouse(row)"></el-button>
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

        <!-- 添加仓库弹窗 -->
        <!-- <el-dialog v-model="addDialogVisible" title="添加仓库" width="30%"> -->
        <el-drawer v-model="addDrawerVisible" title="添加仓库" direction="rtl" size="30%">
            <el-form :model="warehouseModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="编码" prop="warehouseCode">
                    <el-input v-model="warehouseModel.warehouseCode" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="warehouseName">
                    <el-input v-model="warehouseModel.warehouseName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="规格" prop="warehouseSize">
                    <el-input v-model="warehouseModel.warehouseSize" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="warehousePic">
                    <!-- <el-input v-model="warehouseModel.warehousePic" minlength="1" maxlength="50"></el-input> -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="warehouseModel.warehousePic" :src="warehouseModel.warehousePic" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="位置" prop="warehouseLocation">
                    <el-input v-model="warehouseModel.warehouseLocation" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <!-- <el-switch v-model="warehouseModel.state" active-value="true" inactive-value="false"></el-switch> -->
                    <el-switch v-model="warehouseModel.state" size="large" active-value="true" inactive-value="false" inline-prompt
                        active-text="正常" inactive-text="停用" />
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="warehouseModel.remarks" type="textarea" show-word-limit minlength="1" maxlength="200"
                        :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDrawerVisible = false">取消</el-button>
                    <el-button type="primary" @click="addWarehouse()">确认</el-button>
                </span>
            </template>
            <!-- </el-dialog> -->
        </el-drawer>

        <!-- 更新仓库弹窗 -->
        <!-- <el-dialog v-model="updateDialogVisible" title="更新仓库" width="30%"> -->
        <el-drawer v-model="updateDrawerVisible" title="更新仓库" direction="rtl" size="30%">
            <el-form :model="warehouseModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="编码" prop="warehouseCode">
                    <el-input v-model="warehouseModel.warehouseCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="warehouseName">
                    <el-input v-model="warehouseModel.warehouseName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="规格" prop="warehouseSize">
                    <el-input v-model="warehouseModel.warehouseSize" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="warehousePic">
                    <!-- <el-input v-model="warehouseModel.warehousePic" minlength="1" maxlength="20"></el-input> -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="warehouseModel.warehousePic" :src="warehouseModel.warehousePic" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="位置" prop="warehouseLocation">
                    <el-input v-model="warehouseModel.warehouseLocation" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <!-- <el-switch v-model="warehouseModel.state" :active-value="true" :inactive-value="false" /> -->
                    <el-switch v-model="warehouseModel.state" size="large" :active-value="true" :inactive-value="false" inline-prompt
                        active-text="正常" inactive-text="停用" />
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <!-- <el-input v-model="warehouseModel.remarks" minlength="1" maxlength="200"></el-input> -->
                    <el-input v-model="warehouseModel.remarks" type="textarea" show-word-limit minlength="1" maxlength="200"
                        :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="updateDrawerVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateWarehouse()">确认</el-button>
                </span>
            </template>
            <!-- </el-dialog> -->
        </el-drawer>
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
    }
}

.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}
</style>