<template>
  <div class="body">
    <div class="main">
      <editor></editor>
    </div>
    <div class="side">
      <div class="tags"><br /><tags></tags><br /></div>

    </div>
  </div>
</template>

<script>
import tags from "./tags";

import editor from "./editor";
export default {
  name: "addBlog",
  components: { editor,   tags },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
  height: 820px;

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
.tags {
  width: 90%;

  margin: auto;
}

</style>
