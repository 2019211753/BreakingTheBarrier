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

axios.defaults.baseURL = '/api'
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