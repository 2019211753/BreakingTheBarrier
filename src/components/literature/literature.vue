<template>
  <el-container>
    <el-header height="40px">
<!--      <headbar></headbar>-->
    </el-header>
    <el-main>
        <div>
          <menu-link></menu-link>
          <br>
          <!--含输入框-->
          <router-view>
          </router-view>
        </div>
    </el-main>
    <el-footer><!--<bottom></bottom>--></el-footer>
  </el-container>
</template>
<script>
  import headbar from "../basic/headbar";
  import bottom from "../basic/bottom";
  import axios from 'axios'
  import MenuLink from "./MenuLink";

  axios.defaults.headers["token"] = sessionStorage.getItem("token");
  axios.defaults.timeout = 5000
  window.addEventListener("setItem", (e) => {
    console.log(e);
    axios.defaults.headers["token"] = e.newValue;
  });

  export default {
    name: "literature",
    data() {
      return {
        fileName: '',
        total: 100,
        contentList: [],
        pageSize: 1,
        currentPage: 5,
        PageSize: 1,
      }
    },
    methods: {
      handleSizeChange() {
        console.log('size-change函数');
      },
      handleCurrentChange(val) {
        console.log('handleCurrentChange函数');
        console.log(val);
      },
      itemClick() {
        console.log('---+++');
      }
    },
    components: {
      headbar,
      bottom,
      MenuLink,
    }
  }
</script>

<style scoped>

</style>
