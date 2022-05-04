<template>
  <div>
    <div class="carousel"><carousel></carousel></div>
    <br />
    <div class="home">
      <div class="ui basic segment">
        <div class="header">
          <div class="nav">
            <ul>
              <li>
                <a href="" @click="turnHome()"
                  ><i class="sticky note outline icon"></i>全部</a
                >
              </li>
              <li>
                <a href="javascript:;" @click="turnDatabase()"
                  ><i class="server icon"></i>资料库</a
                >
              </li>
<!--              <li>-->
<!--                <a href="" @click="turnTeam()"-->
<!--                  ><i class="handshake icon"></i>组队</a-->
<!--                >-->
<!--              </li>-->
              <li>
                <router-link to="/BreakingTheBarrier/literature/information"
                  ><i class="paw icon"></i>信息百科</router-link>
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
    turnInformation() {
      this.$router.push("/BreakingTheBarrier/literature/information");
    },
    turnTeam() {
      this.$router.push("/BreakingTheBarrier/team");
    },
    turnDatabase() {
      this.$router.push("/BreakingTheBarrier/literature/data");
    },
    turnHome() {
      this.$router.push("/BreakingTheBarrier/home");
    },
  },
};
</script>

<style scoped>
.carousel {
  margin: auto;
  height: 33vw;
  max-height: 300px;
}
.nav {
  width: 55%;
  margin: 5px auto;
}
.nav ul {
  display: flex;
  padding: 0;
  justify-content: space-around;
}
.nav ul li {
  /*float: left;*/
  /*margin: -15px 10px;*/
  white-space: nowrap;
  text-align: center;
  list-style: none;
}
.nav ul li a {
  display: block;
  height: 42px;
  padding: 0 10px;
  line-height: 42px;
  font-size: 16px;
  color: black;
  font-weight: 600;
  text-decoration: none;
}
.nav ul li:hover {
  border-bottom: 4px solid orange;
}
.header {
  height: 40px;
  /*width: 50%;*/
  margin: auto;
}
.body {
  height: 800px;
  margin-top: -20px;
  z-index: -1;
}
.main {
  width: 80%;
  margin: auto;
  /*margin-top: -50px;*/
}

@media screen and (max-width: 570px){
  .nav ul li a {
    font-size: 14px;
  }
}
@media screen and (max-width: 374px) {
  .nav ul li a {
    font-size: 13px;
  }
}
</style>
