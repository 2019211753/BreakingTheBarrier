<template>
  <div>
    <headbar></headbar>
    <div><router-view></router-view></div>
  </div>
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
