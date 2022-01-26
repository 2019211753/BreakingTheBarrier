<template>
  <div>
    <el-skeleton :loading="questionLoading" animated style="margin-top: 14px">
      <template slot="template">
        <div>
          <div class="ui segment">
            <div class="ui large feed">
              <div class="event">
                <div class="label">
                  <el-skeleton-item variant="circle image"/>
                </div>
                <div class="content">
                  <div class="summary"></div>
                </div>
              </div>
            </div>
            <h3 class="title">
              <el-skeleton-item variant="text"/>
            </h3>
            <div class="ui divider"></div>
            <el-skeleton-item
              variant="image"
              style="width: 80%; height: 400px; margin: auto"
            />
            <br/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text"/>
            <el-row>
              <el-col :span="20">
                <div class="grid-content bg-purple-dark"></div>
              </el-col>
            </el-row>
          </div>
        </div>
      </template>
    </el-skeleton>
    <div class="ui segment" v-if="questionLoading == false">
      <div class="ui large feed">
        <div class="event">
          <div class="label">
            <img :src="template.avatar" alt=""/>
          </div>
          <div class="content">
            <div class="summary">
              <a class="user">
                <router-link
                  v-if="$store.state.me.id == postUserId"
                  to="/BreakingTheBarrier/mine/contents/questionFiles"
                >{{ template.nickname }}
                </router-link>
                <router-link
                  v-else
                  :to="{
                    path: '/BreakingTheBarrier/visitor/questions',
                    query: { userId: postUserId },
                  }"
                >{{ template.nickname }}
                </router-link>
              </a>
              <div
                v-if="template.solved == true"
                class="ui teal top right attached label"
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
      <br/>
      <el-row>
        <el-col :span="7">
          <div style="height: 1px"></div>
        </el-col>
        <el-col :span="12">
          <div
            @click="likeArticle(template.id)"
            :class="articleApproved == true ? articleLikeIsActive : button"
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
        <el-col :span="5">
          <div class="grid-content bg-purple"></div>
        </el-col>
      </el-row>
      <div class="ui divider"></div>
      <div class="ui mini labeled button" style="margin-left: 3%"></div>
      <div
        class="ui mini labeled button"
        style="margin-left: 54%"
        @click="dislikeArticle(template.id)"
      >
        <i
          :class="
            articleDisapproved == true ? articleDislikeIsActive : articleDislike
          "
        ></i
        >踩
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
        v-if="postUserId == $store.state.me.id"
        style="margin-left: 3%"
      >
        <i class="trash icon"></i>删除
      </div>
    </div>
    <br/>
    <div>
      <div class="ui segment">
        <div>
          <div class="ui comments">
            <h3>全部评论</h3>
            <el-empty
              :image-size="100"
              v-if="
                commentList.length == 0 &&
                bestComments.length == 0 &&
                selectedComments.length == 0 &&
                commentLoading == false
              "
              description="暂无评论"
            ></el-empty>
            <el-skeleton :loading="commentLoading" animated :count="5">
              <template slot="template">
                <div class="ui large feed">
                  <div class="event">
                    <div class="label">
                      <el-skeleton-item variant="circle image"/>
                    </div>
                    <div class="content">
                      <div class="summary">
                        <el-skeleton-item variant="text"/>
                      </div>
                    </div>
                  </div>
                </div>
              </template>
            </el-skeleton>
            <div class="comment" v-for="item in selectedComments">
              <a class="avatar">
                <img :src="item.avatar" alt=""/>
              </a>
              <div class="content">
                <a class="author">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/visitor',
                      query: { userId: item.postUserId },
                    }"
                  >{{ item.nickname }}
                  </router-link>
                </a
                ><a
                class="ui mini basic blue label"
                style="margin-left: 5px"
                v-if="item.postUserId == postUserId"
              >发布者</a
              >
                <div class="metadata">
                  <span class="date">{{ item.createTime }}</span
                  ><a class="ui mini teal label">精选评论</a>
                </div>
                <div class="text" v-html="item.content"></div>
              </div>
            </div>
            <div class="ui divider" v-if="!selectedComments.length == 0"></div>
            <div class="comment" v-for="item in bestComments">
              <a class="avatar">
                <img :src="item.avatar" alt=""/>
              </a>
              <div class="content">
                <a class="author">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/visitor',
                      query: { userId: item.postUserId },
                    }"
                  >{{ item.nickname }}
                  </router-link>
                </a
                ><a
                class="ui mini basic blue label"
                style="margin-left: 5px"
                v-if="item.postUserId == postUserId"
              >发布者</a
              >
                <div class="metadata">
                  <span class="date">{{ item.createTime }}</span
                  ><a class="ui mini red label">优质评论</a>
                </div>
                <div class="text" v-html="item.content"></div>
              </div>
            </div>
            <div class="ui divider" v-if="!bestComments.length == 0"></div>
            <div
              :class="item.parentCommentId == '0' ? parent : child"
              v-for="item in commentList"
            >
              <a class="avatar">
                <img :src="item.avatar" alt=""/>
              </a>
              <div class="content">
                <a class="author">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/visitor',
                      query: { userId: item.postUserId },
                    }"
                  >{{ item.nickname }}
                  </router-link>
                </a
                ><a
                class="ui mini basic blue label"
                style="margin-left: 5px"
                v-if="item.postUserId == postUserId"
              >发布者</a
              >
                <div class="metadata">
                  <span class="date">{{ item.createTime }}</span>
                </div>
                <div class="text" v-html="item.content"></div>
                <div class="actions">
                  <a
                    :style="item.approved == true ? 'color:RGB(219,40,40)' : ''"
                    class="reply"
                    @click="likeComment(item.id, true)"
                    v-model="likeNumber"
                  >赞( {{ item.likesNum }})</a
                  ><a
                  :style="
                      item.disapproved == true ? 'color:RGB(65,131,196)' : ''
                    "
                  class="reply"
                  @click="dislikeComment(item.id, true)"
                >踩({{ item.disLikesNum }})</a
                ><a class="reply" @click="replyComment(item.id)">回复</a
                ><a
                  :style="item.selected == true ? 'color:RGB(0,181,173)' : ''"
                  class="reply"
                  v-if="postUserId == $store.state.me.id"
                  @click="setSelectedComment(item.id, true)"
                >设为精选评论</a
                ><a
                  class="reply"
                  @click="deleteComment(item.id, true)"
                  v-if="item.postUserId == $store.state.me.id"
                >删除</a
                ><a
                  class="reply"
                  v-if="item.commentsNum > 3"
                  @click="getMoreComments(item.id)"
                >加载全部评论</a
                >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="ui collect modal" style="width: 400px">
        <el-empty
          :image-size="100"
          v-if="favoriteList.length == 0 && collectLoading == false"
          description="暂无收藏夹"
        ></el-empty>
        <el-skeleton :loading="collectLoading" animated :count="1">
          <template slot="template">
            <el-skeleton-item
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
            <el-main>
              <el-row :gutter="24">
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
        <div
          class="actions"
          v-if="!favoriteList.length == 0 && collectLoading == false"
        >
          <div class="ui teal button" @click="collectArticle()">
            <i class="checkmark icon"></i>
            确定
          </div>
        </div>
      </div>
      <div class="ui edit modal" style="width: 400px">
        <div id="websiteEditorElem"></div>
        <div class="actions">
          <div class="ui teal button" @click="sure()">
            <i class="checkmark icon"></i>
            确定
          </div>
        </div>
      </div>
      <div class="ui moreComments modal" style="width: 700px">
        <br/>
        <div class="ui basic segment" style="width: 600px; margin: auto">
          <div class="ui comments">
            <div class="comment" v-for="item in moreComments">
              <a class="avatar">
                <img :src="item.avatar" alt=""/>
              </a>
              <div class="content">
                <a class="author">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/visitor',
                      query: { userId: item.postUserId },
                    }"
                  >{{ item.nickname }}
                  </router-link>
                </a
                ><a
                class="ui mini basic blue label"
                style="margin-left: 5px"
                v-if="item.postUserId == postUserId"
              >发布者</a
              >
                <div class="metadata">
                  <span class="date">{{ item.createTime }}</span>
                </div>
                <div class="text" v-html="item.content"></div>
                <div class="actions">
                  <a
                    :style="item.approved == true ? 'color:RGB(219,40,40)' : ''"
                    class="reply"
                    @click="likeComment(item.id, false)"
                    v-model="likeNumber"
                  >赞( {{ item.likesNum }})</a
                  ><a
                  :style="
                      item.disapproved == true ? 'color:RGB(65,131,196)' : ''
                    "
                  class="reply"
                  @click="dislikeComment(item.id, false)"
                >踩({{ item.disLikesNum }})</a
                ><a class="reply" @click="replyComment(item.id)">回复</a
                ><a
                  :style="item.selected == true ? 'color:RGB(0,181,173)' : ''"
                  class="reply"
                  v-if="postUserId == $store.state.me.id"
                  @click="setSelectedComment(item.id, false)"
                >设为精选评论</a
                ><a
                  class="reply"
                  @click="deleteComment(item.id, false)"
                  v-if="item.postUserId == $store.state.me.id"
                >删除</a
                ><a
                  class="reply"
                  v-if="item.commentsNum > 3"
                  @click="getMoreComments(item.id)"
                >加载全部评论</a
                >
                </div>
              </div>
            </div>
          </div>
        </div>
        <br/>
      </div>
    </div>
  </div>
