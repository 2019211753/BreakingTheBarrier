<template>
  <div class="body">
    <div class="main">
      <div class="left"><left></left><br /></div>
    </div>
    <div class="side">
      <div class="right"><br /><right></right><br /></div>
    </div>
  </div>
</template>

<script>
import left from "./components/left";
import right from "./components/right";
export default {
  name: "rank",
  components: { right, left },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
}

.main {
  margin-top: 20px;
  width: 60%;
  float: left;
}
.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 5%;
}
</style>
