<template>
  <div class="body">
    <div class="main">
      <div class="specific"><articleSpecific></articleSpecific></div>
    </div>
    <div class="side">
      <div class="writer"><articleWriter></articleWriter></div>
    </div>
  </div>
</template>

<script>
import articleWriter from "./components/articleWriter";
import articleSpecific from "./components/articleSpecific";
export default {
  name: "articleContent",
  components: { articleSpecific, articleWriter },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;

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
