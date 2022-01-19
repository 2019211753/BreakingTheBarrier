<template>
  <div class="body">
    <div class="main">
      <div class="contents">
        <router-view></router-view>
      </div>
    </div>
    <div class="hot">
      <hotBlog></hotBlog>
      <div class="ui basic segment">
        <div class="ui segment">
          <div class="ui fluid input">
            <input type="text" placeholder="搜索..." v-model="searchContent" />
            <router-link
              :to="{
                path: '/BreakingTheBarrier/BBS/searchBlogResult',
                query: { searchContent: this.searchContent },
              }"
            >
              <button class="ui teal button" v-if="searchContent">
                搜索
              </button>
            </router-link>
          </div>
          <br />
          <div class="ui teal fluid button"  @click="turnToAddBlog()">
            发布内容
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import hotBlog from "./components/hotBlog";
export default {
  name: "blog",
  components: { hotBlog },
  data() {
    return { searchContent: "" };
  },
  methods: {
    turnToAddBlog() {
      var that = this;
      that.$router.push("/BreakingTheBarrier/BBS/blog/addBlog");
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
