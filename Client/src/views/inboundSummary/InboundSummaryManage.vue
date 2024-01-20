<script setup>

import { onMounted, ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
    Edit,
    Delete,
} from '@element-plus/icons-vue'

// ------------------------------------------------------- 获取相关列表 -------------------------------------------------------
// 供应商列表
import {
    supplierFindAllService,
} from '@/api/supplier'
const suppliers = ref([]);
const supplierList = async () => {
    let result = await supplierFindAllService();
    suppliers.value = result.data;
}
supplierList();

// 用户昵称列表
import {
    userNicknameListService,
} from '@/api/user'
const nicknames = ref([]);
const userNicknameList = async () => {
    let result = await userNicknameListService();
    nicknames.value = result.data;
}
userNicknameList();

// 商品列表
import {
    goodsFindAllService,
} from '@/api/goods'
const goodss = ref([]);
const goodsList = async () => {
    let result = await goodsFindAllService();
    goodss.value = result.data;
}
goodsList();

// 库位列表
import {
    warehouseFindAllService,
} from '@/api/warehouse'
const warehouses = ref([]);
const warehouseList = async () => {
    let result = await warehouseFindAllService();
    warehouses.value = result.data;
}
warehouseList();

// ------------------------------------------------------- 搜索表单时间设置 -------------------------------------------------------
const getOneWeekAgo = () => {
    const end = new Date();
    const start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
    return [start, end];
}

const timeRange = ref(getOneWeekAgo());
const startDate = ref(timeRange.value[0]);
const endDate = ref(timeRange.value[1]);

const handlePickerChange = (value) => {
    timeRange.value = value;
    if (value) {
        startDate.value = value[0];
        endDate.value = value[1];
    } else {
        startDate.value = null;
        endDate.value = null;
    }
}

const shortcuts = [
    {
        text: '1周内',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            return [start, end];
        }
    },
    {
        text: '30日内',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            return [start, end];
        },
    },
    {
        text: '90日内',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            return [start, end];
        },
    },
]

// ------------------------------------------------------- 获取入库单列表 -------------------------------------------------------
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数
const inboundCode = ref('');
const supplierId = ref('');
const inboundSummarys = ref([]);
import {
    inboundSummaryListService,
    inboundSummaryAddService,
    inboundSummaryDeleteService,
    inboundSummaryUpdateService,
    inboundSummaryFindFinalCodeService,
    inboundDetailUpdateService,
    inboundDetailDeleteService,
} from '@/api/inbound'
const inboundSummaryList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        inboundCode: inboundCode.value,
        supplierId: supplierId.value,
        startDate: startDate.value,
        endDate: endDate.value
    };
    let result = await inboundSummaryListService(params);
    total.value = result.data.total;
    inboundSummarys.value = result.data.items;

    for (let i = 0; i < inboundSummarys.value.length; i++) {
        let inboundSummary = inboundSummarys.value[i];
        for (let j = 0; j < suppliers.value.length; j++) {
            if (inboundSummary.supplierId == suppliers.value[j].id) {
                inboundSummary.supplierName = suppliers.value[j].supplierName;
            }
        }
        for (let k = 0; k < nicknames.value.length; k++) {
            if (inboundSummary.operator == nicknames.value[k].id) {
                inboundSummary.operatorNickname = nicknames.value[k].nickname;
            }
        }

        let inbounded = 0;
        let totals = 0;

        for (let x = 0; x < inboundSummary.inboundDetailList.length; x++) {
            let inboundDetail = inboundSummary.inboundDetailList[x];
            for (let y = 0; y < goodss.value.length; y++) {
                if (inboundDetail.goodsId == goodss.value[y].id) {
                    inboundDetail.goodsName = goodss.value[y].goodsName;
                }
            }
            for (let z = 0; z < warehouses.value.length; z++) {
                if (inboundDetail.warehouseId == warehouses.value[z].id) {
                    inboundDetail.warehouseName = warehouses.value[z].warehouseName;
                }
            }
            if (inboundDetail.state) {
                inbounded += 1;
            }
            totals += inboundDetail.total;
        }

        if (inbounded == 0) {
            inboundSummary.inbounded = 0;
        } else if (inbounded < inboundSummary.inboundDetailList.length) {
            inboundSummary.inbounded = 1;
        } else {
            inboundSummary.inbounded = 2;
        }
        inboundSummary.totals = formatCurrencyCustom(totals);
    }
}
inboundSummaryList();

