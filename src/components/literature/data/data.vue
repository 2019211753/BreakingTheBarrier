<template>
  <div class="ui container">
    <sec-menu></sec-menu>
    <br>
    <div class="ui container"
         style="display: inline-block">
      <!--文件展示区-->
      <file-display @handlePage="handlePage">
        <file-display-c-item
          slot="firstRow"
          v-for="(item, index) in files.content"
          :mySrc="files.mySrc"
          :file-name="item.name"
          :file-id="item.id"
          :key="index">
<!--          mySrc="../../../../static/img/fileCategory/unknown.png">-->
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
  </div>
</template>

<script>
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
        }
      }
    },
    mounted() {
      this.handleCurrentChange(1)
    },
    updated() {
      // console.log(this.files.content);
    },
    methods: {
      handlePage(page) { //保留子组件fileDisplay传输的数据
        // console.log(page);
        this.page.totalElements = page.totalElements
        this.page.totalPages = page.totalPages
        this.page.numberOfElements = page.numberOfElements
        // console.log(this.page.numberOfElements);
        // console.log(this.page);
      },
      handleCurrentChange(val) {
        this.page.currentPage = val
        // console.log(this.page.currentPage);
        axios('/files/find', {
          params: {
            query: '',
            pageIndex: this.page.currentPage - 1
          }
        }).then(res => {
          // console.log(res);
          this.files.content = res.data.data.page.content
          // console.log(this.files.content);
          this.page.numberOfElements = res.data.data.page.numberOfElements
          // console.log(this.page.numberOfElements);
        })
      },
    },
    components: {
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
