<template>
  <div>
    <div class="ui segment">
      <el-row>
        <el-col :span="24"
          ><div class="header">
            <div class="nav">
              <ul>
                <li>
                  <a>
                    <router-link
                      to="/BreakingTheBarrier/mine/contents/questionFiles"
                    >
                      <i class="archive icon"></i> 提问
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link
                      to="/BreakingTheBarrier/mine/contents/blogFiles"
                    >
                      <i class="archive icon"></i> 博客
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link
                      to="/BreakingTheBarrier/mine/contents/likeFiles"
                    >
                      <i class="archive icon"></i> 点赞
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link
                      to="/BreakingTheBarrier/mine/contents/commentFiles"
                    >
                      <i class="archive icon"></i> 评论
                    </router-link>
                  </a>
                </li>
              </ul>
            </div>
          </div></el-col
        >
      </el-row>
    </div>
    <div><router-view></router-view></div>
  </div>
</template>

<script>
export default {
  name: "contents",
};
</script>

<style scoped lang="scss">
.header {
  width: 100%;
  //height: 30px;
  float: left;
  .nav ul {
    display: flex;
    white-space: nowrap;
    justify-content: space-around;
    padding: 0;
    align-items: center;
  }
  .nav ul li {
    //float: left;
    //margin: -20px 15px;
    list-style: none;
    a {
      //height: 42px;
      padding: 0 1px;
      //line-height: 42px;
      font-size: 16px;
      color: black;
      font-weight: 600;
      text-decoration: none;
    }
    a:hover {
      border-bottom: 4px solid orange;
    }
  }
}
</style>
