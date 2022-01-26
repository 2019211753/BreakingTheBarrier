<template>
  <div class="ui basic segment">
    <div class="header">
      <div class="logo"></div>
      <div class="nav">
        <ul>
          <li>
            <a href="">
              <router-link to="/BreakingTheBarrier/home">
                <i class="sticky note outline icon"></i>首页
              </router-link>
            </a>
          </li>
          <li>
            <a href="">
              <router-link to="/BreakingTheBarrier/BBS/questions">
                <i class="home icon"></i>论坛
              </router-link>
            </a>
          </li>
          <li>
            <a href="">
              <router-link to="/BreakingTheBarrier/literature/data">
                <i class="server icon"></i>资料库
              </router-link>
            </a>
          </li>
          <li>
            <a href="">
              <router-link to="/BreakingTheBarrier/team">
                <i class="handshake icon"></i>组队
              </router-link>
            </a>
          </li>
          <li>
            <a href="">
              <router-link to="/BreakingTheBarrier/information">
                <i class="paw icon"></i>信息百科
              </router-link>
            </a>
          </li>
          <li style="margin-left: 180px">
            <a href="">
              <router-link to="/BreakingTheBarrier/mine/contents/questionFiles">
                <el-skeleton :loading="loading" animated v-if="loading == true">
                  <template slot="template"
                    ><el-skeleton-item variant="circle image" />
                  </template>
                </el-skeleton>
                <img
                  v-else
                  :src="$store.state.me.avatar"
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
 
/* import eventBus from "../eventBus"; */
/* import jwtDecode from "jwt-decode"; */
export default {
  name: "headbar",
  data() {
    return { loading: true };
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
    that.$api.personalInformation
      .getPersonalInformation()
      .then(function (response) {
        console.log(response.data);
        that.loading = false;
        that.$store.commit("getMyAvatar", response.data.data.user.avatar);
        that.$store.commit("getMyFile1", response.data.data.user);
        that.$store.commit("getMyFile2", response.data.data);
        /* eventBus.$emit("pushMsg", ); */
      })
      .catch(function (error) {
        console.log(error);
      });
    /* window.addEventListener("setItem", (e) => {
      if (e.key == "avatar") {
        console.log(e.newValue);
        that.imgSrc = e.newValue;
      }
    var decode = jwtDecode(e.newValue);
      console.log(decode);
    }); */
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
