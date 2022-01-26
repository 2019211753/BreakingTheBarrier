<template>
    <div class="ui icon input">
      <input type="text"
             placeholder="输入资料关键词"
             v-model="keyword">
      <i class="inverted circular search link icon" @click="searchF(keyword)"></i>
<!--      <button class="ui button"-->
<!--              @click="searchF(keyword)">提交</button>-->
    </div>
</template>

<script>
  // import {request} from "../../network/request";
  import UpFile from "./UpFile";
  import axios from 'axios'
  import DownFile from "./DownFile";
  // import qs from 'qs'
  export default {
    name: "search",
    data() {
      return {
        keyword: ''
      }
    },
    methods: {
      searchF(keyword) {
        // axios({
        //   url: '/files/find',
        //   params:{
        //     query: this.keyword,
        //     pageIndex: 0
        //   }
        // }).then(res => {
        //   // console.log("函数被执行");
        //   console.log(res);
        //   // console.log(res.data.msg);
        //   // console.log(res.data.data.page);
        //   console.log(res.data.data.page.content);
        //   let searchFileContent = res.data.data.page.content
        //   // console.log(res.data.data.page.content[0]);
        //   this.$emit('search',searchFileContent)
        // })
        // .catch(err => {
        //   // console.log("拦截函数被执行");
        //   alert(err);
        // })

        let that = this
        let params = {
          query: this.keyword,
          pageIndex: 0
        }
        // console.log(params);
        that.$api.dataFind
          .findFiles(params)
          .then(res => {
              // console.log("函数被执行");
              console.log(res);
              // console.log(res.data.msg);
              // console.log(res.data.data.page);
              console.log(res.data.data.page.content);
              let searchFileContent = res.data.data.page.content
              // console.log(res.data.data.page.content[0]);
              this.$emit('search',searchFileContent)
            })
            .catch(err => {
              console.log(err);
              // alert(err);
            })
      },
    },
    components: {
      UpFile,
      DownFile
    }
  }
</script>

<style scoped>

</style>
