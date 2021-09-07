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
  /* height: 760px; */
  /* background-color: aqua; */
}

.main {
  margin-top: 20px;
  width: 60%;
  float: left;
  /* background-color: bisque; */
}
.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 5%;
  /* height: 600px; */
  /* background-color: bisque; */
}
.specific {
  width: 90%;
  margin: auto;
  /*  height: 200px; */
  /* background-color: red; */
}

.writer {
  margin: auto;
  width: 90%;
  /* height: 200px; */
  /* background-color: red; */
}
</style>
