import request from '@/utils/request.js'

export const warehouseListService = (params) => {
    return request.get('/warehouse', {params: params})
}

export const warehouseAddService = (warehouseData) => {
    return request.post('/warehouse', warehouseData)
}

export const warehouseUpdateService = (warehouseData) => {
    return request.put('/warehouse', warehouseData)
}

export const warehouseDeleteService = (warehouseID) => {
    return request.delete('/warehouse?id=' + warehouseID)
}

export const warehouseFindAllService = () => {
    return request.get('/warehouse/all')
}