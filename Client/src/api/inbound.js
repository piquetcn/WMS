import request from '@/utils/request.js'

export const inboundSummaryListService = (params) => {
    return request.get('/inboundSummary', {params: params})
}

export const inboundSummaryAddService = (inboundSummaryData) => {
    return request.post('/inboundSummary', inboundSummaryData)
}

export const inboundSummaryDeleteService = (inboundSummaryId) => {
    return request.delete('/inboundSummary?id=' + inboundSummaryId)
}

export const inboundSummaryUpdateService = (inboundSummaryData) => {
    return request.put('/inboundSummary', inboundSummaryData)
}

export const inboundSummaryFindFinalCodeService = () => {
    return request.get('/inboundSummary/findFinalCode')
}

export const inboundDetailUpdateService = (inboundDetailData) => {
    return request.put('/inboundDetail', inboundDetailData)
}

export const inboundDetailDeleteService = (inboundDetailId) => {
    return request.delete('/inboundDetail?id=' + inboundDetailId)
}