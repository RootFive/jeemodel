import request from '@/utils/request'

// 查询部门列表
export function listDept(query) {
  return request({
    url: '/manage/dept/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(id) {
  return request({
    url: '/manage/dept/list/exclude/' + id,
    method: 'get'
  })
}

// 查询部门详细
export function getDept(id) {
  return request({
    url: '/manage/dept/' + id,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/manage/dept/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询部门树结构
export function roleDeptTreeselect(roleId) {
  return request({
    url: '/manage/dept/roleDeptTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/manage/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/manage/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(id) {
  return request({
    url: '/manage/dept/' + id,
    method: 'delete'
  })
}