</template>

<script>
import E from "wangeditor";
import {
  likeComment,
  likeArticle,
  sure,
  dislikeArticle,
  dislikeComment,
  deleteComment,
  deleteArticle,
  getCommentLikesAndDislikes,
  getArticleLikesAndDislikes,
  getMoreComments,
  getAllComments,
  getFavoriteId
} from "../../content";

export default {
  name: "articleSpecific",
  data() {
    return {
      questionLoading: true,
      commentLoading: true,
      collectLoading: true,
      template: "",
      articleApproved: "",
      articleDisapproved: "",
      collected: "",
      articleContent: "",
      articleLikeNumber: "",
      articleCollectNumber: "",
      button: "ui button",
      articleLikeIsActive: "ui red button",
      articleDislike: "thumbs down icon",
      articleDislikeIsActive: "thumbs down blue icon",
      articleCollectIsActive: "ui yellow button",
      favoriteList: [],
      favoriteId: sessionStorage["favoriteId"],
      likeNumber: "",
      dislikeNumber: "",
      commentList: "",
      bestComments: "",
      selectedComments: "",
      moreComments: "",
      reply: "reply",
      parent: "comment",
      child: "child comment",
      parentId: "-1",
      content: "",
      selected: "ui green check circle icon",
      unselected: "ui check circle icon",
      postUserId: this.$route.query.postUserId,
      nowParentId: "",
    };
  },
  created() {
    var that = this;
    var p1 = new Promise((resolve, reject) => {
      that.$api.userArticle
        .showQuestion(that.$route.query.articleId)
        .then(function (response) {
          that.questionLoading = false;
          that.template = response.data.data.template;
          that.articleApproved = that.template.approved;
          that.articleDisapproved = that.template.disapproved;
          that.collected = that.template.collected;
          that.articleContent = that.template.details;
          that.articleLikeNumber = that.template.likesNum;
          that.articleCollectNumber = that.template.collectedNum;
          that.articleDislikeNumber = that.template.disLikesNum;
          that.$store.commit("getArticleId", that.template.id);
        })
        .catch(function (error) {
          console.log(error);
        });
    });
    var p2 = new Promise((resolve, reject) => {
      that.getAllComments(false, "", "");
    });
    Promise.all([p1, p2]).then((res) => {
    });
  },
  mounted() {
    this.phoneEditor = new E("#websiteEditorElem");
    this.phoneEditor.config.zIndex = 500;
    this.phoneEditor.config.height = 200;
    this.phoneEditor.config.uploadImgShowBase64 = true;
    this.phoneEditor.create();
    this.phoneEditor.txt.html();
  },
  methods: {
    likeArticle(id) {
      var that = this;
      that.$api.userLike
        .likeQuestion(id)
        .then(function (response) {
          that.getArticleLikesAndDislikes(
            response.data.data.approved,
            response.data.data.disapproved,
            response.data.data.likesNum
          );
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
      that.$api.userLike
        .dislikeQuestion(id)
        .then(function (response) {
          that.getArticleLikesAndDislikes(
            response.data.data.approved,
            response.data.data.disapproved,
            response.data.data.likesNum
          );
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getArticleLikesAndDislikes(approved, disapproved, likesNum) {
      var that = this;
      that.articleApproved = approved;
      that.articleLikeNumber = likesNum;
      that.articleDisapproved = disapproved;
    },
    replyArticle() {
      $(".edit.modal").modal("show");
    },
    openCollections() {
      var that = this;
      $(".collect.modal").modal("show");
      that.$api.personalFavorite
        .getFavorites()
        .then(function (response) {
          that.collectLoading = false;
          that.favoriteList = response.data.data.favorites;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getFavoriteId(id) {
      var that = this;
      that.favoriteId = id;
      sessionStorage["favoriteId"] = id;
    },
    collectArticle() {
      var that = this;
      that.$api.userFavorite
        .collectQuestion(
          sessionStorage.getItem("favoriteId"),
          that.$route.query.articleId
        )
        .then(function (response) {
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
      that.$api.userQuestion
        .deleteQuestion(id)
        .then(function (response) {
          that.$message({
            message: "删除成功",
            type: "success",
          });
          that.$router.push("/BreakingTheBarrier/BBS/questions");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    flatten(data) {
      var that = this;
      return data.reduce(
        (
          arr,
          {
            adminComment,
            answer,
            blogId,
            commentsNum,
            id,
            approved,
            avatar,
            content,
            createTime,
            disLikesNum,
            disapproved,
            likesNum,
            looked,
            nickname,
            parentCommentId,
            postUserId,
            questionId,
            selected,
            receiveComments = [],
          }
        ) =>
          arr.concat(
            [
              {
                adminComment,
                answer,
                blogId,
                commentsNum,
                id,
                approved,
                avatar,
                content,
                createTime,
                disLikesNum,
                disapproved,
                likesNum,
                looked,
                nickname,
                parentCommentId,
                postUserId,
                questionId,
                selected,
              },
            ],
            that.flatten(receiveComments)
          ),
        []
      );
    },
    getAllComments() {
      var that = this;
      that.$api.userComment
        .getAllQuestionComments(that.$route.query.articleId)
        .then(function (response) {
          that.commentLoading = false;
          var data = response.data.data.comments2;
          that.commentList = that.flatten(data);
          that.bestComments = response.data.data.bestComments;
          that.selectedComments = response.data.data.selectedComments;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getMoreComments(id) {
      var that = this;
      that.nowParentId = id;
      that.$api.userComment
        .getChildComments(id)
        .then(function (response) {
          that.moreComments = response.data.data.receiveComments;
          $(".moreComments.modal").modal("show");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getCommentLikesAndDislikes(
      commentId,
      likesNum,
      dislikesNum,
      approved,
      disapproved,
      flag
    ) {
      var that = this;
      if (flag == true) {
        for (var i in that.commentList) {
          if (that.commentList[i].id == commentId) {
            that.commentList[i].likesNum = likesNum;
            that.commentList[i].disLikesNum = dislikesNum;
            that.commentList[i].approved = approved;
            that.commentList[i].disapproved = disapproved;
            break;
          }
        }
      } else {
        for (var i in that.moreComments) {
          if (that.moreComments[i].id == commentId) {
            that.moreComments[i].likesNum = likesNum;
            that.moreComments[i].disLikesNum = dislikesNum;
            that.moreComments[i].approved = approved;
            that.moreComments[i].disapproved = disapproved;
            break;
          }
        }
      }
    },
    likeComment(id, flag) {
      var that = this;
      that.$api.userLike
        .likeComment(id)
        .then(function (response) {
          that.getCommentLikesAndDislikes(
            id,
            response.data.data.likesNum,
            response.data.data.disLikesNum,
            response.data.data.approved,
            response.data.data.disapproved,
            flag
          );
          that.$message({
            message: "点赞成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    dislikeComment(id, flag) {
      var that = this;
      that.$api.userLike
        .dislikeComment(id)
        .then(function (response) {
          that.getCommentLikesAndDislikes(
            id,
            response.data.data.likesNum,
            response.data.data.disLikesNum,
            response.data.data.approved,
            response.data.data.disapproved,
            flag
          );
          that.$message({
            message: "点踩成功",
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    replyComment(id) {
      var that = this;
      that.parentId = id;
      $(".edit.modal").modal("show");
    },
    setSelectedComment(id, flag) {
      var that = this;
      that.$api.userComment
        .getCommentSelected(that.$store.state.articleId, id)
        .then(function (response) {
          that.template.solved = response.data.data.solved;
          if (flag == false) {
            that.getMoreComments(that.nowParentId);
          }
          that.getAllComments("", "");
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    deleteComment(id, flag) {
      var that = this;
      var p1 = new Promise((resolve, reject) => {
        that.$api.userComment
          .deleteQuestionComment(this.$route.query.articleId, id)
          .then(function (response) {
            that.solved = response.data.data.solved;
            if (flag == true) {
              that.getAllComments("", "");
            } else {
              that.getMoreComments(that.nowParentId);
            }
            that.$message({
              message: "删除成功",
              type: "success",
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      });

      var p2 = new Promise((resolve, reject) => {
        that.getAllComments(false, "", "");
      });

      Promise.all([p1, p2]).then((res) => {
      });
    },
    sure() {
      var that = this;
      var p1 = new Promise((resolve, reject) => {
        if (that.phoneEditor.txt.html()) {
          var data = {
            content: that.phoneEditor.txt.html(),
            answer: true,
            parentCommentId0: that.parentId,
          };
          that.$api.userComment
            .postQuestionComment(that.$route.query.articleId, data)
            .then(function (response) {
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
      });

      var p2 = new Promise((resolve, reject) => {
        that.getAllComments(false, "", "");
      });

      Promise.all([p1, p2]).then((res) => {
      });
    },
  },
};
</script>

<style scoped>
h3:nth-child(2) {
  text-align: center;
}

.child.comment {
  margin-left: 50px;
}
</style>
