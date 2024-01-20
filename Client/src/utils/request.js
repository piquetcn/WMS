//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
import router from '@/router'

//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({ baseURL });

//添加请求拦截器
instance.interceptors.request.use(
    (config) => {
        const tokenStore = useTokenStore();
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err) => {
        Promise.reject(err);
    }
)

//添加响应拦截器
instance.interceptors.response.use(
    result => {
        if (result.data.code === 0) {
            return result.data;
        }
        ElMessage.error(result.data.message ? result.data.message : '错误请求');
        // ElMessage.error(result.data.msg ? result.data.msg : '错误请求');
        
        return Promise.reject(result.data);//异步的状态转化成失败的状态
    },
    err => {
        // 判断响应状态码，401则跳转登录页面
        if (err.response.status === 401) {
            ElMessage.error('请先登录');
            router.push('/login');
        } else {
            ElMessage.error('错误请求');
        }

        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;