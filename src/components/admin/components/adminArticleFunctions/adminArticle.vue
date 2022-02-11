<template>
  <div>
    <div class="ui icon input">
      <input
        type="text"
        placeholder="请输入关键字搜索..."
        v-model="searchContent"
      /><i class="inverted circular search link icon" @click="search()"></i>
    </div>
    <div style="margin-top: 15px">
      <el-skeleton :loading="loading" animated :count="3">
        <template slot="template">
          <div class="ui segment">
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
          </div>
        </template>
      </el-skeleton>
    </div>
    <div
      class="ui segment"
      style="height: 350px"
      v-if="contentList.length == 0 && loading == false"
    >
      <el-empty :image-size="150" description="暂无结果"></el-empty>
    </div>
    <div class="ui segment" v-for="item in contentList" :key="item.id">
      <el-row :gutter="24">
        <el-col :span="4"
        ><h4>id：{{ item.id }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>发布者：{{ item.nickname }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>
          用户头像：<img
          class="ui mini right floated circular image"
          :src="item.avatar"
          alt=""
        /></h4
        ></el-col>
        <el-col :span="6"
        ><h4>发布者id：{{ item.postUserId }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="24">
          <h4>
            标题：<a href="">
            <router-link
              :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: { articleId: item.id,postUserId:item.postUserId },
                }"
            >{{ item.title }}
            </router-link>
          </a>
          </h4>
        </el-col>
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="24"
        ><h4>描述：{{ item.description }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="24"
        ><h4>标签：{{ item.tagIds }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="4"
        ><h4>浏览：{{ item.view }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>点赞：{{ item.likesNum }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>评论：{{ item.commentsNum }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>点踩：{{ item.disLikesNum }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>收藏：{{ item.collectedNum }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>影响：{{ item.impact }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="12"
        ><h4>发布时间：{{ item.createTime }}</h4></el-col
        >
        <el-col :span="12"
        ><h4>最新评论时间：{{ item.newCommentedTime }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="8"
        ><h4>solvedNum：{{ item.solvedNum }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>solved：{{ item.solved }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>是否隐藏：{{ item.hidden }}</h4></el-col
        >
      </el-row>
      <br/>
      <div class="ui divider"></div>
      <button
        class="ui right floated blue icon button"
        @click="viewArticle(item.id)"
      >
        <i class="eye icon"></i>
      </button>
      <button
        class="ui right floated blue icon button"
        @click="editArticle(item.id)"
      >
        <i class="edit icon"></i>
      </button>
      <button class="ui blue icon button" @click="deleteArticle(item.id)">
        <i class="trash icon"></i>
      </button>
    </div>
    <el-dialog :visible.sync="dialogFormVisible">
      <adminArticleContent :key=$store.state.articleId></adminArticleContent>
    </el-dialog>
  </div>
</template>

<script>
import adminArticleContent from "./components/adminArticleContent.vue";

export default {
  components: {adminArticleContent},
  name: "adminArticle",
  data() {
    return {
      dialogFormVisible: false,
      loading: false,
      searchContent: "",
      contentList: [],
      tag: [],
    };
  },
  methods: {
    search() {
      var that = this;
      if (that.searchContent) {
        that.loading = true;
        var data = {
          query: that.searchContent,
          nickname: "",
          tagIds: "",
        };
        that.$api.adminQuestion
          .searchQuestions(data)
          .then(function (response) {
            that.contentList = response.data.data.pages.content;
            for (var i = 0; i < that.contentList.length; i++) {
              var newTag = {id: that.contentList[i].id, state: "1"};
              that.tag.push(newTag);
            }
            that.loading = false;
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message({
          message: "请输入查找内容",
          type: "warning",
        });
      }
    },
    viewArticle(id) {
      var that = this;
      that.$store.commit("getArticleId", id);
      that.dialogFormVisible = true;
    },
    deleteArticle(id) {
      var that = this;
      that.$api.userQuestion
        .deleteQuestion(id)
        .then(function (response) {
          for (var i = 0; i < that.contentList.length; i++) {
            if (that.contentList[i].id == id) {
              that.contentList.splice(i, 1);
              break;
            }
          }
          that.$message({
            message: "删除成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
