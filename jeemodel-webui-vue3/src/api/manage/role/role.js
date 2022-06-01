import request from '@/utils/request'

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/manage/role/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getRole(id) {
  return request({
    url: '/manage/role/' + id,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/manage/role',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/manage/role',
    method: 'put',
    data: data
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/manage/role/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/manage/role/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(ids) {
  return request({
    url: '/manage/role/' + ids,
    method: 'delete'
  })
}

// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/manage/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/manage/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  return request({
    url: '/manage/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/manage/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/manage/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}
