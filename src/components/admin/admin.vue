<template>
  <div class="admin">
    <el-container>
      <el-header style="height: 40px" class="el-header"></el-header>
      <el-main
        ><el-container>
          <el-aside width="640px"
            ><img src="../../assets/adminBG.jpg" style="width: 100%"
          /></el-aside>
          <el-main
            ><img
              class="ui centered small circular image"
              src="../../assets/avatar.jpg"
            />
            <h2>东无神话</h2>

            <h4>你好世界</h4>
          </el-main> </el-container
        ><br /><br />
        <div class="adminTag">
          <h4 class="ui horizontal divider header">
            <i class="bar chart icon"></i>
            标签管理
          </h4>
          <adminTag style="float: left"></adminTag>
        </div>
        <div class="functions">
          <h4 class="ui horizontal divider header">
            <i class="bar chart icon"></i>
            快捷入口
          </h4>
          <div class="ui fluid vertical buttons">
            <button class="ui button" @click="turnToHome()">首页</button>
            <button class="ui button" @click="turnToBBS()">论坛</button>
            <button class="ui button" @click="turnToMine()">空间</button>
          </div>
          <!-- <div class="functionButtons">
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
            </div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
            </div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
            </div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
            </div>
          </div> -->
        </div>
        <br />
        <div class="adminUser">
          <h4 class="ui horizontal divider header">
            <i class="bar chart icon"></i>
            用户管理
          </h4>
          <adminUser></adminUser>
        </div>
        <br />
        <div class="adminArticle">
          <h4 class="ui horizontal divider header">
            <i class="bar chart icon"></i>
            问题管理
          </h4>
          <adminArticle></adminArticle>
        </div>
        <br />
        <div class="adminBlog">
          <h4 class="ui horizontal divider header">
            <i class="bar chart icon"></i>
            博客管理
          </h4>
          <adminBlog></adminBlog></div
      ></el-main>
      <el-footer
        ><div class="bottom">
          <br />
          <div class="message">
            <img
              class="ui small right floated image"
              src="../../assets/wechat.png"
            />
            <h3 class="ui header">联系我们</h3>
            <div class="ui hidden divider">
              扫描右边二维码提出您的宝贵意见，么么哒
            </div>
          </div>
        </div></el-footer
      >
    </el-container>
    <!--
    <div class="ui fluid image"><img src="../../assets/bg.jpg" /></div> -->
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
    turnToHome() {
      this.$router.push("/helloWorld/home");
    },
    turnToBBS() {
      this.$router.push("/helloWorld/BBS/question");
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
h4 {
  text-align: center;
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
