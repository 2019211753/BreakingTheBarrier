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
                    <router-link to="/helloWorld/mine/contents/questionFiles">
                      <i class="archive icon"></i> 提问
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link to="/helloWorld/mine/contents/blogFiles">
                      <i class="archive icon"></i> 博客
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link to="/helloWorld/mine/contents/commentFiles">
                      <i class="archive icon"></i> 评论
                    </router-link>
                  </a>
                </li>
                <li>
                  <a>
                    <router-link to="/helloWorld/mine/contents/likeFiles">
                      <i class="archive icon"></i> 点赞
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

<style scoped>
.nav ul li {
  float: left;
  margin: -20px 15px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0 1px;
  line-height: 42px;
  font-size: 16px;
  color: black;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 4px solid orange;
  /*   color: black; */
}

a {
  text-decoration: none;
}

.header {
  width: 100%;
  height: 30px;
  /* background-color: rgb(100, 133, 208); */
  float: left;
}

li {
  list-style: none;
}
</style>
