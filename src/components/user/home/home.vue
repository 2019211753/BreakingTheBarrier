<template>
  <div>
    <div class="carousel"><carousel></carousel></div>
    <div class="home">
      <div class="header">
        <div class="logo"></div>
        <div class="nav">
          <ul>
            <li>
              <a href=""><i class="sticky note outline icon"></i>全部</a>
            </li>
            <li>
              <a href=""><i class="trophy icon"></i>问卷</a>
            </li>
            <li>
              <a href="" onclick="turnmine()"><i class="home icon"></i>组队</a>
            </li>
            <li>
              <a href="" onclick="turnmine()"><i class="home icon"></i>资料</a>
            </li>
            <li><a href="" onclick="turnmine()">信息百科</a></li>
          </ul>
        </div>
      </div>
      <div class="body">
        <div class="main">
          <questionnaire></questionnaire>
          <div class="inside">
            <div class="team"><team></team></div>
            <div class="database"><database></database></div>
            <div class="wikipedia">
              <wikipedia style="float: left"></wikipedia>
              <div class="ui icon input">
                <input type="text" placeholder="搜索..." />
                <i class="inverted circular search link icon"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import carousel from "./components/carousel";
import questionnaire from "./components/questionnaire";
import team from "./components/team";
import database from "./components/database";
import wikipedia from "./components/wikipedia.vue";
export default {
  name: "home",
  components: { carousel, questionnaire, team, database, wikipedia },
};
</script>

<style scoped>
.home {
  /* background-color: rgb(250, 250, 250); */
}
.carousel {
  width: 80%;
  height: 300px;
  margin: auto;
}
.nav {
  float: left;
  margin-left: 3%;
}

.nav ul li {
  float: left;
  margin: -15px 10px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0 10px;
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
  height: 40px;
  background-color: rgb(100, 133, 208);
  width: 50%;
  margin: auto;
  margin-top: -20px;
}

.logo {
  float: left;
  width: 6%;
  height: 100%;
}

li {
  list-style: none;
}
.body {
  height: 800px;

  margin-top: -20px;
  z-index: -1;
}

.main {
  width: 80%;
  margin: auto;
  margin-top: 20px;
  /* background-color: blue; */
}
.inside {
  width: 100%;
  margin: auto;
  margin-top: 20px;
}
.team {
  width: 60%;
  /* height: 800px; */
  /* background-color: blue; */
  float: left;
}
.database {
  width: 38%;
  height: 320px;
  margin-left: 2%;
  float: left;
  background-color: aquamarine;
}
.wikipedia {
  width: 100%;
  /*   background-color: blueviolet; */
  float: left;
  margin-top: 40px;
}
.input {
  width: 38%;
  margin-left: 2%;
  height: 40px;
}
</style>
