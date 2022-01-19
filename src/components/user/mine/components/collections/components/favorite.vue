<template>
  <div>
    <div
      class="ui segment"
      v-if="loading == false && !questions.length && !blogs.length"
    >
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <el-skeleton :loading="loading" animated :count="3">
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
    </el-skeleton>
    <div class="ui segment" v-for="(item, index) in questions">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 45px"
            :src=" item.avatar"
        /></el-aside>

        <el-main>
          <h4 style="margin-top: -13px">{{ item.nickname }}</h4>
          <p style="margin-top: -13px; color: grey; font-size: 13px">
            {{ item.createTime }}
          </p>
        </el-main>
      </el-container>
      <el-row :gutter="24" style="margin-top: -10px">
        <el-col :span="24"
          ><h4>
            <a href="">
              <router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: {
                    articleId: item.id,
                    posterUserId0: item.posterUserId0,
                  },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h4></el-col
        >
      </el-row>
      <el-row :gutter="24" style="margin-top: 5px">

        <el-col :span="24"
          ><h5 style="color: grey">
            {{ item.description }}
          </h5></el-col
        >
      </el-row>
      <div class="ui divider"></div>
      <el-row :gutter="24">
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="thumbs up icon"></i>{{ item.likesNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="comment icon"></i>{{ item.commentsNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="share icon"></i>0
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="eye icon"></i>{{ item.view }}
          </div></el-col
        >
      </el-row>
    </div>
    <div class="ui segment" v-for="(item, index) in blogs">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 45px"
            :src=" item.avatar"
        /></el-aside>

        <el-main>
          <h4 style="margin-top: -13px">{{ item.nickname }}</h4>
          <p style="margin-top: -13px; color: grey; font-size: 13px">
            {{ item.createTime }}
          </p>
        </el-main>
      </el-container>
      <el-row :gutter="24" style="margin-top: -10px">
        <el-col :span="24"
          ><h4>
            <a href="">
              <router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: {
                    blogId: item.id,
                    posterUserId0: item.posterUserId0,
                  },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h4></el-col
        >
      </el-row>
      <el-row :gutter="24" style="margin-top: 5px">

        <el-col :span="24"
          ><h5 style="color: grey">
            {{ item.description }}
          </h5></el-col
        >
      </el-row>
      <div class="ui divider"></div>
      <el-row :gutter="24">
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="thumbs up icon"></i>{{ item.likesNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="comment icon"></i>{{ item.commentsNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="share icon"></i>0
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="eye icon"></i>{{ item.view }}
          </div></el-col
        >
      </el-row>
    </div>
  </div>
</template>

<script>


export default {
  name: "favorite",
  data() {
    return {
      loading: true,
      avatar: sessionStorage.getItem("imgSrc"),
      cover: [],
      questions: [],
      blogs: [],
      nowUser: sessionStorage.getItem("nickname"),
    };
  },
  created() {
    var that = this;
    that.$api.personalFavorite.showFavoriteContents(that.$route.query.favoriteId)

      .then(function (response) {
        console.log(response.data);
        that.loading = false;
        that.questions = response.data.data.questions.content;
        that.blogs = response.data.data.blogs.content;

      })
      .catch(function (error) {
        console.log(error);
      });
  },

};
</script>

<style scoped>
.framework {
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
.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
