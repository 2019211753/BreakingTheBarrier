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
                path: '/helloWorld/BBS/searchBlogResult',
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
      that.$router.push("/helloWorld/BBS/blog/addBlog");
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
