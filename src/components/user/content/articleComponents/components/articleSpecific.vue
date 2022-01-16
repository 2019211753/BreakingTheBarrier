<template>
  <div>
    <el-skeleton :loading="questionLoading" animated style="margin-top: 14px">
      <template slot="template"
        ><div>
          <div class="ui segment">
            <div class="ui large feed">
              <div class="event">
                <div class="label">
                  <el-skeleton-item variant="circle image" />
                </div>
                <div class="content">
                  <div class="summary"></div>
                </div>
              </div>
            </div>
            <h3 class="title"><el-skeleton-item variant="text" /></h3>
            <div class="ui divider"></div>
            <el-skeleton-item
              variant="image"
              style="width: 80%; height: 400px; margin: auto"
            /><br />
            <el-skeleton-item variant="text" /><el-skeleton-item
              variant="text"
            />
            <el-skeleton-item variant="text" />
            <el-row>
              <el-col :span="20"
                ><div class="grid-content bg-purple-dark"></div
              ></el-col>
            </el-row>
          </div>
        </div>
      </template>
    </el-skeleton>
    <div class="ui segment" v-if="questionLoading == false">
      <div class="ui large feed">
        <div class="event">
          <div class="label">
            <img :src=" template.avatar" alt="" />
          </div>
          <div class="content">
            <div class="summary">
              <a class="user"
                ><router-link
                  v-if="$store.state.me.id == posterUserId0"
                  to="/helloWorld/mine/contents/questionFiles"
                  >{{ template.nickname }} </router-link
                ><router-link
                  v-else
                  :to="{
                    path: '/helloWorld/visitor/questions',
                    query: { userId0: posterUserId0 },
                  }"
                  >{{ template.nickname }}
                </router-link>
              </a>
              <div
                v-if="template.solved == true"
                class="ui green top right attached label"
              >
                已解决
              </div>
              <div
                v-if="template.solved == false"
                class="ui red top right attached label"
              >
                未解决
              </div>
              <div class="date">{{ template.createTime }}</div>
            </div>
          </div>
        </div>
      </div>
      <h3 class="title">{{ template.title }}</h3>
      <div class="ui divider"></div>
      <div v-html="articleContent">{{ articleContent }}</div>
      <br /><el-row>
        <el-col :span="7"><div style="height: 1px"></div></el-col>
        <el-col :span="12">
          <div
            @click="likeArticle(template.id)"
            :class="approved == true ? articleLikeIsActive : button"
          >
            <i class="heart icon"></i>
            {{ articleLikeNumber }}
          </div>
          <div
            class="ui button"
            :class="collected == true ? articleCollectIsActive : button"
            @click="collected == false ? openCollections() : collectArticle()"
          >
            <i class="star icon"></i>
            {{ articleCollectNumber }}
          </div>
          <div class="ui icon blue button" @click="replyArticle()">
            <i class="comment icon"></i>
          </div>
        </el-col>
        <el-col :span="5"><div class="grid-content bg-purple"></div></el-col>
      </el-row>

      <div class="ui divider"></div>
      <div class="ui mini labeled button" style="margin-left: 3%"></div>
      <div
        class="ui mini labeled button"
        style="margin-left: 54%"
        @click="dislikeArticle(template.id)"
      >
        <i class="thumbs down icon"></i>踩
      </div>
      <div class="ui mini labeled button" style="margin-left: 3%">
        <i class="share icon"></i>转发
      </div>
      <div class="ui mini labeled button" style="margin-left: 3%">
        <i class="red exclamation triangle icon"></i>举报
      </div>
      <div
        class="ui mini labeled button"
        @click="deleteArticle(template.id)"
        v-if="posterUserId0 == $store.state.me.id"
        style="margin-left: 3%"
      >
        <i class="trash icon"></i>删除
      </div>
    </div>
    <br />
    <div>
      <div class="ui segment">
        <div>
          <div class="ui comments">
            <h3>全部评论</h3>
            <el-empty
              :image-size="100"
              v-if="commentList.length == 0 && commentLoading == false"
              description="暂无评论"
            ></el-empty>
            <el-skeleton :loading="commentLoading" animated :count="5">
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
              </template>
            </el-skeleton>
            <div
              :class="item.parentCommentId0 == -1 ? parent : child"
              v-for="item in commentList"
            >
              <a class="avatar">
                <img :src=" item.avatar" alt="" />
              </a>
              <div class="content">
                <a class="author"
                  ><router-link
                    :to="{
                      path: '/helloWorld/visitor',
                      query: { userId0: item.postUserId0 },
                    }"
                    >{{ item.nickname }}
                  </router-link></a
                >
                <div class="metadata">
                  <span class="date">{{ item.createTime }}</span>
                </div>
                <div class="text" v-html="item.content"></div>
                <div class="actions">
                  <a
                    class="reply"
                    @click="likeComment(item.id)"
                    v-model="likeNumber"
                    >赞( {{ item.likesNum }})</a
                  ><a class="reply" @click="dislikeComment(item.id)"
                    >踩({{ item.disLikesNum }})</a
                  ><a class="reply" @click="replyComment(item.id)">回复</a
                  ><a
                    class="reply"
                    @click="deleteComment(item.id)"
                    v-if="item.postUserId0 == $store.state.me.id"
                    >删除</a
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="ui collect modal" style="width: 400px">
        <el-skeleton :loading="collectLoading" animated :count="1">
          <template slot="template"
            ><el-skeleton-item
              variant="text"
              style="height: 50px; margin-top: 15px"
            />
          </template>
        </el-skeleton>
        <div class="ui basic segment">
          <el-container v-for="(item, index) in favoriteList" :key="index">
            <el-aside width="70px"
              ><i class="huge yellow folder icon"></i
            ></el-aside>
            <el-main
              ><el-row :gutter="24">
                <el-col :span="21">
                  <div
                    class="ui button"
                    style="margin-top: -30px; background-color: white"
                    @click="getFavoriteId(item.id)"
                  >
                    <h4>{{ item.title }}</h4>
                  </div>
                  <a
                    v-if="item.open == false"
                    class="ui small blue label"
                    style="margin-left: 10px"
                    >私密</a
                  >
                </el-col>
                <el-col :span="3"
                  ><i
                    :class="item.id == favoriteId ? selected : unselected"
                    style="margin-top: 2px"
                  ></i>
                </el-col>
              </el-row>
            </el-main>
          </el-container>
        </div>
        <div class="actions">
          <div class="ui teal ok inverted button" @click="collectArticle()">
            <i class="checkmark icon"></i>
            确定
          </div>
        </div>
      </div>
      <div class="ui edit modal" style="width: 400px">
        <div id="websiteEditorElem"></div>
        <div class="actions">
          <div class="ui teal ok inverted button" @click="sure()">
            <i class="checkmark icon"></i>
            确定
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import E from "wangeditor";

