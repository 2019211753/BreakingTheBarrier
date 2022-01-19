<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editorId="editorId"
      :defaultConfig="toolbarConfig"
      :mode="mode"
    />
    <Editor
      style="height: 500px"
      :editorId="editorId"
      :defaultConfig="editorConfig"
      :defaultContent="getDefaultContent"
      :mode="mode"
    />
  </div>
</template>

<script>
import Vue from 'vue'
import '@wangeditor/editor/dist/css/style.css'
import {Editor, Toolbar, getEditor, removeEditor} from '@wangeditor/editor-for-vue'
import cloneDeep from 'lodash.clonedeep'

export default Vue.extend({
  name: "myEditor",
  components: {Editor, Toolbar},
  data() {
    return {
      editorId: `w-e-${Math.random().toString().slice(-5)}`, //【注意】编辑器 id ，要全局唯一
      toolbarConfig: {},
      editorConfig: {placeholder: '请输入内容...'},
      defaultContent: [
        {type: 'paragraph', children: [{text: '一行文字'}]}
      ],
      mode: 'default', // or 'simple'
    }
  },
  computed: {
    getDefaultContent() {
      return cloneDeep(this.defaultContent) //【注意】深度拷贝 defaultContent ，否则会报错
    }
  },
  beforeDestroy() {
    const editor = getEditor(this.editorId)
    if (editor == null) return

    // 【注意】组件销毁时，及时销毁编辑器
    editor.destroy()
    removeEditor(this.editorId)
  }
})
</script>

<!-- 记得引入 wangEditor css 文件，重要 ！！！ -->
<style src="@wangeditor/editor/dist/css/style.css"></style>
