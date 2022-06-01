import request from '@/utils/request'

// 查询登录日志列表
export function list(query) {
  return request({
    url: '/manage/log/userLogin/list',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function delLogininfor(ids) {
  return request({
    url: '/manage/log/userLogin/' + ids,
    method: 'delete'
  })
}

// 清空登录日志
export function cleanLogininfor() {
  return request({
    url: '/manage/log/userLogin/clean',
    method: 'delete'
  })
}
