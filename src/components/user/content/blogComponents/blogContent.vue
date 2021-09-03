<template>
  <div class="body">
    <div class="main">
      <div class="specific"><br /><blogSpecific></blogSpecific><br /></div>
    </div>
    <div class="side">
      <div class="writer"><br /><blogWriter></blogWriter><br /></div>
    </div>
  </div>
</template>

<script>
import blogSpecific from "./components/blogSpecific";
import blogWriter from "./components/blogWriter";
export default {
  name: "blogContent",
  components: {blogSpecific,blogWriter },
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
  width: 64%;
  float: left;


}
.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 1%;
  /* height: 600px; */

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
