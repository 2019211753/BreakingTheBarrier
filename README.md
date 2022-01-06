# project1

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


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
