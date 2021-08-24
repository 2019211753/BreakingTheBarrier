<template>
  <div class="body">
    <div class="main">
      <div class="personalCard">
        <div>
          <br />
          <div class="avatar">
            <img
              class="ui small rounded image"
              src="../../../assets/logo.png"
            />
          </div>
          <div class="information">
            <h2>{{ userData.nickname }}</h2>
            <p>{{ userData.personalSignature }}</p>
            <div class="data">
              <div class="ui four mini statistics">
                <div class="statistic">
                  <div class="value">0</div>
                  <div class="label">内容</div>
                </div>
                <div class="statistic">
                  <div class="value">{{ userData.donation }}</div>
                  <div class="label">贡献度</div>
                </div>
                <div class="statistic">
                  <div class="value">{{ userData.followed }}</div>
                  <div class="label">粉丝</div>
                </div>
                <div class="statistic">
                  <div class="value">{{ userData.following }}</div>
                  <div class="label">关注</div>
                </div>
                <!--  <div class="statistic">
                  <div class="value">{{ userData.registerTime }}</div>
                  <div class="label">在网时间</div>
                </div> -->
              </div>
            </div>
          </div>
          <div class="header">
            <div class="nav">
              <ul>
                <li>
                  <a href=""
                    ><router-link to="/helloWorld/mine/questionFiles">
                      <i class="sticky note outline icon"></i
                      >问题归档</router-link
                    ></a
                  >
                </li>
                <li>
                  <a href=""
                    ><router-link to="/helloWorld/mine/blogFiles">
                      <i class="home icon"></i>博客归档</router-link
                    ></a
                  >
                </li>
                <li>
                  <a href=""
                    ><router-link to="/helloWorld/mine/friends">
                      <i class="home icon"></i>关注</router-link
                    ></a
                  >
                </li>
                <li>
                  <a href=""
                    ><router-link to="/helloWorld/mine/message">
                      <i class="home icon"></i>消息通知</router-link
                    ></a
                  >
                </li>
                <li>
                  <a href=""
                    ><router-link to="/helloWorld/mine/information">
                      <i class="home icon"></i>资料编辑</router-link
                    ></a
                  >
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div><router-view></router-view></div>
    </div>
    <div class="side">
      <recommend></recommend>
    </div>
  </div>
</template>

<script>
import recommend from "./components/recommend";
import axios from "axios";
export default {
  components: { recommend },
  name: "mine",
  data() {
    return { userData: "" };
  },
  created() {
    var that = this;
    axios
      .get("/customer/personal")
      .then(function (response) {
        console.log(response.data.data.user);
        that.userData = response.data.data.user;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
  height: 760px;
  /* background-color: aqua; */
}

.main {
  margin-top: 20px;
  width: 64%;
  float: left;
  height: 600px;
  /* background-color: bisque; */
}
.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 1%;
  height: 380px;
  /* background-color: bisque; */
}
.personalCard {
  width: 100%;
  height: 220px;
  /* background-color: red; */
}
.avatar {
  width: 150px;
  height: 150px;
  /* background-color: rgb(82, 168, 168); */
  float: left;
}
.information {
  width: 444px;
  height: 150px;
  margin-left: 20px;
  /* background-color: rgb(50, 236, 236); */
  float: left;
}
h2 {
  margin-top: 20px;
  margin-left: 5%;
}
p {
  margin-left: 5%;
}
.data {
  width: 50%;
}

.nav ul li {
  float: left;
  margin: -15px 10px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0 1px;
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
  width: 100%;
  height: 40px;
  background-color: rgb(100, 133, 208);
  float: left;
  margin-top: 10px;
}

li {
  list-style: none;
}
</style>
