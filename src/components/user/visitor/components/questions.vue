<template>
  <div>
    <div class="ui segment" v-if="!contentList.length && loading == false">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <div class="ui segment" v-for="(item, index) in contentList">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui circular image"
            :src="  $store.state.others.avatar"
        /></el-aside>

        <el-main>
          <h4 style="margin-top: -13px">{{ $store.state.others.nickname }}</h4>
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
        <!-- <el-col :span="8"
          ><img class="ui rounded fluid image" :src="cover[index]"
        /></el-col> -->
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
  </div>
</template>
<script>

export default {
  name: "questions",

  data() {
    return {
      loading: true,
      cover: [],
      contentList: [],
    };
  },
  created() {
    var that = this;
    that.$api.userSocial.getOtherFile(that.$route.query.userId)
      .then(function (response) {

        /* that.loading = false; */
        that.contentList = response.data.data.questions;
        that.loading = false;

      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
</style>
