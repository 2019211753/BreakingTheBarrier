<template>
  <div class="ui basic segment">
    <div class="header">
      <div class="logo"></div>
      <div class="nav">
        <ul>
          <li>
            <a href="">
              <router-link to="/helloWorld/home">
                <i class="sticky note outline icon"></i>首页
              </router-link>
            </a>
          </li>
          <li>
            <a href="">
              <router-link to="/helloWorld/BBS/questions">
                <i class="home icon"></i>论坛
              </router-link>
            </a>
          </li>
          <!-- <li>
            <a href="">
              <router-link to="/helloWorld/rank">
                <i class="trophy icon"></i>排行榜
              </router-link>
            </a>
          </li> -->
          <li style="margin-left: 500px">
            <a href="">
              <router-link to="/helloWorld/mine/contents/questionFiles">
                <el-skeleton :loading="loading" animated v-if="loading == true">
                  <template slot="template"
                    ><el-skeleton-item variant="circle image" />
                  </template>
                </el-skeleton>
                <img
                  v-else
                  :src="'data:image/jpg;base64,' + imgSrc"
                  alt=""
                  class="ui avatar image"
                />
              </router-link>
            </a>
          </li>
          <li style="margin-left: 20px">
            <a href="" @click="logOut()">
              <router-link to="/"><i class="sign out icon"></i></router-link>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import eventBus from "../eventBus";
/* import jwtDecode from "jwt-decode"; */
export default {
  name: "headbar",
  data() {
    return { loading: true, imgSrc: "" };
  },
  methods: {
    logOut() {
      sessionStorage.clear();
      history.pushState(null, null, document.URL);
      window.addEventListener("popstate", function () {
        history.pushState(null, null, document.URL);
      });
    },
  },
  created() {
    var that = this;
    axios
      .get("/customer/personal")
      .then(function (response) {
        console.log(response.data.data.ACADEMIES);
        that.imgSrc = response.data.data.user.avatar;
        that.loading = false;
        sessionStorage["id"] = response.data.data.user.id;
        sessionStorage["avatar"] = response.data.data.user.avatar;
        sessionStorage["nickname"] = response.data.data.user.nickname;
        sessionStorage["academy"] = response.data.data.user.academy;
        sessionStorage["email"] = response.data.data.user.email;
        sessionStorage["major"] = response.data.data.user.major;
        sessionStorage["sex"] = response.data.data.user.sex;
        sessionStorage["QQ"] = response.data.data.user.qqId;
        sessionStorage["personalSignature"] =
          response.data.data.user.personalSignature;
        /* sessionStorage["avatar"] = response.data.data.user.avatar; */
        sessionStorage["donation"] = response.data.data.user.donation;
        sessionStorage["followedUserNum"] =
          response.data.data.user.followedUserNum;
        sessionStorage["followingUserNum"] =
          response.data.data.user.followingUserNum;
        eventBus.$emit("pushMsg", response.data.data.ACADEMIES);
      })
      .catch(function (error) {
        console.log(error);
      });
    window.addEventListener("setItem", (e) => {
      if (e.key == "avatar") {
        console.log(e.newValue);
        that.imgSrc = e.newValue;
      }
      /* var decode = jwtDecode(e.newValue);
      console.log(decode); */
    });
  },
};
</script>

<style scoped>
.nav {
  float: left;
  margin-left: 3%;
}

.nav ul li {
  float: left;
  margin: auto;
  margin-top: -10px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0px 10px;
  line-height: 42px;
  font-size: 16px;
  color: grey;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 3px solid orange;
  /* color: white; */
}

a {
  text-decoration: none;
}

.header {
  width: 100%;
  height: 40px;
  /* background-color: rgb(100, 133, 208); */
  margin: 0px auto;
}

.logo {
  float: left;
  width: 6%;
  height: 100%;
}

li {
  list-style: none;
}
</style>
