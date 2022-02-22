<template>
  <div class="body">
    <div class="main">
      <div class="specific"><blogSpecific></blogSpecific></div>
    </div>
    <div class="side">
      <div class="writer"><blogWriter></blogWriter></div>
    </div>
  </div>
</template>

<script>
import blogSpecific from "./components/blogSpecific";
import blogWriter from "./components/blogWriter";
export default {
  name: "blogContent",
  components: { blogSpecific, blogWriter },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
}

.main {
  margin-top: 20px;
  width: 73%;
  float: left;
}
.side {
  margin-top: 20px;
  width: 27%;
  float: left;
}
.specific {
  width: 98%;
  margin: auto;
}

.writer {
  margin: auto;
  width: 100%;
}
</style>
