// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from "axios"
/* import Axios from 'axios' */
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 引入js文件，这里直接使用的是这个文件里的路径，不需要再resolver中修改路径之类的操作
import semantic from '../node_modules/semantic-ui-css/semantic.min.js'
// 引入css文件
import '../node_modules/semantic-ui-css/semantic.min.css'

Vue.use(semantic);

// 使用elementUI
Vue.use(ElementUI);
//监听sessionStorge

Vue.prototype.resetSetItem = function (key, newVal) {
  if (key === 'token') {
    // 创建一个StorageEvent事件
    var newStorageEvent = document.createEvent('StorageEvent');
    const storage = {
      setItem: function (k, val) {
        sessionStorage.setItem(k, val);
        // 初始化创建的事件
        newStorageEvent.initStorageEvent('setItem', false, false, k, null, val, null, null);
        // 派发对象
        window.dispatchEvent(newStorageEvent)
      }
    }
    return storage.setItem(key, newVal);
  }
}

// loading框设置局部刷新，且所有请求完成后关闭loading框
let loading
let needLoadingRequestCount = 0 // 声明一个对象用于存储请求个数
function startLoading() {
    loading = Vue.prototype.$loading({
        lock: true,
        text: '努力加载中...',
        /* background: 'rgba(0,0,0,0.5)', */
        target: document.querySelector('.loading-area') // 设置加载动画区域
    })
}

function endLoading() {
    loading.close()
}

function showFullScreenLoading() {
    if (needLoadingRequestCount === 0) {
        startLoading()
    }
    needLoadingRequestCount++
}

function hideFullScreenLoading() {
    if (needLoadingRequestCount <= 0) return
    needLoadingRequestCount--
    if (needLoadingRequestCount === 0) {
        endLoading()
    }
}
axios.defaults.baseURL = '/api'
axios.defaults.timeout = 200000
axios.interceptors.request.use(config => {
        if (config.isLoading !== false) { // 如果配置了isLoading: false，则不显示loading
            showFullScreenLoading()
        }
        config.headers['Content-Type'] = 'application/json;charset=UTF-8'
        return config
    }, error => {
        hideFullScreenLoading()
        return Promise.reject(error.response)
    })
    // http响应拦截器
axios.interceptors.response.use(data => {
    hideFullScreenLoading() // 响应成功关闭loading
    return data
}, error => {
    hideFullScreenLoading()
    let _status = error.response && error.response.status
    if (_status === 504 || _status === 404) {
        // 跳转404页面（目前没有，只能先跳转首页）
        //router.push({ path: '/' })
    }
    return Promise.reject(error)
})
Vue.prototype.$http = axios
    // 使用axios
    /*
    Vue.prototype.$axios = Axios
    Axios.defaults.baseURL = '/api'
    Axios.defaults.headers.post['Content-Type'] = 'application/json'; */
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})
