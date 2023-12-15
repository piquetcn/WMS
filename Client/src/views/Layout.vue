<script setup>
import {
    House,
    Goods,
    SoldOut,
    Connection,
    Box,
    User,
    EditPen,
    Crop,
    Key,
    Management,
    Promotion,
    UserFilled,
    SwitchButton,
    CaretBottom,
    Document
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

//
import { userInfoServeice } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js';
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const getUserInfo = async () => {
    let result = await userInfoServeice();
    // 存储到pinia中
    userInfoStore.setInfo(result.data);

    localStorage.setItem('role', result.data.role);
}
getUserInfo();

const getWelcomeMsg = () => {
    let hours = new Date().getHours();
    if (hours < 12) {
        return '上午好！'
    } else if (hours < 18) {
        return '下午好！'
    } else {
        return '晚上好！'
    }

}

// 顶部下拉菜单
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus';
const router = useRouter();
const handleCommand = (command) => {
    if (command === 'logout') {
        ElMessageBox.confirm(
            '确认退出？',
            '警告',
            {
                confirmButtonText: '是的',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(async () => {
            // clear pinia token
            tokenStore.removeToken();
            userInfoStore.removeInfo();
            // login
            router.push('/login');
            ElMessage({
                type: 'success',
                message: '已退出',
            })
        })
    } else {
        router.push('/user/' + command);
    }
}

</script>



<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff" router unique-opened>
                <el-menu-item index="/home">
                    <el-icon>
                        <House />
                    </el-icon>
                    <span>首页</span>
                </el-menu-item>
                <el-sub-menu index=1>
                    <template #title>
                        <el-icon>
                            <Goods />
                        </el-icon>
                        <span>商品管理</span>
                    </template>
                    <el-menu-item index="/goods/category">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>商品分类</span>
                    </el-menu-item>
                    <el-menu-item index="/goods/manage">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>商品信息</span>
                    </el-menu-item>
                </el-sub-menu>
                <el-menu-item index="/inboundSummary/manage">
                    <el-icon>
                        <SoldOut />
                    </el-icon>
                    <span>入库汇总</span>
                </el-menu-item>
                <el-menu-item index="/supplier/manage">
                    <el-icon>
                        <Connection />
                    </el-icon>
                    <span>供应商管理</span>
                </el-menu-item>
                <el-menu-item index="/warehouse/manage">
                    <el-icon>
                        <Box />
                    </el-icon>
                    <span>仓库管理</span>
                </el-menu-item>

                <el-sub-menu index=2>
                    <template #title>
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <Key />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>

                <!-- 网站管理 -->
                <el-sub-menu v-if="userInfoStore.info.role == 'root'" index=99>
                    <!-- <el-sub-menu> -->
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>网站管理</span>
                    </template>
                    <el-menu-item index="/root/memberChange">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>成员变动</span>
                    </el-menu-item>
                    <el-menu-item index="/root/permissionSetting">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>权限设置</span>
                    </el-menu-item>
                </el-sub-menu>

            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div style="font-size: 18px;">
                    <span :textContent="getWelcomeMsg()"></span>
                    <span>{{ userInfoStore.info.nickname ? userInfoStore.info.nickname : '' }}</span>
                </div>
                <!-- 下拉菜单 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                                                                                                                    内容展示区
                                                                                                                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>WMS ©2023 Created by CaoCao</el-footer>
        </el-container>
    </el-container>
</template>



<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>
