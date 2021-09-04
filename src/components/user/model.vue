<template>
  <el-container>
    <el-header height="40px"><headbar></headbar></el-header>
    <el-main
      ><div><router-view></router-view></div
    ></el-main>
    <el-footer><bottom></bottom></el-footer>
  </el-container>
</template>

<script>
import axios from "axios";
import headbar from "../basic/headbar";
import home from "./home/home";
import BBS from "./BBS/BBS";
import wikipedia from "./wikipedia/wikipedia";
import bottom from "../basic/bottom";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
window.addEventListener("setItem", (e) => {
  console.log(e);
  axios.defaults.headers["token"] = e.newValue;
});
export default {
  name: "model",
  components: { wikipedia, headbar, home, BBS, bottom },
};
</script>

<style scoped>
</style>
