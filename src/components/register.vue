<template>
  <div class="ui segment">
    <div class="ui fluid input">
      <input type="text" placeholder="用户名" v-model="userName" />
    </div>
    <div class="ui fluid input">
      <input type="text" placeholder="昵称" v-model="nickName" />
    </div>
    <div class="ui fluid input">
      <input type="password" placeholder="密码" v-model="passWord" />
    </div>
    <div class="ui fluid input">
      <input type="password" placeholder="确认密码" v-model="surePassWord" />
    </div>
    <div class="ui fluid button" @click="sure()">确定</div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "register",
  data() {
    return {
      userName: "",
      nickName: "",
      passWord: "",
      surePassWord: "",
    };
  },
  methods: {
    sure() {
      var that = this;
      if (that.userName && that.passWord && that.surePassWord) {
        console.log(that.userName);
        if (that.passWord === that.surePassWord) {
          axios
            .post("http://39.105.212.210/register", {
              username: that.userName,
              nickname: that.nickName,
              password: that.passWord,
            })
            .then(function (response) {
              console.log(response);
              if (response.data.code == 200) {
                that.$message({
                  message: "注册成功",
                  type: "success",
                });
                that.$router.push("/helloWorld/logIn");
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
            message: "两次输入的密码不相同",
            type: "warning",
          });
        }
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
