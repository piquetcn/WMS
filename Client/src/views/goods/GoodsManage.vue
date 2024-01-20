<script setup>

import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();

import { useRouter } from 'vue-router';
const router = useRouter();

import {
    Edit,
    Delete,
    DocumentDelete,
    Plus
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
    goodsDeleteService,
    goodsFindFinalCodeByCategoryIdService
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

//控制弹窗
const addDrawerVisible = ref(false)
const updateDrawerVisible = ref(false)

const goodsModel = ref({
    goodsCode: '',
    goodsName: '',
    goodsSpec: '',
    goodsUnit: '',
    categoryId: '',
    goodsPic: '',
    manufacturer: '',
    minInventory: 20,
    state: '',
    remarks: ''
})

//添加分类表单校验
const rules = {
    goodsCode: [
        { required: true, message: '请输入商品编码', trigger: 'blur' },
    ],
    goodsName: [
        { required: true, message: '请输入商品名称', trigger: 'blur' },
    ],
    goodsSpec: [
        { required: true, message: '请输入商品规格', trigger: 'blur' },
    ],
    goodsUnit: [
        { required: true, message: '请输入商品单位', trigger: 'blur' },
    ],
    categoryId: [
        { required: true, message: '请选择商品分类', trigger: 'blur' },
    ],
    minInventory: [
        { required: true, message: '请输入库存预警值', trigger: 'blur' },
        { pattern: /^(100|[1-9]?[0-9])$/, message: '请输入100以内的有效数字', trigger: 'blur' },
    ],
    state: [
        { required: true, message: '请确认商品状态', trigger: 'blur' },
    ]
}

const clearData = () => {
    goodsModel.value.goodsCode = '';
    goodsModel.value.goodsName = '';
    goodsModel.value.goodsSpec = '';
    goodsModel.value.goodsUnit = '';
    goodsModel.value.categoryId = '';
    goodsModel.value.goodsPic = '';
    goodsModel.value.manufacturer = '';
    goodsModel.value.minInventory = 20;
    goodsModel.value.state = '';
    goodsModel.value.remarks = '';

    handleInput.value = '';
    addDrawerVisible.value = false;
    updateDrawerVisible.value = false;
}

const handleInput = ref('')

const getNewGoodsCode = async (id) => {
    let result = await goodsFindFinalCodeByCategoryIdService(id);
    let finalGoodsCode = result.data;
    let nextNumberPart = 1;
    if (finalGoodsCode) {
        let numberPart = parseInt(finalGoodsCode.match(/\d+/)[0], 10);
        // 递增数字部分
        nextNumberPart = numberPart + 1;
        // 生成下一个字符串
    }
    // const prefix = finalGoodsCode.match(/[a-zA-Z]+/)[0];
    let prefix = '';
    for (let index = 0; index < categorys.value.length; index++) {
        if (categorys.value[index].id === id) {
            prefix = categorys.value[index].categoryAcronyms;
        }
    }
    let nextString = `${prefix}${nextNumberPart.toString().padStart(3, '0')}`;
    handleInput.value = nextString;
    goodsModel.value.goodsCode = nextString;
}

const handleSelectionChange = (id) => {
    getNewGoodsCode(id);
    goodsModel.value.categoryId = id;
}

// 上传图片成功后的回调函数
const uploadSuccess = (result) => {
    goodsModel.value.goodsPic = result.data;
    console.log(result.data);
}

const addCategoty = () => {
    router.push('/goods/category');
}

// 调用接口添加表单
const addGoods = async () => {
    let result = await goodsAddService(goodsModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    addDrawerVisible.value = false;
    goodsList();
}

const editDrawer = (row) => {
    updateDrawerVisible.value = true;

    goodsModel.value.id = row.id;
    goodsModel.value.categoryId = row.categoryId;

    for (let index = 0; index < categorys.value.length; index++) {
        if (categorys.value[index].id === row.categoryId) {
            goodsModel.value.categoryName = categorys.value[index].categoryName;
        }
    }

    goodsModel.value.goodsCode = row.goodsCode;
    goodsModel.value.goodsName = row.goodsName;
    goodsModel.value.goodsSpec = row.goodsSpec;
    goodsModel.value.goodsUnit = row.goodsUnit;
    goodsModel.value.goodsPic = row.goodsPic;
    goodsModel.value.manufacturer = row.manufacturer;
    goodsModel.value.minInventory = row.minInventory;
    goodsModel.value.state = row.state;
    goodsModel.value.remarks = row.remarks;
}

const updateGoods = async () => {
    let result = await goodsUpdateService(goodsModel.value);
    ElMessage.success(result.msg ? result.msg : '更新成功');

    updateDrawerVisible.value = false;
    goodsList();
}

const deleteGoods = (row) => {
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
            let result = await goodsDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: result.msg ? result.msg : '删除成功',
            });
            goodsList();
        })
}

