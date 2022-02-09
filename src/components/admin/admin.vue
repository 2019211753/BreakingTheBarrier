<template>
  <div class="admin">
    <el-container>
      <el-aside width="270px">
        <div class="ui basic segment">
          <img
            style="width: 120px"
            class="ui centered circular image"
            :src="$store.state.me.avatar"
          />
          <h2>{{ $store.state.me.nickname }}</h2>
          <h5>{{ $store.state.me.personalSignature }}</h5>
          <div class="ui divider"></div>
          <div class="ui fluid button" @click="turnToTag()">标签管理</div>
          <div class="ui fluid button" @click="turnToUser()">用户管理</div>
          <div class="ui fluid button" @click="turnToQuestion()">问题管理</div>
          <div class="ui fluid button" @click="turnToBlog()">博客管理</div>
          <div class="ui divider" style="margin-top: 150px"></div>
          <div style="width: 150px; margin: auto">
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
            </div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
            </div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
            </div>
          </div>
        </div>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import adminArticle from "./components/adminArticleFunctions/adminArticle";
import adminTag from "./components/adminTag";
import adminUser from "./components/adminUser";
import adminBlog from "./components/adminBlogFunctions/adminBlog";

export default {
  name: "admin",
  components: { adminArticle, adminTag, adminBlog, adminUser },
  methods: {
    turnToTag() {
      this.$router.push("/BreakingTheBarrier/admin/adminTag");
    },
    turnToUser() {
      this.$router.push("/BreakingTheBarrier/admin/adminUser");
    },
    turnToQuestion() {
      this.$router.push("/BreakingTheBarrier/admin/adminArticle");
    },
    turnToBlog() {
      this.$router.push("/BreakingTheBarrier/admin/adminBlog");
    },
    turnToHome() {
      this.$router.push("/BreakingTheBarrier/home");
    },
    turnToBBS() {
      this.$router.push("/BreakingTheBarrier/BBS/questions");
    },
    turnToMine() {
      this.$router.push("/BreakingTheBarrier/mine/questionFiles");
    },
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
}

h5 {
  text-align: center;
  margin-top: -5px;
  color: grey;
}

.button {
  background-color: white;
}

.admin {
  width: 80%;
  margin: auto;
}
</style>
