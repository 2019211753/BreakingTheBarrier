<template>
  <div>
    <div
      class="ui segment"
      v-if="loading == false && !questions.length && !blogs.length"
    >
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
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
    <div class="ui segment" v-for="(item, index) in questions">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 45px"
            :src="item.avatar"
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
                    postUserId: item.postUserId,
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
            :src="item.avatar"
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
                    postUserId: item.postUserId,
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
          ><h5>
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
      questions: [],
      blogs: [],
    };
  },
  created() {
    var that = this;
    that.$api.personalFavorite
      .showFavoriteContents(that.$route.query.favoriteId)
      .then(function (response) {
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
