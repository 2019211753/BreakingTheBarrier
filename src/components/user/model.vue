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
import headbar from "../basic/headbar";
import home from "./home/home";
import BBS from "./BBS/BBS";
import wikipedia from "./wikipedia/wikipedia";
import bottom from "../basic/bottom";

export default {
  name: "model",
  components: { wikipedia, headbar, home, BBS, bottom },
};
</script>

<style scoped>
</style>