const setGoodsState = async (row) => {

    let result = await goodsUpdateService(row);
    ElMessage.success(result.msg ? result.msg : '状态已更新');

    goodsList();
}

</script>



<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span style="font-size: 14px;">商品管理</span>
                <div class="extra">
                    <el-button type="primary" @click="addDrawerVisible = true">添加商品</el-button>
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
                    <el-popover v-if="isImageUrl(row.goodsPic)" placement="right"
                        popper-style="width: 600px; height: 600px; padding: 0;" trigger="click">
                        <template style="display: flex; align-items: center; justify-content: center;">
                            <el-image :src=row.goodsPic style="min-width: 600px; min-height: 600px;" />
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
                    <!-- <el-switch v-model="row.state" :active-value=true :inactive-value=false></el-switch> -->
                    <el-switch v-model="row.state" size="large" :active-value="true" :inactive-value="false" inline-prompt
                        active-text="在售中" inactive-text="已下架" @change="setGoodsState(row)" />
                </template>
            </el-table-column>
            <el-table-column prop="remarks" label="备注" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteGoods(row)"></el-button>
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

        <!-- 添加商品弹窗 -->
        <el-drawer v-model="addDrawerVisible" title="添加商品" direction="rtl" size="30%" :before-close="clearData">
            <el-form :model="goodsModel" :rules="rules" label-width="100px" style="padding-right: 30px">

                <el-form-item label="分类" prop="categoryId" class="custom-form-item">
                    <el-select placeholder="请选择" v-model="goodsModel.categoryId" @change="handleSelectionChange">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id" />
                    </el-select>
                    <el-button type="primary" @click="addCategoty">添加分类</el-button>
                </el-form-item>

                <el-form-item label="编码" prop="goodsCode">
                    <el-input v-model="handleInput" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="goodsName">
                    <el-input v-model="goodsModel.goodsName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="规格" prop="goodsSpec">
                    <el-input v-model="goodsModel.goodsSpec" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="单位" prop="goodsUnit">
                    <el-input v-model="goodsModel.goodsUnit" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="goodsPic">
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="goodsModel.goodsPic" :src="goodsModel.goodsPic" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="生产厂家" prop="manufacturer">
                    <el-input v-model="goodsModel.manufacturer" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="库存预警" prop="minInventory">
                    <el-input v-model="goodsModel.minInventory"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <!-- <el-switch v-model="goodsModel.state" active-value="true" inactive-value="false"></el-switch> -->
                    <el-switch v-model="goodsModel.state" size="large" active-value="true" inactive-value="false"
                        inline-prompt active-text="在售中" inactive-text="已下架" />
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="goodsModel.remarks" type="textarea" show-word-limit minlength="1" maxlength="200"
                        :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDrawerVisible = false">取消</el-button>
                    <el-button type="primary" @click="addGoods()">确认</el-button>
                </span>
            </template>
        </el-drawer>

        <!-- 更新商品弹窗 -->
        <el-drawer v-model="updateDrawerVisible" title="更新仓库" direction="rtl" size="30%" :before-close="clearData">
            <el-form :model="goodsModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类" prop="categoryId">
                    <el-input v-model="goodsModel.categoryName" disabled></el-input>
                </el-form-item>
                <el-form-item label="编码" prop="goodsCode">
                    <el-input v-model="goodsModel.goodsCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="goodsName">
                    <el-input v-model="goodsModel.goodsName" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="规格" prop="goodsSpec">
                    <el-input v-model="goodsModel.goodsSpec" disabled></el-input>
                </el-form-item>
                <el-form-item label="单位" prop="goodsUnit">
                    <el-input v-model="goodsModel.goodsUnit" disabled></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="goodsPic">
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="goodsModel.goodsPic" :src="goodsModel.goodsPic" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="生产厂家" prop="manufacturer">
                    <el-input v-model="goodsModel.manufacturer" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="库存预警" prop="minInventory">
                    <el-input v-model="goodsModel.minInventory" minlength="1" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <!-- <el-switch v-model="goodsModel.state" :active-value=true :inactive-value=false /> -->
                    <el-switch v-model="goodsModel.state" size="large" :active-value="true" :inactive-value="false"
                        inline-prompt active-text="在售中" inactive-text="已下架" />
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="goodsModel.remarks" type="textarea" show-word-limit minlength="1" maxlength="200"
                        :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="updateDrawerVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateGoods()">确认</el-button>
                </span>
            </template>
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

        .el-switch {
            width: 220px;
        }
    }

    .custom-form-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .custom-form-item .el-select {
        flex: 1;
        padding-right: 20px;
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
