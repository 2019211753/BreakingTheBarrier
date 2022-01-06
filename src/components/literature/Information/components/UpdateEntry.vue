<template>
  <div v-show="showUpdateFlag">
    <h2>更新词条</h2>
    <create :flag="0" :inputFlag="0" :entryId="id"
      >
      <span slot="subText">更新</span>
    </create>
  </div>
</template>

<script>
  /*简单说两句
    该组件props中的id来自Information,然后Information中的id来自ShowUnproved，
  然后ShowUnproved中的id来自ShowUnproved中早已填充好的id；什么时候填充的？
  Information渲染（mounted）时请求了所有未审核的词条，然后存储到Information的data里
                 showUpdateFlag来自Information
  * */
  import Create from "./Create";
  import axios from 'axios'
  export default {
    name: "UpdateEntry",
    updated() {
      // console.log(this.showUpdateFlag);
    },
    props: {
      showUpdateFlag: {
        type: Boolean,
        default: false
      },
      id: Number
    },
    methods: {
      // makeSureUpdate(title, newContent) {
      //   if (title && newContent) {
      //     axios.post('infoEntry/' + this.id + '/update', {
      //       'title': title,
      //       'newContent': newContent,
      //     }).then(res => {
      //       console.log(res.data);
      //       console.log(title + '\n' + newContent);
      //       alert(res.data.msg)
      //     }).catch(err => {
      //       alert(err)
      //     })
      //   }
      //   else
      //     alert('标题或内容不能为空')
      // }
    },
    components: {
      Create
    }
  }
</script>

<style scoped>

</style>
