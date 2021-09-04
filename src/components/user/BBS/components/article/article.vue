<template>
  <div class="body">
    <div class="main">
      <div class="contents">
        <router-view></router-view>
      </div>
    </div>
    <br />
    <div class="hot">
      <hot></hot>
      <br />
      <div class="ui fluid input">
        <input type="text" placeholder="搜索..." v-model="searchContent" />
        <router-link
          :to="{
            path: '/helloWorld/BBS/searchArticleResult',
            query: { searchContent: this.searchContent },
          }"
        >
          <button class="ui button" v-if="this.searchContent">搜索</button>
        </router-link>
      </div>
      <br />
      <div class="ui fluid button" @click="turnToAddArticle()">发布内容</div>
    </div>
  </div>
</template>

<script>
import hot from "./components/hot";
export default {
  name: "article",
  components: { hot },
  data() {
    return { searchContent: "" };
  },
  methods: {
    turnToAddArticle() {
      var that = this;
      that.$router.push("/helloWorld/BBS/article/addArticle");
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