// 金额格式化
const formatCurrencyCustom = (num) => {
    return '￥' + num.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '元';
}

// 点击行展开
const expandTableRef = ref();
const handleRowClick = (row, column, event) => {
    if (column.no != 7) {
        expandTableRef.value.toggleRowExpansion(row);
    }
}

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    inboundSummaryList();
}

//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    inboundSummaryList();
}

// 表格高度设置
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

// ------------------------------------------------------- 清空 -------------------------------------------------------
// 清空搜索表单
const clear = () => {
    inboundCode.value = '';
    supplierId.value = '';
    timeRange.value = getOneWeekAgo();
    startDate.value = timeRange.value[0];
    endDate.value = timeRange.value[1];

    inboundSummaryList();
}
// 清空明细弹窗
const clearDialog = () => {
    inboundDetailModel.value.goodsId = '';
    inboundDetailModel.value.quantity = '';
    inboundDetailModel.value.price = '';
    inboundDetailModel.value.total = '';
    inboundDetailModel.value.warehouseId = '';
    inboundDetailModel.value.state = true;
}
// 清空汇总抽屉
const clearDrawer = () => {
    ElMessageBox.confirm(
        '即将丢失输入的信息，是否继续？',
        '警告',
        {
            confirmButtonText: '是的',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            inboundSummaryModel.value.inboundCode = '';
            inboundSummaryModel.value.supplierId = '';
            inboundSummaryModel.value.operator = '';
            inboundSummaryModel.value.addedTime = '';
            inboundSummaryModel.value.updateTime = '';
            inboundSummaryModel.value.remarks = '';
            inboundSummaryModel.value.inboundDetailList = [];

            handleSummaryCodeInput.value = '';

            addDrawerVisible.value = false;
            updateDrawerVisible.value = false;
        })
    inboundSummaryList();
}


// ------------------------------------------------------- 开关设置 -------------------------------------------------------
const addDrawerVisible = ref(false);
const updateDrawerVisible = ref(false);

const addDialogVisible = ref(false);
const updateDialogVisible = ref(false);

// ------------------------------------------------------- 数据模型 -------------------------------------------------------
const inboundDetailModel = ref({
    goodsId: '',
    quantity: '',
    price: '',
    total: '',
    warehouseId: '',
    state: true,
})

const inboundSummaryModel = ref({
    inboundCode: '',
    supplierId: '',
    remarks: '',
    inboundDetailList: [],
})
const handleSummaryCodeInput = ref('');
const getNewSummaryCode = async () => {
    let result = await inboundSummaryFindFinalCodeService();
    let finalInboundSummaryCode = result.data;

    /*
    const match = finalInboundSummaryCode.match(/([a-zA-Z]+)([0-9]+)/);

    const prefix = match[1];
    let numStr = match[2];
    let num = parseInt(numStr, 10) + 1;
    numStr = String(num).padStart(numStr.length, '0');
    const code = prefix + numStr;
    handleSummaryCodeInput.value = code;
    */

    let num = parseInt(finalInboundSummaryCode, 10) + 1;
    const code = String(num).padStart(8, '0');

    handleSummaryCodeInput.value = code;
    console.log(code);
}

const computedTotal = () => {
    return inboundDetailModel.value.quantity * inboundDetailModel.value.price;
}
const updateTotal = () => {
    if (inboundDetailModel.value.quantity !== null && inboundDetailModel.value.price !== null) {
        inboundDetailModel.value.total = computedTotal();
    }
}
const updatePrice = () => {
    if (inboundDetailModel.value.quantity !== null && inboundDetailModel.value.price !== null && inboundDetailModel.value.quantity !== 0) {
        inboundDetailModel.value.price = inboundDetailModel.value.total / inboundDetailModel.value.quantity;
    }
}

