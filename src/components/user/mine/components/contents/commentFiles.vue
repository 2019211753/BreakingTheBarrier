<template>
  <div class="ui segment">
    <div class="ui feed" v-for="item in contentList" :key="item.id">
      <div class="event">
        <div class="label">
          <img
            :src="'data:image/jpg;base64,' + $store.state.me.avatar"
            alt=""
          />
        </div>
        <div class="content">
          <div class="summary">
            评论了 <a>文章 </a>
            <div class="date">{{ item.createTime }}</div>
          </div>
          <div class="extra text">
            {{ item.content }}
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
        ><div class="ui large feed">
          <div class="event">
            <div class="label">
              <el-skeleton-item variant="circle image" />
            </div>
            <div class="content">
              <div class="summary">
                <el-skeleton-item variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template> </el-skeleton
    ><el-empty
      :image-size="100"
      v-if="!contentList.length && loading == false"
      description="暂无评论"
    ></el-empty>
    <!-- <div class="event">
      <div class="label">
        <img :src="'data:image/jpg;base64,' + item.avatar" alt="" />
      </div>
      <div class="content">
        <div class="summary">
          <a class="user"> {{ item.nickname }} </a> 评论了你：
          <a href=""
            ><router-link
              v-if="item.blogId"
              :to="{
                path: '/helloWorld/BBS/blogContent',
                query: { blogId: item.blogId0 },
              }"
            >
              {{ item.content }}</router-link
            ><router-link
              v-else
              :to="{
                path: '/helloWorld/BBS/articleContent',
                query: { articleId: item.questionId0 },
              }"
            >
              {{ item.content }}</router-link
            ></a
          >
          <div class="date"></div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "commentFiles",
  data() {
    return {
      loading: true,
      contentList: [],
    };
  },
  created() {
    var that = this;
    axios
      .get("/customer/archiveComment")
      .then(function (response) {
        console.log(response.data);
        that.contentList = response.data.data.postComments;
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
