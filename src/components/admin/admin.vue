<template>
  <div class="admin">
    <!-- 
    <div class="ui fluid image"><img src="../../assets/bg.jpg" /></div> -->
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
      <div class="functionButtons">
        <div class="ui icon buttons">
          <button class="ui button"><i class="align left icon"></i></button>
          <button class="ui button"><i class="align center icon"></i></button>
          <button class="ui button"><i class="align right icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
        </div>
        <div class="ui icon buttons">
          <button class="ui button"><i class="align left icon"></i></button>
          <button class="ui button"><i class="align center icon"></i></button>
          <button class="ui button"><i class="align right icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
        </div>
        <div class="ui icon buttons">
          <button class="ui button"><i class="align left icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
          <button class="ui button"><i class="align center icon"></i></button>
          <button class="ui button"><i class="align right icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
        </div>
        <div class="ui icon buttons">
          <button class="ui button"><i class="align left icon"></i></button>
          <button class="ui button"><i class="align center icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
          <button class="ui button"><i class="align right icon"></i></button>
          <button class="ui button"><i class="align justify icon"></i></button>
        </div>
      </div>
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
      <adminBlog></adminBlog>
    </div>
  </div>
</template>

<script>
import adminArticle from "./components/adminArticle";
import adminTag from "./components/adminTag";
import adminUser from "./components/adminUser";
import adminBlog from "./components/adminBlog";
export default {
  name: "admin",
  components: { adminArticle, adminTag, adminBlog, adminUser },
};
</script>

<style scoped>
.admin {
  width: 80%;
  margin: auto;
  background-color: aqua;
}
.adminTag {
  width: 65%;

  float: left;
  background-color: aqua;
}
.functions {
  margin-left: 5%;
  width: 30%;
  float: left;
}
.functionButtons {
  width: 90%;
  margin: auto;
}
.buttons {
  margin-top: 15px;
  margin-left: 5%;
}
.adminUser {
  float: left;
  margin-top: 20px;
  background-color: rgb(192, 16, 54);
}
.adminArticle {
  float: left;
  margin-top: 20px;
  background-color: rgb(192, 16, 54);
}
.adminBlog {
  float: left;
  margin-top: 20px;
  background-color: rgb(192, 16, 54);
}
</style>
