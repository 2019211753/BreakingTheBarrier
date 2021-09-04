<template>
  <div class="header">
    <div class="logo"></div>
    <div class="nav">
      <ul>
        <li>
          <a href=""
            ><router-link to="/helloWorld/home">
              <i class="sticky note outline icon"></i>首页</router-link
            ></a
          >
        </li>
        <li>
          <a href=""
            ><router-link to="/helloWorld/BBS/questions">
              <i class="home icon"></i>论坛</router-link
            ></a
          >
        </li>
        <li>
          <a href=""
            ><router-link to="/helloWorld/rank">
              <i class="trophy icon"></i>排行榜</router-link
            ></a
          >
        </li>
        <li style="margin-left: 270px">
          <a href=""
            ><router-link to="/helloWorld/mine/questionFiles">
              <img
                class="ui avatar image"
                src="../../assets/logo.png"
              />我的</router-link
            ></a
          >
        </li>
        <li>
          <a href="" @click="logOut()"
            ><router-link to="/"><i class="sign out icon"></i></router-link
          ></a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "headbar",
  methods: {
    logOut() {
      sessionStorage.clear();
      history.pushState(null, null, document.URL);
      window.addEventListener("popstate", function () {
        history.pushState(null, null, document.URL);
      });
    },
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
  margin-top: -15px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0px 10px;
  line-height: 42px;
  font-size: 16px;
  color: white;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 2px solid white;
  color: white;
}

a {
  text-decoration: none;
}

.header {
  width: 80%;
  height: 40px;
  background-color: rgb(100, 133, 208);
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
