import Vue from "vue";
import App from "./App";
import router from "./router";
import store from "./store";
import axios from "axios";

// 使用semanticUI
import semantic from "../node_modules/semantic-ui-css/semantic.min.js";
import "../node_modules/semantic-ui-css/semantic.min.css";

Vue.use(semantic);

// 使用elementUI
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

Vue.use(ElementUI);

//监听sessionStorge
Vue.prototype.resetSetItem = function(key, newVal) {
    // 创建一个StorageEvent事件
    if (key === "token") {
        var newStorageEvent = document.createEvent("StorageEvent");
        const storage = {
            setItem: function(k, val) {
                sessionStorage.setItem(k, val); // 初始化创建的事件
                newStorageEvent.initStorageEvent(
                    "setItem",
                    false,
                    false,
                    k,
                    null,
                    val,
                    null,
                    null
                ); // 派发对象
                window.dispatchEvent(newStorageEvent);
            }
        };
        return storage.setItem(key, newVal);
    }
};

//创建编辑器
import VMdPreviewHtml from '@kangc/v-md-editor/lib/preview-html';
import VueMarkdownEditor from "@kangc/v-md-editor";
import "@kangc/v-md-editor/lib/style/base-editor.css";
import vuepressTheme from "@kangc/v-md-editor/lib/theme/vuepress.js";
import "@kangc/v-md-editor/lib/theme/style/vuepress.css";
import Prism from "prismjs";
import createMermaidPlugin from "@kangc/v-md-editor/lib/plugins/mermaid/cdn";
import createEmojiPlugin from "@kangc/v-md-editor/lib/plugins/emoji/index";
import createKatexPlugin from "@kangc/v-md-editor/lib/plugins/katex/cdn";
import createTodoListPlugin from "@kangc/v-md-editor/lib/plugins/todo-list/index";
import createLineNumbertPlugin from "@kangc/v-md-editor/lib/plugins/line-number/index";
import createHighlightLinesPlugin from "@kangc/v-md-editor/lib/plugins/highlight-lines/index";
import "@kangc/v-md-editor/lib/plugins/highlight-lines/highlight-lines.css";
import hljs from "highlight.js";
import json from "highlight.js/lib/languages/json";
import createCopyCodePlugin from "@kangc/v-md-editor/lib/plugins/copy-code/index";
import "@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css";
import createAlignPlugin from "@kangc/v-md-editor/lib/plugins/align";
Vue.use(VMdPreviewHtml);
hljs.registerLanguage("json", json);
VueMarkdownEditor.use(createAlignPlugin());
VueMarkdownEditor.use(createCopyCodePlugin());
VueMarkdownEditor.use(createLineNumbertPlugin());
VueMarkdownEditor.use(createHighlightLinesPlugin());
VueMarkdownEditor.use(createEmojiPlugin());
VueMarkdownEditor.use(createKatexPlugin());
VueMarkdownEditor.use(createMermaidPlugin());
VueMarkdownEditor.use(createTodoListPlugin());
VueMarkdownEditor.use(vuepressTheme, {
    Hljs: hljs,
    Prism
    /* extend(md) {
          // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
          md.set(option).use();
      } */
});
Vue.use(VueMarkdownEditor);

/*Vue.prototype.$http = axios
 axios.defaults.baseURL = '/api'*/
import api from "./request/api";

Vue.prototype.$api = api;
import instance from "./request/http";

instance.defaults.baseURL = "/api";
/*
Vue.prototype.$axios = Axios
Axios.defaults.baseURL = '/api'
Axios.defaults.headers.post['Content-Type'] = 'application/json'; */
Vue.config.productionTip = false;

new Vue({
    el: "#app",
    router,
    store,
    components: { App },
    template: "<App/>"
});
