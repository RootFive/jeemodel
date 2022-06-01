import axios from 'axios'
import { Notification, MessageBox, Message, Loading } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { tansParams, blobValidate } from "@/utils/jeemodel";
import { saveAs } from 'file-saver'

let downloadLoadingInstance;

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})

// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    // 二进制数据则直接返回
    if(res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer'){
      return res.data
    }

    // code状态码 成功响应 code = 0
    const code = res.data.code;
    if (code === 0) {
      return res.data;
    }


    // 获取错误信息大类
    const msg =  res.data.msg || '系统未知错误，请反馈给管理员';
  
    if (code === 110 || code === 112112 || code === 191519) {
      Message({
        message: msg,
        type: 'error'
      })
      return Promise.reject(new Error(msg))
    } else if (code === 119 ||code === 120 || code === 122 ) {
      //子状态码和描述
      const subCode =  res.data.subCode;
      const subMsg =  res.data.subMsg;
      if (subCode === 'U0401' || subCode === 'U0402' || subCode === 'U0403'|| subCode === 'U0404') {
        MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            location.href = '/index';
          })
        }).catch(() => {});
        return Promise.reject('无效的会话，或者会话已过期，请重新登录。')        
      }else{
        Message({
          message: subMsg,
          type: 'error'
        })
        return Promise.reject(new Error(subMsg))
      }
    } else {
      Notification.error({
        title: msg
      })
      return Promise.reject('error')
    }
  },
  error => {
    console.log('err' + error)
    let { message } = error;
    if (message == "Network Error") {
      message = "后端服务接口连接异常";
    }
    else if (message.includes("timeout")) {
      message = "后端服务接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
      message = "后端服务接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

// 通用下载方法
export function download(url, params, filename) {
  downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
  return service.post(url, params, {
    transformRequest: [(params) => { return tansParams(params) }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'blob'
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = rspObj.subMsg || rspObj.msg || '系统未知错误，请反馈给管理员'
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('下载文件出现错误，请联系管理员！')
    downloadLoadingInstance.close();
  })
}

export default service
