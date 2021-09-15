<template>
  <div>
    <div class="ui icon input">
      <input
        type="text"
        placeholder="请输入关键字搜索..."
        v-model="searchContent"
      /><i class="inverted circular search link icon" @click="search()"></i>
    </div>
    <el-empty
      :image-size="200"
      v-if="!contentList.length"
      description="找不到任何结果"
    ></el-empty>
    <el-container v-for="(item, index) in contentList" key="item.id">
      <el-aside width="250px"
        ><div class="ui large feed">
          <br />
          <div class="event">
            <div class="label">
              <img src="../../../../assets/avatar.jpg" />
            </div>
            <div class="content">
              <div class="summary">
                <a>{{ item.title }}</a>
              </div>
              <br />
              <div class="ui icon buttons">
                <button class="ui button" @click="viewData()">
                  <i class="database icon"></i>
                </button>
                <button class="ui button" @click="viewArticle(item.id, index)">
                  <i class="eye icon"></i></button
                ><button class="ui button">
                  <i class="edit icon"></i>
                </button>
                <button class="ui button" @click="deleteArticle(item.id)">
                  <i class="trash icon"></i>
                </button>
              </div>
            </div>
          </div></div
      ></el-aside>
      <el-main>
        <div class="ui tiny statistics" v-if="tag[index].state == '1'">
          <div class="statistic">
            <div class="value">{{ item.id }}</div>
            <div class="label">id</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.nickname }}</div>
            <div class="label">作者</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.posterUserId0 }}</div>
            <div class="label">作者id</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.hidden }}</div>
            <div class="label">隐藏</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.likesNum }}</div>
            <div class="label">赞</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.disLikesNum }}</div>
            <div class="label">踩</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.view }}</div>
            <div class="label">浏览</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.collectedNum }}</div>
            <div class="label">收藏</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.commentsNum }}</div>
            <div class="label">评论</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.impact }}</div>
            <div class="label">影响</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.createTime.split(" ")[0] }}</div>
            <div class="label">创建时间</div>
          </div>
        </div>
        <div>
          <div v-if="tag[index].state == '2'">
            <adminBlogContent></adminBlogContent>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import adminBlogContent from "./components/adminBlogContent";
export default {
  components: { adminBlogContent },
  name: "adminBlog",
  data() {
    return { searchContent: "", contentList: [], tag: [] };
  },
  methods: {
    search() {
      var that = this;
      /* this.$router.push("/helloWorld/admin/adminArticle/adminArticleData"); */
      if (that.searchContent) {
        axios
          .post("/admin/searchBlogs", {
            query: that.searchContent,
          })
          .then(function (response) {
            that.contentList = response.data.data.pages.content;
            for (var i = 0; i < that.contentList.length; i++) {
              var newTag = { id: that.contentList[i].id, state: "1" };
              that.tag.push(newTag);
            }
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
    viewData() {
      var that = this;
      for (var i = 0; i < that.tag.length; i++) {
        that.tag[i].state = "1";
      }
    },
    viewArticle(id, index) {
      var that = this;
      sessionStorage["blogId"] = id;
      for (var i = 0; i < that.tag.length; i++) {
        that.tag[i].state = "1";
      }
      that.tag[index].state = "2";
    },
    deleteArticle(id) {
      var that = this;
      axios
        .get("/customer/blog/" + id + "/delete")
        .then(function (response) {
          console.log(response.data);
          for (var i = 0; i < that.contentList.length; i++) {
            if (that.contentList[i].id == id) {
              that.contentList.pop(that.contentList[i]);
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
