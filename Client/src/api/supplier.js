import request from '@/utils/request.js'

export const supplierListService = (params) => {
    return request.get('/supplier', { params: params })
}

export const supplierFindAllService = () => {
    return request.get('/supplier/all')
}

export const supplierAddService = (supplierData) => {
    return request.post('/supplier', supplierData)
}

export const supplierUpdateService = (supplierData) => {
    return request.put('/supplier', supplierData)
}

export const supplierDeleteService = (supplierID) => {
    return request.delete('/supplier?id=' + supplierID)
}