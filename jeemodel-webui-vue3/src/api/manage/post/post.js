import request from '@/utils/request'

// 查询岗位列表
export function listPost(query) {
  return request({
    url: '/manage/post/list',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
export function getPost(id) {
  return request({
    url: '/manage/post/' + id,
    method: 'get'
  })
}

// 新增岗位
export function addPost(data) {
  return request({
    url: '/manage/post',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updatePost(data) {
  return request({
    url: '/manage/post',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delPost(ids) {
  return request({
    url: '/manage/post/' + ids,
    method: 'delete'
  })
}
