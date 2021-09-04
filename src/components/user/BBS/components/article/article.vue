<template>
  <div class="body">
    <div class="main">
      <div class="contents">
        <articleContents></articleContents>
      </div>
    </div>
    <br />
    <div class="hot">
      <hot></hot>
      <br />
      <div class="ui fluid icon input">
        <input type="text" placeholder="搜索..." v-model="searchContent" />
        <i class="inverted circular search link icon" @click="search()"></i>
      </div>
      <br />
      <div class="ui fluid button" @click="turnToAddArticle()">发布内容</div>
    </div>
  </div>
</template>

<script>
import articleContents from "./components/articleContents";
import hot from "./components/hot";
import axios from "axios";

export default {
  name: "article",
  components: { articleContents, hot },
  data() {
    return { searchContent: "" };
  },
  methods: {
    turnToAddArticle() {
      var that = this;
      that.$router.push("/helloWorld/BBS/article/addArticle");
    },
    search() {
      var that = this;
      if (that.searchContent) {
        axios
          .post("/customer/searchQuestions", { query: that.searchContent })
          .then(function (response) {
            console.log(response.data);
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message({
          message: "请输入搜索内容",
          type: "warning",
        });
      }
    },
  },
};
</script>

<style scoped>
.body {
  width: 80%;
  /* height: 760px; */
  margin: auto;
  margin-top: 20px;
  /* background-color: aqua; */
}
/* contents {
  margin-top: 200px;
} */
.main {
  width: 64%;
  float: left;
}

.hot {
  /* background-color: green; */
  /*  margin-top: 10px; */
  width: 35%;
  float: left;
  margin-left: 1%;
}
</style>
