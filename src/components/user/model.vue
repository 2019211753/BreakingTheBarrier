<template>
  <el-container class="el-container">
    <el-header height="40px"><headbar></headbar></el-header>
    <el-main
      ><div>
        <router-view :key="$router.currentRoute.fullPath"></router-view></div
    ></el-main>
    <el-footer><bottom></bottom></el-footer>
  </el-container>
</template>

<script>

import headbar from "../basic/headbar";
import home from "./home/home";
import BBS from "./BBS/BBS";
import wikipedia from "./wikipedia/wikipedia";
import bottom from "../basic/bottom";
import instance from "../../request/http";

 window.addEventListener("setItem", (e) => {
  console.log(e);
  if (e.key == "token") {
    instance.defaults.headers["token"] = e.newValue;
  }
});
export default {
  name: "model",
  components: { wikipedia, headbar, home, BBS, bottom },
};
</script>

<style scoped>
.el-container {
  width: 1200px;
}
</style>
