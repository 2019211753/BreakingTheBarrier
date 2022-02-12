<template>
  <div class="ui container">
        <sec-menu></sec-menu>
    <div style="width: fit-content;margin:0 auto">
      <search-file @search="search"></search-file>
    </div>
        <br>
        <div class="ui container"
             style="display: inline-block">
          <!--文件展示区-->
          <file-display @handlePage="handlePage">
            <file-display-c-item
              slot="firstRow"
              v-for="(item, index) in files.content"
              :mySrc="files.mySrc"
              :file-name="item.originName"
              :file-id="item.id"
              :key="index">
            </file-display-c-item>
          </file-display>
          <!--热门资料-->
          <hot-file></hot-file>
        </div>
        <el-pagination
          :total="page.totalElements"
          :page-size="9"
          :current-page="page.currentPage"
          @current-change="handleCurrentChange"
          layout="total, prev, pager, next">
        </el-pagination>
<!--        <button class="ui button" @click="downFile">按钮</button>-->
      </div>
</template>

<script>
  import bottom from "../../basic/bottom";
  import headbar from "../../basic/headbar";
  import MenuLink from "../MenuLink";
  import SecMenu from "../SecMenu";
  import SearchFile from "../components/SearchFile";
  import FileDisplay from "../FileDisplay";
  import HotFile from "../HotFile";
  import axios from 'axios'
  import FileDisplayCItem from "../Information/FileDisplayCItem";
  export default {
    name: "Data",
    data() {
      return {
        page: {
          totalElements: 0,
          totalPages: 0,
          currentPage: 1,
          numberOfElements: 0
        },
        files: {
          content: [],
          mySrc: require('@/assets/img/fileCategory/pdf.png'),
          category: ''
        },
        keyword: ''
      }
    },
    mounted() {
      this.handleCurrentChange(1)
    },
    methods: {
      handlePage(page) {
        {
          //保留子组件fileDisplay传输的数据
          // console.log(page);
          this.page.totalElements = page.totalElements
          this.page.totalPages = page.totalPages
          this.page.numberOfElements = page.numberOfElements
          // console.log(this.page.numberOfElements);
          // console.log(this.page);
        }
      },
      handleCurrentChange(val) {
        this.page.currentPage = val
        // console.log(this.page.currentPage);
        {
          // axios('files/find',{
          //   params: {
          //     query: '',
          //     pageIndex: this.page.currentPage - 1
          //   }
          // })
          // .then(res => {
          //   // console.log(res.data.data.page.numberOfElements);
          //   this.files.content = res.data.data.page.content
          //   this.page.numberOfElements = res.data.data.page.numberOfElements
          // }).catch(err => {
          //   alert(err)
          // })

          let params = {
            query: '',
            pageIndex: this.page.currentPage - 1
          }
          let that = this
          that.$api.dataFind
            .findFiles(params)
            .then(res=> {
              // console.log(res.data.data.page.numberOfElements);
              // console.log(res);
              this.files.content = res.data.data.page.content
              this.page.numberOfElements = res.data.data.page.numberOfElements
            })
            .catch(err => {
              console.log(err);
            })
        }
      },
      // downFile() {
      //   axios.get('files/download',{
      //     params: {
      //       fileId: '',
      //     },
      //     headers:{
      //       // 'Content-Type': 'application/json; charset=UTF-8',
      //     },
      //     responseType:'blob',
      //   }).then(res => {
      //     console.log(res);
      //     const data = res.data
      //     const url = window.URL.createObjectURL(
      //       new Blob([data],
      //         {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}
      //         ))
      //     const link = document.createElement('a')
      //     link.style.display = 'none'
      //     link.href = url
      //     link.setAttribute('download', fileName)
      //     document.body.appendChild(link)
      //     link.click()
      //     document.body.removeChild(link)
      //   })
      // },
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
        //   // console.log(res.data.data.page.content[0]);
        // })
        //   .catch(err => {
        //     // console.log("拦截函数被执行");
        //     alert(err);
        //   })

        let that = this
        let params = {
          query: this.keyword,
          pageIndex: 0
        }
        console.log(params);
        that.$api.dataFind
          .findFiles(params)
          .then(res => {
            // console.log("函数被执行");
            // console.log(res);
            // console.log(res.data.msg);
            // console.log(res.data.data.page);
            console.log(res.data.data.page.content);
            // console.log(res.data.data.page.content[0]);
          })
          .catch(err => {
            console.log(err);
            // alert(err);
          })
      },
      search(searchFileContent) {
        // console.log("这是父组件data：");
        console.log(searchFileContent);
        this.files.content = searchFileContent
      }
    },
    components: {
      headbar,
      bottom,
      MenuLink,
      SecMenu,
      SearchFile,
      FileDisplay,
      HotFile,
      FileDisplayCItem
    }
  }
</script>

<style scoped>
  div.el-pagination {
    width: 500px;
    margin: 20px auto;
  }
</style>
