<template>
  <div class="ui basic segment">
    <!-- <div class="ui segment"></div> -->
    <div class="ui segment" v-if="loading == false && !contentList.length">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <div class="ui segment" v-for="(item, index) in contentList">
      <div class="ui middle aligned grid">
        <div class="ten wide column">
          <h3 class="ui header">
            <a href="">
              <router-link
                :to="{
                  path: '/helloWorld/BBS/blogContent',
                  query: { blogId: item.id, posterUserId0: item.posterUserId0 },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h3>
          <p class="m-text">{{ item.description }}</p>
          <div class="ui grid">
            <div class="eleven wide column">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <img :src="item.avatar" alt="" class="ui avatar image" />
                  <div class="content">
                    <a class="header">{{ item.nickname }}</a>
                  </div>
                </div>
              </div>
            </div>
            <div class="sixteen wide column" style="margin-top: -20px">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <i class="calendar icon"></i>
                  <span style="margin-left: 2px">{{ item.createTime }}</span>
                </div>
                <div class="item">
                  <i class="thumbs up icon"></i>
                  <span style="margin-left: 2px">{{ item.likesNum }}</span>
                </div>
                <div class="item">
                  <i class="comment icon"></i>
                  <span style="margin-left: 2px">{{ item.commentsNum }}</span>
                </div>
                <div class="item">
                  <i class="eye icon"></i>
                  <span style="margin-left: 2px">{{ item.view }}</span>
                </div>
              </div>
            </div>
            <div class="sixteen wide column" style="margin-top: -20px">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <a class="ui teal basic label" v-for="tags in item.tags">{{
                    tags.name
                  }}</a>
                  <a class="ui teal basic label" v-if="item.origin == true"
                    >原创</a
                  >
                  <a class="ui blue basic label" v-else>转载</a>
                </div>
              </div>
            </div>
          </div>
          <div class="ui grid">
            <!-- <div class="right aligned five wide column">
              <a
                href="/types/2"
                class="ui teal basic label m-padded-tb-tiny m-text-thin"
                >JavaSE</a
              >
            </div> -->
          </div>
        </div>
        <div class="six wide column">
          <a href="/blog/120" target="_blank">
            <img
              src="https://picsum.photos/800/450/?random=108"
              alt=""
              class="ui rounded image"
            />
          </a>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="7">
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
            <el-col :span="5"
              ><el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
          </el-row>
        </div>
      </template>
    </el-skeleton>

    <el-pagination
      class="el-pagination"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-count="pageSize"
      :hide-on-single-page="true"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "blogContents",
  data() {
    return { loading: true, contentList: [], pageSize: 0, cover: [] };
  },
  created() {
    var that = this;
    axios
      .get("/listBlogs/?page=0")
      .then(function (response) {
        that.loading = false;
        console.log(response.data);
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
        /* for (var i = 0; i < that.contentList.length; i++) {
          var url = require("../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 51) +
            ").jpg");
          that.cover.push(url);
        } */
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    handleCurrentChange(val) {
      var that = this;
      var nowPage = val - 1;
      axios
        .get("/listBlogs/?page=" + nowPage)
        .then(function (response) {
          console.log(response.data);
          that.contentList = response.data.data.pages.content;
          that.pageSize = response.data.data.pages.totalPages;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.framework {
  /* background-color: bisque; */
  margin-top: 20px;
  height: 260px;
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
.el-pagination {
  margin-top: 20px;
  margin-left: 25%;
}
.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
