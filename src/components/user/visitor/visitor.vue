<template>
  <div class="body">
    <div class="main">
      <div class="ui segment">
        <!-- <el-skeleton :loading="loading" animated v-if="loading == true">
          <template slot="template">
            <el-row :gutter="24">
              <el-col :span="6">
                <el-skeleton-item
                  style="
                    width: 130px;
                    height: 130px;
                    margin-left: 20px;
                    margin-top: 10px;
                  "
                  variant="circle image"
                /> </el-col
              ><el-col :span="1" style="height: 1px"> </el-col>
              <el-col :span="17">
                <el-skeleton-item
                  style="height: 30px; margin-top: 20px"
                  variant="text"
                />
                <el-skeleton-item variant="text" style="margin-top: 5px" />
                <el-skeleton-item
                  style="height: 20px; margin-top: 5px"
                  variant="text"
                />
                <el-skeleton-item style="height: 20px" variant="text" />
              </el-col>
            </el-row>
          </template>
        </el-skeleton> -->
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="avatar" style="margin-left: 20px; margin-top: 10px">
              <img
                :src="$store.state.others.avatar"
                alt=""
                class="ui small circular image"
              />
            </div>
          </el-col>
          <el-col :span="14">
            <div class="information" style="margin-top: -5px">
              <h2>{{ $store.state.others.nickname }}</h2>
              <p
                style="color: grey"
                v-if="$store.state.others.personalSignature"
              >
                {{ $store.state.others.personalSignature }}
              </p>
              <p style="color: grey" v-else>暂无签名</p>
              <div class="data">
                <div class="ui three mini statistics">
                  <div class="statistic">
                    <div class="value">
                      {{ $store.state.others.contentsNum }}
                    </div>
                    <div class="label">内容</div>
                  </div>
                  <div class="statistic">
                    <div class="value">
                      {{ $store.state.others.followedUserNum }}
                    </div>
                    <div class="label">粉丝</div>
                  </div>
                  <div class="statistic">
                    <div class="value">
                      {{ $store.state.others.followingUserNum }}
                    </div>
                    <div class="label">关注</div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div class="header" style="margin-top: 0px">
              <div class="nav">
                <ul>
                  <li>
                    <a
                      ><router-link
                        :to="{
                          path: '/helloWorld/visitor/questions',
                          query: { userId0: userId0 },
                        }"
                        >问题
                      </router-link>
                    </a>
                  </li>
                  <li>
                    <a
                      ><router-link
                        :to="{
                          path: '/helloWorld/visitor/blogs',
                          query: { userId0: userId0 },
                        }"
                        >博客
                      </router-link>
                    </a>
                  </li>
                  <li>
                    <a
                      ><router-link
                        :to="{
                          path: '/helloWorld/visitor/following',
                          query: { userId0: userId0 },
                        }"
                        >关注
                      </router-link>
                    </a>
                  </li>
                  <li>
                    <a
                      ><router-link
                        :to="{
                          path: '/helloWorld/visitor/collections',
                          query: { userId0: userId0 },
                        }"
                        >收藏
                      </router-link>
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div>
        <router-view></router-view>
      </div>
    </div>
    <div class="side">
      <recommend></recommend>
    </div>
  </div>
</template>

<script>
import recommend from "./components/recommend";

export default {
  name: "visitor",
  components: { recommend },
  data() {
    return {
      /*   loading: true, */
      userId0: this.$route.query.userId0,
    };
  },
  /*   created() {
    var that = this;
    setTimeout(() => {
      that.loading = false;
    }, 1000);
  }, */
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
  /* height: 600px; */
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

.avatar {
  width: 95%;
}

.information {
  width: 400px;
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
  margin: -15px 15px;
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
  height: 40px;
  /* background-color: rgb(100, 133, 208); */
  float: left;
  margin-top: 10px;
}

li {
  list-style: none;
}
</style>
