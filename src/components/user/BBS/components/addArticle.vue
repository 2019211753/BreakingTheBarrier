<template>
  <div class="body">
    <div class="main">
      <editor></editor>
    </div>
    <div class="side">
      <div class="tags"><br /><tags></tags><br /></div>
      <!-- <div class="choices"><br /><choices></choices><br /></div> -->
    </div>
  </div>
</template>

<script>
import tags from "./addArticleComponents/tags";
/* import choices from "./addArticleComponents/choices"; */
import editor from "./addArticleComponents/editor";
export default {
  name: "addArticle",
  components: { editor, /* choices,  */ tags },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
  height: 820px;
  /* background-color: aqua; */
}

.main {
  margin-top: 20px;
  width: 64%;
  float: left;
  /* height: 600px; */
  /* background-color: bisque; */
}
.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 1%;
  /* height: 380px; */
  /* background-color: bisque; */
}
.tags {
  width: 90%;
  /* height: 400px; */
  /* background-color: rgb(25, 22, 224); */
  margin: auto;
}
/* .choices {
  width: 90%;

  margin: auto;
  margin-top: 20px;
} */
</style>
