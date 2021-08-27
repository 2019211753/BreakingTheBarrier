<template>
  <div>
    <div class="header">
      <div class="logo"></div>
      <div class="nav">
        <ul>
          <li>
            <a href=""><i class="sticky note outline icon"></i>全部</a>
          </li>
          <li>
            <a href=""><i class="trophy icon"></i>博客</a>
          </li>
          <li>
            <a href="" onclick="turnmine()"><i class="home icon"></i>问答</a>
          </li>

          <li>
            <a href=""
              ><router-link to="/helloWorld/rank">排行榜</router-link></a
            >
          </li>
        </ul>
      </div>
    </div>
    <div class="body">
      <div class="main">
        <div class="contents">
          <contents></contents>
        </div>
      </div>
      <br />
      <div class="hot">
        <hot></hot>
        <br />
        <div class="ui fluid icon input">
          <input type="text" placeholder="搜索..." v-model="searchContent" />
          <i class="inverted circular search link icon" @click="search()"></i>
        </div>
        <br />
        <div class="ui fluid button" @click="turnToAddArticle()">发布内容</div>
      </div>
    </div>
  </div>
</template>

<script>
import contents from "./components/contents";
import hot from "./components/hot";
import bottom from "../../basic/bottom";
export default {
  name: "BBS",
  data() {
    return { searchContent: "" };
  },
  components: { contents, hot, bottom },
  methods: {
    turnToAddArticle() {
      var that = this;
      that.$router.push("/BBS/addArticle");
    },
    search() {
      var that = this;
      axios
        .post("/customer/searchQuestions", {})
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
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
  margin-top: 20px;
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
  width: 80%;
  /* height: 760px; */
  margin: auto;
  margin-top: 20px;
  /* background-color: aqua; */
}
/* contents {
  margin-top: 200px;
} */
.main {
  width: 64%;
  float: left;
}

.hot {
  /* background-color: green; */
  /*  margin-top: 10px; */

  width: 35%;
  float: left;
  margin-left: 1%;
}
</style>
