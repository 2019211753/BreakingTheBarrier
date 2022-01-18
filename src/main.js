 import Vue from 'vue'
 import App from './App'
 import router from './router'
 import store from './store'
 import axios from "axios"

 // 使用semanticUI
 import semantic from '../node_modules/semantic-ui-css/semantic.min.js'
 import '../node_modules/semantic-ui-css/semantic.min.css'
 Vue.use(semantic);

 // 使用elementUI
 import ElementUI from 'element-ui';
 import 'element-ui/lib/theme-chalk/index.css';
 Vue.use(ElementUI);

 //监听sessionStorge
 Vue.prototype.resetSetItem = function(key, newVal) { // 创建一个StorageEvent事件
     if (key === 'token') {
         var newStorageEvent = document.createEvent('StorageEvent');
         const storage = {
             setItem: function(k, val) {
                 sessionStorage.setItem(k, val); // 初始化创建的事件
                 newStorageEvent.initStorageEvent('setItem', false, false, k, null, val, null, null); // 派发对象
                 window.dispatchEvent(newStorageEvent)
             }
         }
         return storage.setItem(key, newVal);
     }
 }

 /*Vue.prototype.$http = axios
 axios.defaults.baseURL = '/api'*/
 import api from "./request/api";
 Vue.prototype.$api = api;
 /*
 Vue.prototype.$axios = Axios
 Axios.defaults.baseURL = '/api'
 Axios.defaults.headers.post['Content-Type'] = 'application/json'; */
 /*  Vue.config.productionTip = false */

 /* eslint-disable no-new */
 new Vue({
     el: '#app',
     router,
     store,
     components: { App },
     template: '<App/>'
 })