// ------------------------------------------------------- 添加入库单 -------------------------------------------------------
const addInboundSummary = async () => {
    inboundSummaryModel.value.inboundCode = handleSummaryCodeInput.value;

    let result = await inboundSummaryAddService(inboundSummaryModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    addDrawerVisible.value = false;
    inboundSummaryList();
}

// ------------------------------------------------------- 添加明细 -------------------------------------------------------
const addDetail = () => {
    const newDetail = {
        goodsId: inboundDetailModel.value.goodsId,
        goodsName: goodss.value.find(goods => goods.id == inboundDetailModel.value.goodsId).goodsName,
        quantity: inboundDetailModel.value.quantity,
        price: inboundDetailModel.value.price,
        total: inboundDetailModel.value.total,
        warehouseId: inboundDetailModel.value.warehouseId,
        warehouseName: warehouses.value.find(warehouse => warehouse.id == inboundDetailModel.value.warehouseId).warehouseName,
        state: inboundDetailModel.value.state,
    }
    inboundSummaryModel.value.inboundDetailList.push({ ...newDetail });
}
// 展开明细
const editDialog = (row) => {
    inboundDetailModel.value.id = row.id;
    inboundDetailModel.value.summaryId = row.summaryId;
    inboundDetailModel.value.goodsId = row.goodsId;
    inboundDetailModel.value.price = row.price;
    inboundDetailModel.value.quantity = row.quantity;
    inboundDetailModel.value.total = row.total;
    inboundDetailModel.value.state = row.state;
    inboundDetailModel.value.warehouseId = row.warehouseId;
    inboundDetailModel.value.inboundSummary = row.inboundSummary;

    updateDialogVisible.value = true;
}
// ------------------------------------------------------- 更新明细 -------------------------------------------------------
const updateDetail = async (e) => {
    if (e) {
        let result = await inboundDetailUpdateService(inboundDetailModel.value);
        ElMessage.success(result.message ? result.message : '更新成功');

        updateDialogVisible.value = false;
        inboundSummaryList();
    } else {
        inboundDetailModel.value.goodsName = goodss.value.find(goods => goods.id == inboundDetailModel.value.goodsId).goodsName;
        inboundDetailModel.value.warehouseName = warehouses.value.find(warehouse => warehouse.id == inboundDetailModel.value.warehouseId).warehouseName;

        const index = inboundSummaryModel.value.inboundDetailList.findIndex(item => item.id == inboundDetailModel.value.id);
        inboundSummaryModel.value.inboundDetailList.splice(index, 1, inboundDetailModel.value);
    }
}

const deleteDetail = (row) => {
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
            if (row) {
                let result = await inboundDetailDeleteService(row.id);
                ElMessage({
                    type: 'success',
                    message: result.message ? result.message : '删除成功',
                });
                inboundSummaryList();
            } else {
                const index = inboundSummaryModel.value.inboundDetailList.findIndex(item => item.id == inboundDetailModel.value.id);
                inboundSummaryModel.value.inboundDetailList.splice(index, 1);
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                });
            }
        })
}

// ------------------------------------------------------- 更新明细状态 -------------------------------------------------------
const setDetailState = async (row) => {
    let result = await inboundDetailUpdateService(row);
    ElMessage.success(result.msg ? result.msg : '状态已更新');
    inboundSummaryList();
}

// ------------------------------------------------------- 更新入库单 -------------------------------------------------------
const editUpdateSummaryDrawer = (row) => {

    inboundSummaryModel.value.inboundCode = row.inboundCode;
    inboundSummaryModel.value.supplierId = row.supplierId;
    inboundSummaryModel.value.supplierName = suppliers.value.find(supplier => supplier.id == row.supplierId).supplierName;
    inboundSummaryModel.value.remarks = row.remarks;
    inboundSummaryModel.value.inboundDetailList = row.inboundDetailList;
    inboundSummaryModel.value.id = row.id;

    for (let i = 0; i < inboundSummaryModel.value.inboundDetailList.length; i++) {
        let inboundDetail = inboundSummaryModel.value.inboundDetailList[i];
        inboundDetail.id = row.inboundDetailList[i].id;
        inboundDetail.goodsId = row.inboundDetailList[i].goodsId;
        inboundDetail.goodsName = goodss.value.find(goods => goods.id === inboundDetail.goodsId).goodsName;
        inboundDetail.price = row.inboundDetailList[i].price;
        inboundDetail.quantity = row.inboundDetailList[i].quantity;
        inboundDetail.total = row.inboundDetailList[i].total;
        inboundDetail.state = row.inboundDetailList[i].state;
        inboundDetail.summaryId = row.inboundDetailList[i].summaryId;
        inboundDetail.warehouseId = row.inboundDetailList[i].warehouseId;
        inboundDetail.warehouseName = warehouses.value.find(warehouse => warehouse.id === inboundDetail.warehouseId).warehouseName;
    }
    updateDrawerVisible.value = true;
}

