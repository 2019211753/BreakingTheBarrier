<template>
  <div>
    <div class="header">
      <div class="logo"></div>
      <div class="nav">
        <ul>
          <li>
            <a href=""
              ><router-link to="/BreakingTheBarrier/BBS/questions"
                ><i class="home icon"></i>问答</router-link
              ></a
            >
          </li>
          <li>
            <a href=""
              ><router-link to="/BreakingTheBarrier/BBS/blogs"
                ><i class="home icon"></i>博客</router-link
              ></a
            >
          </li>
          <li>
            <a href=""
              ><router-link to="/BreakingTheBarrier/BBS/rank"
                ><i class="home icon"></i>排行榜</router-link
              ></a
            >
          </li>
        </ul>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "BBS",
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
  color: black;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 4px solid orange;
}

a {
  text-decoration: none;
}

.header {
  height: 40px;
  width: 40%;
  margin: auto;
  margin-top: 20px;
}

li {
  list-style: none;
}
</style>
