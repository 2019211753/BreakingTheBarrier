<template>
  <el-container>
    <el-main>
        <div>
          <br>
          <!--含输入框-->
          <router-view>
          </router-view>
        </div>
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>
<script>
  import headbar from "../basic/headbar";
  import bottom from "../basic/bottom";
  import axios from 'axios'

  axios.defaults.headers["token"] = sessionStorage.getItem("token");
  axios.defaults.timeout = 5000
  window.addEventListener("setItem", (e) => {

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
      },
      turnWikipedia() {
        this.$router.push("/BreakingTheBarrier/literature/information");
      },
      turnTeam() {
        this.$router.push("/BreakingTheBarrier/team");
      },
      turnDatabase() {
        this.$router.push("/BreakingTheBarrier/literature/data");
      },
      turnHome() {
        this.$router.push("/BreakingTheBarrier/home");
      },
    },
    components: {
      headbar,
      bottom,
    }
  }
</script>

<style scoped>
  .home {
    /* background-color: rgb(250, 250, 250); */
  }
  .carousel {
    height: 300px;
    margin: auto;
  }
  .nav {
    float: left;
    margin-left: 3%;
  }

  .nav ul li {
    float: left;
    margin: -15px 10px;
  }

  .nav ul li a {
    display: block;
    height: 42px;
    padding: 0 10px;
    line-height: 42px;
    font-size: 16px;
    color: black;
    font-weight: 600;
  }

  .nav ul a:hover {
    border-bottom: 4px solid orange;
    /* color: white; */
  }

  a {
    text-decoration: none;
  }

  .header {
    height: 40px;
    /* background-color: rgb(100, 133, 208); */
    width: 50%;
    margin: auto;
    /* margin-top: -20px; */
  }

  .logo {
    float: left;
    width: 6%;
    height: 100%;
  }

  li {
    list-style: none;
  }
  .body {
    height: 800px;
    margin-top: -20px;
    z-index: -1;
  }

  .main {
    width: 80%;
    margin: auto;
    margin-top: -50px;
    /* background-color: blue; */
  }
  .inside {
    width: 100%;
    margin: auto;
    margin-top: 20px;
  }
  .team {
    width: 60%;
    /* height: 800px; */
    /* background-color: blue; */
    float: left;
  }
  .database {
    width: 38%;
    height: 320px;
    margin-left: 2%;
    float: left;
    background-color: aquamarine;
  }
  .wikipedia {
    width: 100%;
    /*   background-color: blueviolet; */
    float: left;
    margin-top: 40px;
  }
  .input {
    width: 38%;
    margin-left: 2%;
    height: 40px;
  }
</style>
