<template>
  <div>
    <div class="background"></div>
    <div class="a">
      <div class="b">
        <img :src="imgSrc" height="100%" alt="" />
      </div>
      <div class="c">
        <div class="d">
          <h1>登录</h1>
          <input
            type="text"
            class="e"
            placeholder="用户名"
            v-model="userName"
          />
          <input
            type="password"
            class="e"
            placeholder="密码"
            v-model="passWord"
          />
          <button
            class="ui big teal button"
            style="margin-left: 18%; margin-top: 195px; width: 150px"
            @click="sure()"
          >
            确定
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";
export default {
  name: "logIn",
  data() {
    return { userName: "", passWord: "", imgSrc: require("../assets/bg.jpg") };
  },
  methods: {
    sure() {
      var that = this;
      if (that.userName && that.passWord) {
        axios
          .post("/login", {
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
              /* sessionStorage["token"] = response.data.data.token; */
              /* that.$store.commit("getToken", response.data.data.token); */
              that.resetSetItem("token", response.data.data.token);
              that.$router.push("/helloWorld/home");
              history.pushState(null, null, document.URL);
              window.addEventListener("popstate", function () {
                history.pushState(null, null, document.URL);
              });
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
.background {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
  /* background-image: linear-gradient(to left, #9c88ff, #3cadeb); */
  display: flex;
  justify-content: center;
}
.a {
  margin: auto;
  position: relative;
  top: 100px;
  width: 1100px;
  height: 550px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.8);
  display: flex;
}

.b {
  width: 800px;
  height: 550px;

  background-size: cover;
}

.c {
  width: 300px;
  height: 550px;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.d {
  width: 250px;
  height: 500px;
}

.d h1 {
  font: 900 30px "";
}

.e {
  width: 230px;
  margin: 20px 0;
  outline: none;
  border: 0;
  padding: 10px;
  border-bottom: 3px solid rgb(80, 80, 170);
  font: 900 16px "";
}

.f {
  float: right;
  margin: 10px 0;
}

.g {
  position: absolute;
  margin: 20px;
  bottom: 40px;
  display: block;
  width: 200px;
  height: 60px;
  font: 900 30px "";
  text-decoration: none;
  line-height: 50px;
  border-radius: 30px;
  background-image: linear-gradient(to left, #9c88ff, #3cadeb);
  text-align: center;
}
</style>
