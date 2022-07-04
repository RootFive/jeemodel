import request from '@/utils/request'

// 查询场景标识列表
export function listUseScene(query) {
  return request({
    url: '/idcode/useScene/list',
    method: 'get',
    params: query
  })
}

// 查询场景标识详细
export function getUseScene(id) {
  return request({
    url: '/idcode/useScene/' + id,
    method: 'get'
  })
}

// 新增场景标识
export function addUseScene(data) {
  return request({
    url: '/idcode/useScene',
    method: 'post',
    data: data
  })
}

// 修改场景标识
export function updateUseScene(data) {
  return request({
    url: '/idcode/useScene',
    method: 'put',
    data: data
  })
}

// 删除场景标识
export function delUseScene(id) {
  return request({
    url: '/idcode/useScene/' + id,
    method: 'delete'
  })
}
