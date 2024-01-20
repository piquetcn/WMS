import request from '@/utils/request.js'

export const goodsCategoryListService = (params) => {
    return request.get('/category', {params: params})
}

export const goodsCategoryFindAllService = () => {
    return request.get('/category/all')
}

export const goodsCategoryAddService = (categoryData) => {
    return request.post('/category', categoryData)
}

export const goodsCategoryUpdateService = (categoryData) => {
    return request.put('/category', categoryData)
}

export const goodsCategoryDeleteService = (categoryID) => {
    return request.delete('/category?id=' + categoryID)
}

export const goodsListService = (params) => {
    return request.get('/goods', {params: params})
}

export const goodsAddService = (goodsData) => {
    return request.post('/goods', goodsData)
}

export const goodsUpdateService = (goodsData) => {
    return request.put('/goods', goodsData)
}

export const goodsDeleteService = (goodsID) => {
    return request.delete('/goods?id=' + goodsID)
}

export const goodsFindFinalCodeByCategoryIdService = (categoryID) => {
    return request.get('/goods/findCodeByCategoryId?categoryId=' + categoryID)
}

export const goodsFindAllService = () => {
    return request.get('/goods/all')
}