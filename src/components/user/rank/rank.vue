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

<style scoped lang="scss">
.body {
  width: 80%;
  margin: auto;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.main {
  margin-top: 20px;
  width: 60%;
}
.side {
  margin-top: 20px;
  width: 35%;
  //margin-left: 5%;
}
@media screen and (max-width: 580px) {
  .body {
    width: 95%;
    flex-direction: column-reverse;
    justify-content: space-around;
    .main {
      width: 100%;
    }
    .side {
      width: 100%;
      //display: none;
    }
  }
}
</style>
