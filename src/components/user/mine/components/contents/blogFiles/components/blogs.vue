<template>
  <div>
    <div class="ui segment" v-if="loading == false && count == 0">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <!-- <el-skeleton :loading="loading" animated :count="3">
      <template slot="template"
        ><div class="ui segment">
          <el-container>
            <el-aside width="45px"
              ><el-skeleton-item
                variant="circle image"
                style="width: 45px; height: 45px; margin-top: 15px"
              />
            </el-aside>
            <el-main
              ><el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
            </el-main>
          </el-container>
          <el-row :gutter="20">
            <el-col :span="20"><el-skeleton-item variant="text" /></el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8"
              ><el-skeleton-item variant="image" style="height: 100px" />
            </el-col>
            <el-col :span="16"
              ><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item variant="text"
            /></el-col> </el-row
          ><el-row :gutter="20" style="margin-top: 5px">
            <el-col :span="20"> <el-skeleton-item variant="text" /></el-col>
          </el-row>
          <div class="ui divider"></div>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5"></el-col>
          </el-row>
        </div>
      </template>
    </el-skeleton> -->
    <el-skeleton :loading="loading" v-if="loading == true" animated :count="5">
      <template slot="template"
        ><div class="ui segment">
          <div class="ui large feed">
            <div class="event">
              <div class="label">
                <el-skeleton-item
                  variant="circle image"
                  style="margin-top: 10px"
                />
              </div>
              <div class="content">
                <div class="summary">
                  <el-skeleton-item variant="text" /><el-skeleton-item
                    variant="text"
                  />
                </div>
                <el-skeleton-item variant="text" /><el-skeleton-item
                  variant="text"
                /><el-skeleton-item variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
    <div class="ui segment" v-for="(item, index) in contentList[0]">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 40px"
            :src="'data:image/jpg;base64,' + $store.state.me.avatar"
        /></el-aside>
        <el-main>
          <h4 style="margin-top: -20px">{{ $store.state.me.nickname }}</h4>
          <p
            style="margin-top: -10px; color: grey; font-size: 13px"
            v-if="$store.state.me.personalSignature"
          >
            {{ $store.state.me.personalSignature }}
          </p>
          <p style="margin-top: -10px; color: grey; font-size: 13px" v-else>
            暂无签名
          </p>
        </el-main>
      </el-container>
      <el-row :gutter="20" style="margin-top: -10px">
        <el-col :span="20"
          ><h4>
            <a>
              <router-link
                :to="{
                  path: '/helloWorld/BBS/blogContent',
                  query: { blogId: item.id, posterUserId0: item.posterUserId0 },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h4></el-col
        >
      </el-row>
      <el-row :gutter="20" style="margin-top: 5px">
        <el-col :span="8"
          ><img class="ui rounded fluid image" :src="cover[index]"
        /></el-col>
        <el-col :span="16"
          ><h5 style="color: grey">
            {{ item.description }}
          </h5></el-col
        >
      </el-row>
      <!-- <div class="ui divider"></div>
      <div class="ui mini button" style="background-color: white">
        <i class="thumbs up icon"></i>{{ item.likesNum }}
      </div>
      <div
        class="ui mini button"
        style="margin-left: 40px; background-color: white"
      >
        <i class="comment icon"></i>{{ item.commentsNum }}
      </div>
      <div
        class="ui mini button"
        style="margin-left: 40px; background-color: white"
      >
        <i class="share icon"></i>33
      </div> -->
    </div>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "blogs",
  data() {
    return {
      loading: true,
      avatar: sessionStorage.getItem("avatar"),
      nickname: sessionStorage.getItem("nickname"),
      personalSignature: sessionStorage.getItem("personalSignature"),
      cover: [],
      contentList: [],
      count: 0,
    };
  },
  /* mounted() {
    var that = this;
    axios
      .get("/customer/personal")
      .then(function (response) {
        console.log(response.data.data.user);
        that.nowUser = response.data.data.user.nickname;
      })
      .catch(function (error) {
        console.log(error);
      });
  }, */
  created() {
    var that = this;
    axios
      .get("/customer/archivesBlog")
      .then(function (response) {
        that.loading = false;
        console.log(response.data);
        that.count = response.data.data.count;
        for (var year = 2022; year <= 2025; year++) {
          for (var month = 1; month <= 12; month++) {
            if (response.data.data.archiveMap[year][month]) {
              that.contentList.push(response.data.data.archiveMap[year][month]);
            }
          }
        }
        for (var i = 0; i < that.contentList[0].length; i++) {
          var url = require("../../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 10) +
            ").jpg");
          that.cover.push(url);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
    window.addEventListener("setItem", (e) => {
      if (e.key == "avatar") {
        console.log(e.newValue);
        that.avatar = e.newValue;
      }
      /* var decode = jwtDecode(e.newValue);
      console.log(decode); */
    });
  },
};
</script>

<style scoped>
.framework {
  /*
  background-color: bisque; */
  margin-top: 20px;
  height: 200px;
}

.frameworkBody {
  width: 90%;
  margin: auto;
}

.title {
  margin-top: 0px;
}

.ui.label {
  margin-top: 5px;
}

img {
  height: 120px;
}

.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
