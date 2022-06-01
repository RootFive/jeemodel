import request from '@/utils/request'

// 查询生成表数据
export function listTable(query) {
  return request({
    url: '/coding/gen/list',
    method: 'get',
    params: query
  })
}
// 查询db数据库列表
export function listDbTable(query) {
  return request({
    url: '/coding/gen/db/list',
    method: 'get',
    params: query
  })
}

// 查询表详细信息
export function getGenTable(tableId) {
  return request({
    url: '/coding/gen/' + tableId,
    method: 'get'
  })
}

// 修改代码生成信息
export function updateGenTable(data) {
  return request({
    url: '/coding/gen',
    method: 'put',
    data: data
  })
}

// 导入表
export function importTable(data) {
  return request({
    url: '/coding/gen/importTable',
    method: 'post',
    params: data
  })
}

// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: '/coding/gen/preview/' + tableId,
    method: 'get'
  })
}

// 删除表数据
export function delTable(tableIds) {
  return request({
    url: '/coding/gen/' + tableIds,
    method: 'delete'
  })
}

// 生成代码（自定义路径）
export function genCode(tableId) {
  return request({
    url: '/coding/gen/genCode/' + tableId,
    method: 'get'
  })
}

// 同步数据库
export function synchDb(tableId) {
  return request({
    url: '/coding/gen/synchDb/' + tableId,
    method: 'get'
  })
}