export default {
  name: "articleSpecific",
  data() {
    return {
      questionLoading: true,
      commentLoading: true,
      collectLoading: true,
      template: "",
      approved: "",
      disapproved: "",
      collected: "",
      articleContent: "",
      articleLikeNumber: "",
      articleDislikeNumber: "",
      articleCollectNumber: "",
      button: "ui button",
      articleLikeIsActive: "ui red button",
      articleDislikeIsActive: "ui blue button",
      articleCollectIsActive: "ui yellow button",
      favoriteList: [],
      favoriteId: sessionStorage["favoriteId"],
      /* ---------------------------------- */
      likeNumber: "",
      dislikeNumber: "",
      commentList: "",
      parent: "comment",
      child: "child comment",
      parentId: "-1",
      /* ----------------------------- */
      content: "",
      selected: "ui green check circle icon",
      unselected: "ui check circle icon",

      posterUserId0: this.$route.query.posterUserId0,
    };
  },
  created() {
    var that = this;
    axios
      .get("/question/" + this.$route.query.articleId)
      .then(function (response) {
        that.questionLoading = false;
        that.template = response.data.data.template;
        that.approved = that.template.approved;
        that.disapproved = that.template.disapproved;
        that.collected = that.template.collected;
        that.articleContent = that.template.details;
        that.articleLikeNumber = that.template.likesNum;
        that.articleCollectNumber = that.template.collectedNum;
        that.articleDislikeNumber = that.template.disLikesNum;
        that.$store.commit("getArticleId", that.template.id);

        /* sessionStorage["posterUserId0"] = that.template.posterUserId0; */

        console.log(that.template);
      })
      .catch(function (error) {
        console.log(error);
      });
    /* ------------------------- */
    axios
      .get("/question/" + this.$route.query.articleId + "/comments")
      .then(function (response) {
        that.commentLoading = false;
        console.log(that.flatten(response.data.data.comments2));
        that.commentList = that.flatten(response.data.data.comments2);
      })
      .catch(function (error) {
        console.log(error);
      });
    var that = this;
  },
  mounted() {
    // wangeditor
    this.phoneEditor = new E("#websiteEditorElem");
    this.phoneEditor.config.zIndex = 500;
    this.phoneEditor.config.height = 200;
    // this.phoneEditor.onchange = function () {
    //   this.formData.phone = this.$txt.html()
    // }
    // 上传图片到服务器，base64形式
    this.phoneEditor.config.uploadImgShowBase64 = true;
    // 隐藏网络图片
    //this.phoneEditor.customConfig.showLinkImg = false;
    // 创建一个富文本编辑器
    this.phoneEditor.create();
    // 富文本内容
    this.phoneEditor.txt.html();
  },
  methods: {
    likeArticle(id) {
      var that = this;
      axios
        .get("/question/" + id + "/approve")
        .then(function (response) {
          console.log(response.data);
          that.approved = response.data.data.approved;
          that.articleLikeNumber = response.data.data.likesNum;
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    dislikeArticle(id) {
      var that = this;
      axios
        .get("/question/" + id + "/disapprove")
        .then(function (response) {
          console.log(response.data);
          that.disapproved = response.data.data.disapproved;
          that.articleDislikeNumber = response.data.data.disLikesNum;
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    openCollections() {
      var that = this;
      /* if (collected == false) { */
      $(".ui.collect.modal").modal("show");
      axios
        .get("/customer/favorites")
        .then(function (response) {
          that.collectLoading = false;
          that.favoriteList = response.data.data.favorites;
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
      /*  } */
    },
    getFavoriteId(id) {
      var that = this;
      that.favoriteId = id;
      sessionStorage["favoriteId"] = id;
    },
    collectArticle() {
      var that = this;
      axios
        .get(
          "/customer/favorite/" +
            sessionStorage.getItem("favoriteId") +
            "/modify/question/" +
            this.$route.query.articleId
        )
        .then(function (response) {
          console.log(response.data);
          that.collected = response.data.data.collected;
          that.articleCollectNumber = response.data.data.collectedNum;
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    deleteArticle(id) {
      var that = this;
      axios
        .get("/customer/question/" + id + "/delete")
        .then(function (response) {
          console.log(response.data);
          that.$message({
            message: "删除成功",
            type: "success",
          });
          that.$router.push("/helloWorld/BBS/questions");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /* ---------------------------------- */
    flatten(data) {
      return data.reduce(
        (
          arr,
          {
            adminComment,
            answer,
            blogId0,
            commentsNum,
            id,
            approved,
            avatar,
            content,
            createTime,
            disLikesNum,
            disapproved,
            hidden,
            likesNum,
            looked,
            nickname,
            parentCommentId0,
            postUserId0,
            questionId0,
            receiveComments = [],
          }
        ) =>
          arr.concat(
            [
              {
                adminComment,
                answer,
                blogId0,
                commentsNum,
                id,
                approved,
                avatar,
                content,
                createTime,
                disLikesNum,
                disapproved,
                hidden,
                likesNum,
                looked,
                nickname,
                parentCommentId0,
                postUserId0,
                questionId0,
              },
            ],
            this.flatten(receiveComments, id)
          ),
        []
      );
    },
    likeComment(id) {
      var that = this;
      axios
        .get("/comment/" + id + "/approve")
        .then(function (response) {
          console.log(response.data);
          that.$message({
            message: "点赞成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    dislikeComment(id) {
      var that = this;
      axios
        .get("/comment/" + id + "/disapprove")
        .then(function (response) {
          console.log(response.data);
          that.$message({
            message: "点踩成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    replyArticle() {
      $(".ui.edit.modal").modal("show");
    },
    replyComment(id) {
      var that = this;
      that.parentId = id;
      $(".ui.edit.modal").modal("show");
    },
    /*   replyComment(id) {
      var that = this;
      if (that.phoneEditor.txt.html()) {
        axios
          .post(
            "/question/" +
              sessionStorage.getItem("articleId") +
              "/comment/post",
            {
              content: that.phoneEditor.txt.html(),
              answer: true,
              parentCommentId0: id,
            }
          )
          .then(function (response) {
            that.commentList = that.flatten(response.data.data.comments);
            that.$message({
              message: "回复成功",
              type: "success",
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        this.$message({
          message: "请填写评论内容",
          type: "warning",
        });
      }
    }, */
    deleteComment(id) {
      var that = this;
      axios
        .get(
          "/question/" +
            sessionStorage.getItem("articleId") +
            "/comment/" +
            id +
            "/delete"
        )
        .then(function (response) {
          console.log(response.data);
          that.commentList = that.flatten(response.data.data.comments);
          that.$message({
            message: "删除成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /* ------------------------------------------ */
    sure() {
      var that = this;
      if (that.phoneEditor.txt.html()) {
        axios
          .post("/question/" + that.$store.state.articleId + "/comment/post", {
            content: that.phoneEditor.txt.html(),
            answer: true,
            parentCommentId0: this.parentId,
          })
          .then(function (response) {
            console.log(response.data);
            that.commentList = that.flatten(response.data.data.comments);
            that.parentId = "-1";
            that.$message({
              message: "评论成功",
              type: "success",
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        this.$message({
          message: "请填写评论内容",
          type: "warning",
        });
      }
    },
  },
};
</script>

<style scoped>
h3:nth-child(2) {
  text-align: center;
}

.buttons {
  width: 400px;
  margin: 0 20%;
}

/* --------------------------- */
.child.comment {
  margin-left: 50px;
}
</style>
