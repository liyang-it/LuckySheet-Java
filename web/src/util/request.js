import axios from 'axios'

import { ElNotification } from 'element-plus'

const duration = 2 * 1000

const baseURL = process.env.VUE_APP_BASE_API

const service = axios.create({
    baseURL: baseURL, // url = base url + request url
    timeout: 5000, // request timeout
})

// 请求前处理head头
service.interceptors.request.use(
    config => {
        // 在请求发送之前，可以在这里设置请求头等信息
        config.headers['Auth'] = 'liyangme';
        return config;
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
)
//响应拦截器
service.interceptors.response.use(
    response => {
        const data = response.data

        if (data.code == 500) {
            ElNotification({
                title: '错误',
                message: data.msg,
                type: 'error',
                duration: duration
            })
            return Promise.reject(new Error(data.msg || '未知异常'))
        } else {
            return response;
        }

    },
    // 请求响应（服务端）异常
    error => {
        ElNotification({
            title: '错误',
            message: '请求异常',
            type: 'error',
            duration: duration
        })
        console.info('请求异常啦', error)
        return Promise.reject(error)
    }
)

export default service