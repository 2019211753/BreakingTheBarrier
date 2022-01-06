<template>
  <div class="admin">
    <el-container>
      <el-aside width="270px">
        <div class="ui basic segment">
          <img
            style="width: 120px"
            class="ui centered circular image"
            :src="'data:image/jpg;base64,' + avatar"
          />
          <h2>{{ nickname }}</h2>
          <h5>{{ personalSignature }}</h5>
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
import axios from "axios";

/* axios.defaults.headers["token"] = sessionStorage.getItem("token");
window.addEventListener("setItem", (e) => {
  console.log(e);
  axios.defaults.headers["token"] = e.newValue;
}); */
export default {
  name: "admin",
  data() {
    return {
      avatar: sessionStorage.getItem("avatar"),
      nickname: sessionStorage.getItem("nickname"),
      personalSignature: sessionStorage.getItem("personalSignature"),
    };
  },
  components: { adminArticle, adminTag, adminBlog, adminUser },
  methods: {
    turnToTag() {
      this.$router.push("/helloWorld/admin/adminTag");
    },
    turnToUser() {
      this.$router.push("/helloWorld/admin/adminUser");
    },
    turnToQuestion() {
      this.$router.push("/helloWorld/admin/adminArticle");
    },
    turnToBlog() {
      this.$router.push("/helloWorld/admin/adminBlog");
    },
    turnToHome() {
      this.$router.push("/helloWorld/home");
    },
    turnToBBS() {
      this.$router.push("/helloWorld/BBS/questions");
    },
    turnToMine() {
      this.$router.push("/helloWorld/mine/questionFiles");
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

.el-header {
  background-color: rgb(100, 133, 208);
}

.admin {
  width: 80%;
  margin: auto;
  /* background-color: aqua; */
}

.adminTag {
  width: 65%;
  float: left;
  /* background-color: aqua; */
}

.functions {
  margin-left: 5%;
  width: 30%;
  float: left;
}

/* .functionButtons {
  width: 90%;
  margin: auto;
} */
/* .buttons {
  margin-top: 15px;
  margin-left: 5%;
} */
.adminUser {
  width: 100%;
  float: left;
  margin-top: 100px;
  /* background-color: rgb(192, 16, 54); */
}

.adminArticle {
  width: 100%;
  float: left;
  margin-top: 100px;
  /* background-color: rgb(192, 16, 54); */
}

.adminBlog {
  width: 100%;
  float: left;
  margin-top: 100px;
  /* background-color: rgb(192, 16, 54); */
}

.bottom {
  margin-top: 100px;
}
</style>
