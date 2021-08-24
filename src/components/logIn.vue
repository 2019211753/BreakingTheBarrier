<template>
  <div class="ui segment">
    <div class="ui fluid input">
      <input type="text" placeholder="用户名" v-model="userName" />
    </div>
    <div class="ui fluid input">
      <input type="password" placeholder="密码" v-model="passWord" />
    </div>
    <div class="ui fluid button" @click="sure()">确定</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "logIn",
  data() {
    return { userName: "", passWord: "" };
  },
  methods: {
    sure() {
      var that = this;

      if (that.userName && that.passWord) {
        axios
          .post("http://39.105.212.210/login", {
            username: that.userName,
            password: that.passWord,
          })
          .then(function (response) {
            console.log(response);
            if (response.data.code == 200) {
              that.$message({
                message: "登陆成功",
                type: "success",
              });
              sessionStorage["token"] =
                response.data.data.token
              ;
              that.$router.push("/helloWorld/home");
            } else {
              that.$message({
                message: response.data.msg,
                type: "warning",
              });
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message({
          message: "请填写所有内容",
          type: "warning",
        });
      }
    },
  },
};
</script>

<style scoped>
.ui.segment {
  width: 40%;
  margin: auto;
}
.ui.input {
  margin-top: 20px;
}
.ui.button {
  margin-top: 20px;
}
</style>
