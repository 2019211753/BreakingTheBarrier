<template>
  <div class="body">
    <div class="main">
      <div class="contents">
        <router-view></router-view>
      </div>
    </div>
    <div class="hot">
      <hotArticle></hotArticle>
      <div class="ui basic segment">
        <div class="ui segment">
          <div class="ui fluid input">
            <input type="text" placeholder="搜索..." v-model="searchContent" />
            <router-link
              :to="{
                path: '/BreakingTheBarrier/BBS/searchArticleResult',
                query: { searchContent: this.searchContent },
              }"
            >
              <button class="ui teal button" v-if="this.searchContent">
                搜索
              </button>
            </router-link>
          </div>
          <br />
          <div class="ui teal fluid button" @click="turnToAddArticle()">
            发布内容
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import hotArticle from "./components/hotArticle";
export default {
  name: "article",
  components: { hotArticle },
  data() {
    return { searchContent: "" };
  },
  methods: {
    turnToAddArticle() {
      var that = this;
      that.$router.push("/BreakingTheBarrier/BBS/article/addArticle");
    },
  },
};
</script>

<style scoped>
.body {
  width: 80%;

  margin: auto;
  margin-top: 20px;

}

.main {
  width: 64%;
  float: left;
}

.hot {

  width: 35%;
  float: left;
  margin-left: 1%;
}
</style>
