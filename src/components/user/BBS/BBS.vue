<template>
  <div>
    <div class="header">
      <div class="nav">
        <ul>
          <li>
            <router-link to="/BreakingTheBarrier/BBS/questions">
              <i class="home icon"></i>问答
            </router-link>
          </li>
          <li>
            <router-link to="/BreakingTheBarrier/BBS/blogs">
              <i class="home icon"></i>博客
            </router-link>
          </li>
          <li>
            <router-link to="/BreakingTheBarrier/BBS/rank">
              <i class="home icon"></i>排行榜
            </router-link>
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

<style scoped lang="scss">
.header {
  height: 40px;
  width: 40%;
  margin: 20px auto 0 auto;
  .nav {
    > ul {
      display: flex;
      justify-content: center;
      flex-wrap: nowrap;
      > li {
        white-space: nowrap;
        margin: -15px 2vw;
        list-style: none;
        a {
          display: block;
          height: 42px;
          padding: 0 10px;
          line-height: 42px;
          font-size: 16px;
          color: grey;
          font-weight: 600;
        }
        a:hover {
          border-bottom: 4px solid orange;
        }
      }
    }
  }
}
@media screen and (max-width: 570px){
  .header {
    //height: 15px;
  }
  .header .nav > ul > li a {
    font-size: 14px;
  }
}
</style>
