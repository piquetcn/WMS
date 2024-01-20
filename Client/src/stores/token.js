import { ref } from 'vue'
// 定义store
import { defineStore } from 'pinia'

export const useTokenStore = defineStore('token', () => {
    // 响应式变量
    const token = ref('')

    // 修改token值
    const setToken = (newToken) => {
        token.value = newToken
    }

    // 移除token
    const removeToken = () => {
        token.value = ''
    }

    return {
        token, setToken, removeToken
    }
}, {
    // 持久化存储
    persist: true
});
