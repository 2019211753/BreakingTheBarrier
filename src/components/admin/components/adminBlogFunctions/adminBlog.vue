<template>
  <div>
    <div class="ui icon input">
      <input
        type="text"
        placeholder="请输入关键字搜索..."
        v-model="searchContent"
      /><i class="inverted circular search link icon" @click="search()"></i>
    </div>
    <!-- <div class="ui segment" v-if="loading == true" style="height: 590px"></div> -->
    <div
      class="ui segment"
      style="height: 350px"
      v-if="contentList.length == 0 && loading == false"
    >
      <el-empty :image-size="150" description="暂无结果"></el-empty>
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
    <div class="ui segment" v-for="item in contentList" :key="item.id">
      <el-row :gutter="24">
        <el-col :span="4"
        ><h4>id：{{ item.id }}</h4></el-col
        >
        <el-col :span="9"
        ><h4>发布者：{{ item.nickname }}</h4></el-col
        >
        <el-col :span="5"
        ><h4>
          头像：<img
          class="ui mini right floated circular image"
          :src=item.avatar
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
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.id },
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
        ><h4>原创：{{ item.origin }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>接受赞赏：{{ item.appreciation }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>是否隐藏：{{ item.hidden }}</h4></el-col
        >
      </el-row
      >
      <br/>
      <el-row :gutter="24">
        <el-col :span="8"
        ><h4>允许评论：{{ item.commentAllowed }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>公开：{{ item.open }}</h4></el-col
        >
        <el-col :span="8"
        ><h4>更新时间：{{ item.updateTime }}</h4></el-col
        >
      </el-row
      >
      <br/>
      <el-row :gutter="24">
        <el-col :span="24"
        ><h4>转载说明：{{ item.transferStatement }}</h4></el-col
        >
      </el-row>
      <div class="ui divider"></div>
      <button
        class="ui right floated blue icon button"
        @click="viewBlog(item.id)"
      >
        <i class="eye icon"></i>
      </button>
      <button
        class="ui right floated blue icon button"
        @click="editBlog(item.id)"
      >
        <i class="edit icon"></i>
      </button>
      <button class="ui blue icon button" @click="deleteBlog(item.id)">
        <i class="trash icon"></i>
      </button>
    </div>
    <div class="ui adminBlogContent modal" style="width: 600px">
<!--      <adminBlogContent></adminBlogContent>-->
    </div>
  </div>
</template>

<script>
/*import adminBlogContent from "./components/adminBlogContent";*/
export default {
 /* components: {adminBlogContent},*/
  name: "adminBlog",
  data() {
    return {loading: false, searchContent: "", contentList: [], tag: []};
  },
  /*created() {
    var that = this;
    axios
      .get("/listBlogs/?page=0")
      .then(function (response) {
        that.loading = false;
        console.log(response.data);
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
      })
      .catch(function (error) {
        console.log(error);
      });
  },*/
  methods: {
    search() {
      var that = this;
      /* this.$router.push("/BreakingTheBarrier/admin/adminArticle/adminArticleData"); */
      if (that.searchContent) {
        that.loading = true;
        var data = {
          query: that.searchContent,
          nickname: "",
          tagIds: ""
        }
       that.$api.adminBlog.serchBlogs(data)
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
    viewBlog(id) {
      var that = this;
      sessionStorage.setItem("blogId", id);
      $(".adminBlogContent.modal").modal("show");
    },
    deleteBlog(id) {
      var that = this;
      that.$api.userBlog.deleteBlog(id)
        .then(function (response) {
          console.log(response.data);
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
