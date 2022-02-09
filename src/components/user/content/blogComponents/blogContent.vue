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
  width: 70%;
  float: left;
}
.side {
  margin-top: 20px;
  width: 30%;
  float: left;
}
.specific {
  width: 90%;
  margin: auto;
}

.writer {
  margin: auto;
  width: 90%;
}
</style>