const updateInboundSummary = async () => {
    let result = await inboundSummaryUpdateService(inboundSummaryModel.value);
    ElMessage.success(result.message ? result.message : '更新成功');

    updateDrawerVisible.value = false;
    inboundSummaryList();
}

// ------------------------------------------------------- 删除入库单 -------------------------------------------------------
const deleteSummary = (row) => {
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
            let result = await inboundSummaryDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: result.message ? result.message : '删除成功',
            });
            inboundSummaryList();
        })
}



</script>



<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" ref="headerRef">
                <span style="font-size: 14px;">入库汇总</span>
                <div class="extra">
                    <el-button type="primary" @click="getNewSummaryCode(); addDrawerVisible = true;">添加入库单</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <div class="form" ref="formRef">
            <el-form :inline="true" class="form-inline">
                <el-form-item label="入库编码：">
                    <el-input v-model="inboundCode" />
                </el-form-item>
                <el-form-item label="供应商名称：">
                    <el-select v-model="supplierId" filterable>
                        <el-option v-for="s in suppliers" :key="s.id" :label="s.supplierName" :value="s.id" />
                    </el-select>
                </el-form-item>
                <br />
                <el-form-item label="入库时间：">
                    <div class="el-p">
                        <el-date-picker v-model="timeRange" type="datetimerange" :shortcuts="shortcuts" range-separator="-"
                            start-placeholder="起始时间" end-placeholder="截止时间" @change="handlePickerChange" />
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="inboundSummaryList">搜索</el-button>
                    <el-button @click="clear">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <!-- 入库汇总列表 -->
        <el-table ref="expandTableRef" :data="inboundSummarys" style="width: 100%;" :height="getTableHeight()" border
            @row-click="handleRowClick">

            <el-table-column type="expand">
                <template #default="props">
                    <div class="expand-table">
                        <span>状态：</span>
                        <span v-if="props.row.inbounded === 0">未入库</span>
                        <span v-else-if="props.row.inbounded === 1">部分入库</span>
                        <span v-else>已全部入库</span>
                        <p>总金额：{{ props.row.totals }}</p>
                        <br />
                        <h3>明细</h3>
                        <el-table :data="props.row.inboundDetailList" style="width: auto;" border>
                            <el-table-column label="商品名称" prop="goodsName" width="100px" />

                            <el-table-column label="单价" width="100px">
                                <template #default="{ row }">
                                    {{ row.price.toFixed(2) }}
                                </template>
                            </el-table-column>
                            <el-table-column label="数量" width="100px">
                                <template #default="{ row }">
                                    {{ row.quantity.toFixed(2) }}
                                </template>
                            </el-table-column>
                            <el-table-column label="金额" width="100px">
                                <template #default="{ row }">
                                    {{ row.total.toFixed(2) }}
                                </template>
                            </el-table-column>

                            <el-table-column label="库位" prop="warehouseName" width="100px" />
                            <el-table-column label="状态">
                                <template #default="{ row }">
                                    <el-switch v-model="row.state" size="large" :active-value="true" :inactive-value="false"
                                        inline-prompt active-text="已入库" inactive-text="未入库" @change="setDetailState(row)" />
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="120" align="center">
                                <template #default="{ row }">
                                    <el-button :icon="Edit" circle plain type="primary"
                                        @click="editDialog(row)"></el-button>
                                    <el-button :icon="Delete" circle plain type="danger"
                                        @click="deleteDetail(row)"></el-button>
                                </template>
                            </el-table-column>
                            <template #empty>
                                <el-empty description="没有数据" />
                            </template>
                        </el-table>
                    </div>
                </template>
            </el-table-column>

            <el-table-column prop="inboundCode" label="编码" width="120" align="center"></el-table-column>
            <el-table-column prop="supplierName" label="供应商" width="300" align="center"></el-table-column>
            <el-table-column prop="operatorNickname" label="操作员" width="100" align="center"></el-table-column>
            <el-table-column prop="addedTime" label="新增时间" width="200" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后更新时间" width="200" align="center"></el-table-column>
            <el-table-column prop="remarks" label="备注" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editUpdateSummaryDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteSummary(row)"></el-button>
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

        <!-- 添加明细弹窗 -->
        <el-dialog v-model="addDialogVisible" title="添加明细" width="30%" class="dialog" :before-close="clearDialog">
            <el-form :model="inboundDetailModel" :rules="rules" label-width="80px" style="padding-right: 30px">
                <el-form-item label="商品：" prop="goodsId">
                    <el-select v-model="inboundDetailModel.goodsId" filterable>
                        <el-option v-for="g in goodss" :key="g.id" :value="g.id" :label="g.goodsName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="单价：" prop="price">
                    <el-input v-model="inboundDetailModel.price" @input="updateTotal"
                        :formatter="(value) => (Number(value) ? Number(value).toString() : '')"
                        :parser="(value) => (Number(value) ? Number(value) : '')" />
                </el-form-item>
                <el-form-item label="数量：" prop="quantity">
                    <el-input v-model="inboundDetailModel.quantity" @input="updateTotal"
                        :formatter="(value) => (Number(value) ? Number(value).toString() : '')"
                        :parser="(value) => (Number(value) ? Number(value) : '')" />
                </el-form-item>
                <el-form-item label="金额：" prop="total">
                    <el-input v-model="inboundDetailModel.total" @input="updatePrice"
                        :formatter="(value) => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                        :parser="(value) => value.replace(/\$\s?|(,*)/g, '')" />
                </el-form-item>
                <el-form-item label="库位：" prop="warehouseId">
                    <el-select v-model="inboundDetailModel.warehouseId" filterable>
                        <el-option v-for="w in warehouses" :key="w.id" :value="w.id" :label="w.warehouseName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态：" prop="state">
                    <el-switch v-model="inboundDetailModel.state" size="large" inline-prompt active-text="已入库"
                        inactive-text="未入库" />
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <div class="left">
                    <el-button @click="clearDialog">清空</el-button>
                </div>
                <div class="right">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addDetail()">添加</el-button>
                </div>
            </div>
        </el-dialog>

        <!-- 编辑明细弹窗 -->
        <el-dialog v-model="updateDialogVisible" title="编辑明细" width="30%" class="dialog" :before-close="clearDialog">
            <el-form :model="inboundDetailModel" :rules="rules" label-width="80px" style="padding-right: 30px">
                <el-form-item label="商品：" prop="goodsId">
                    <el-select v-model="inboundDetailModel.goodsId" filterable>
                        <el-option v-for="g in goodss" :key="g.id" :label="g.goodsName" :value="g.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="单价：" prop="price">
                    <el-input v-model="inboundDetailModel.price" @input="updateTotal"
                        :formatter="(value) => (Number(value) ? Number(value).toString() : '')"
                        :parser="(value) => (Number(value) ? Number(value) : '')" />
                </el-form-item>
                <el-form-item label="数量：" prop="quantity">
                    <el-input v-model="inboundDetailModel.quantity" @input="updateTotal"
                        :formatter="(value) => (Number(value) ? Number(value).toString() : '')"
                        :parser="(value) => (Number(value) ? Number(value) : '')" />
                </el-form-item>
                <el-form-item label="金额：" prop="total">
                    <el-input v-model="inboundDetailModel.total" @input="updatePrice"
                        :formatter="(value) => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                        :parser="(value) => value.replace(/\$\s?|(,*)/g, '')" />
                </el-form-item>
                <el-form-item label="库位：" prop="warehouseId">
                    <el-select v-model="inboundDetailModel.warehouseId" filterable>
                        <el-option v-for="w in warehouses" :key="w.id" :value="w.id" :label="w.warehouseName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态：" prop="state">
                    <el-switch v-model="inboundDetailModel.state" size="large" inline-prompt active-text="已入库"
                        inactive-text="未入库" />
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <div class="left">
                    <el-button @click="clearDialog">清空</el-button>
                    <el-button @click="deleteDetail(), updateDialogVisible = false">删除</el-button>
                </div>
                <div class="right">
                    <el-button @click="updateDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateDetail(true)">更新</el-button>
                </div>
            </div>
        </el-dialog>

        <!-- 添加入库单抽屉 -->
        <el-drawer v-model="addDrawerVisible" title="添加入库单" direction="rtl" size="40%" :before-close="clearDrawer">
            <el-form :model="inboundSummaryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="入库编码：" prop="inboundCode">
                    <el-input v-model="handleSummaryCodeInput" disabled></el-input>
                </el-form-item>
                <el-form-item label="供应商：" class="custom-form-item">
                    <el-select v-model="inboundSummaryModel.supplierId" filterable>
                        <el-option v-for="s in suppliers" :key="s.id" :label="s.supplierName" :value="s.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="入库明细：" class="table-form-item">
                    <el-table v-if="inboundSummaryModel.inboundDetailList.length !== 0"
                        :data="inboundSummaryModel.inboundDetailList" style="width: 100%" max-height="500px" border
                        @row-dblclick="editDialog">
                        <el-table-column label="商品" prop="goodsName" align="center"></el-table-column>
                        <el-table-column label="单价" align="center">
                            <template #default="{ row }">
                                {{ row.price.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="数量" align="center">
                            <template #default="{ row }">
                                {{ row.quantity.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="金额" align="center">
                            <template #default="{ row }">
                                {{ row.total.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="库位" prop="warehouseName" align="center"></el-table-column>
                        <el-table-column label="状态" align="center">
                            <template #default="{ row }">
                                <el-switch v-model="row.state" size="large" inline-prompt active-text="已入库"
                                    inactive-text="未入库" />
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button @click="clearDialog(), addDialogVisible = true">添加明细</el-button>
                </el-form-item>
                <el-form-item label="备注：" prop="remarks">
                    <el-input v-model="inboundSummaryModel.remarks" type="textarea" show-word-limit minlength="1"
                        maxlength="200" :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="addDrawerVisible = false">取消</el-button>
                <el-button type="primary" @click="addInboundSummary()">添加</el-button>
            </template>
        </el-drawer>

        <!-- 更新入库单抽屉 -->
        <el-drawer v-model="updateDrawerVisible" title="更新入库单" direction="rtl" size="40%" :before-close="clearDrawer">
            <el-form :model="inboundSummaryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="入库编码：" prop="inboundCode">
                    <el-input v-model="inboundSummaryModel.inboundCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="供应商：" class="custom-form-item">
                    <el-select v-model="inboundSummaryModel.supplierId" filterable disabled>
                        <el-option v-for="s in suppliers" :key="s.id" :label="s.supplierName" :value="s.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="入库明细：" class="table-form-item">
                    <el-table v-if="inboundSummaryModel.inboundDetailList.length !== 0"
                        :data="inboundSummaryModel.inboundDetailList" style="width: 100%" max-height="500px" border
                        @row-dblclick="editDialog">
                        <el-table-column label="商品" prop="goodsName" align="center"></el-table-column>
                        <el-table-column label="单价" align="center">
                            <template #default="{ row }">
                                {{ row.price.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="数量" align="center">
                            <template #default="{ row }">
                                {{ row.quantity.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="金额" align="center">
                            <template #default="{ row }">
                                {{ row.total.toFixed(2) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="库位" prop="warehouseName" align="center"></el-table-column>
                        <el-table-column label="状态" align="center">
                            <template #default="{ row }">
                                <el-switch v-model="row.state" size="large" inline-prompt active-text="已入库"
                                    inactive-text="未入库" />
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button @click="clearDialog(), addDialogVisible = true">添加明细</el-button>
                </el-form-item>
                <el-form-item label="备注：" prop="remarks">
                    <el-input v-model="inboundSummaryModel.remarks" type="textarea" show-word-limit minlength="1"
                        maxlength="200" :autosize="{ minRows: 5, maxRows: 5 }"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="updateDrawerVisible = false">取消</el-button>
                <el-button type="primary" @click="updateInboundSummary()">更新</el-button>
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

    .expand-table {
        padding: 20px;
    }

    .expand-table p {
        // margin-block-start: 0px;
        // margin-block-end: 0px;
        margin: 10px 0px;
    }

    .expand-table h3 {
        // margin-block-start: 0px;
        // margin-block-end: 0px;
        margin: 5px 0px;
    }

    .form-inline {
        .el-input {
            width: 220px;
        }

        :deep(.el-p .el-input__wrapper) {
            width: 548px !important;
        }
    }

    .custom-form-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .custom-form-item .el-select {
        flex: 1;
        // padding-right: 20px;
    }

    .table-form-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .table-form-item .el-button {
        flex: 1;
    }

    .dialog {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .dialog .el-select {
        width: 100%;
    }

    .dialog-footer {
        display: flex;

        .left {
            flex: 1;
        }

        .left .el-button {
            margin: 0 10px;
        }

        .right {
            display: flex;
            justify-content: flex-end;
        }

        .right .el-button {
            margin: 0 10px;
        }
    }

    .drawer-footer {
        display: flex;

        .right {
            display: flex;
            justify-content: flex-end;
        }

        .el-button {
            margin: 0 10px;
        }
    }
}
</style>