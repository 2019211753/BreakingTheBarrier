<template>
  <div>
    <div class="carousel"><carousel></carousel></div>
    <br />
    <div class="home">
      <div class="ui basic segment">
        <div class="header">
          <div class="logo"></div>
          <div class="nav">
            <ul>
              <li>
                <a href="" @click="turnHome()"
                  ><i class="sticky note outline icon"></i>全部</a
                >
              </li>
              <li>
                <a href="" @click="turnDatabase()"
                  ><i class="server icon"></i>资料库</a
                >
              </li>
              <li>
                <a href="" @click="turnTeam()"
                  ><i class="handshake icon"></i>组队</a
                >
              </li>
              <li>
                <a href="" @click="turnWikipedia()"
                  ><i class="paw icon"></i>信息百科</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="body">
        <div class="main">
          <database></database><br />
          <team></team><br />
          <wikipedia></wikipedia>
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
  methods: {
    turnWikipedia() {
      this.$router.push("/helloWorld/wikipedia");
    },
    turnTeam() {
      this.$router.push("/helloWorld/team");
    },
    turnDatabase() {
      this.$router.push("/helloWorld/database");
    },
    turnHome() {
      this.$router.push("/helloWorld/home");
    },
  },
};
</script>

<style scoped>
.home {
  /* background-color: rgb(250, 250, 250); */
}
.carousel {
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
  color: black;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 4px solid orange;
  /* color: white; */
}

a {
  text-decoration: none;
}

.header {
  height: 40px;
  /* background-color: rgb(100, 133, 208); */
  width: 50%;
  margin: auto;
  /* margin-top: -20px; */
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
  margin-top: -50px;
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
