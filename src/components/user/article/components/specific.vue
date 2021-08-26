<template>
  <div>
    <div class="ui large feed">
      <div class="event">
        <div class="label">
          <img src="../../../../assets/logo.png" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ template.nickname }} </a>
            <div class="date">{{ template.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
    <h3 class="title">{{ template.title }}</h3>
    <br />
    <div v-html="articleContent">{{ articleContent }}</div>
    <br />
    <div class="buttons">
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
        @click="openCollections()"
      >
        <i class="star icon"></i>
        {{ articleCollectNumber }}
      </div>
      <div
        class="ui button"
        :class="disapproved == true ? articleDislikeIsActive : button"
        @click="dislikeArticle(template.id)"
      >
        <i class="heart icon"></i>
        {{ articleDislikeNumber }}
      </div>
      <div class="ui blue icon button" v-if="template.nickname == nowUser">
        <i class="trash icon" @click="deleteArticle(template.id)"></i>
      </div>
    </div>
    <!-- <div class="ui divider"></div>
    <p style="float: left">转发</p>
    <p>举报</p> -->

    <!-- --------------------------- -->
    <br />
    <div>
      <!-- <div class="ui divider"></div> -->
      <div>
        <div class="ui comments">
          <h3 class="ui dividing header">全部评论</h3>
          <div
            :class="item.parentCommentId0 == -1 ? parent : child"
            v-for="item in commentList"
          >
            <a class="avatar">
              <img src="../../../../assets/logo.png" />
            </a>
            <div class="content">
              <a class="author">{{ item.nickname }}</a>
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
                  v-if="item.nickname == nowUser"
                  >删除</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- -------------------------- -->
    <br />
    <div>
      <h3>添加评论</h3>
      <!--  <div class="ui divider"></div> -->
      <div id="websiteEditorElem"></div>
      <br />
      <div class="ui right floated button" @click="sure()">确定</div>
    </div>
    <br />
    <div class="ui basic modal">
      <div class="ui four stackable cards">
        <div class="card" v-for="item in favoriteList">
          <div class="image"><img src="../../../../assets/bg.jpg" /></div>
          <div class="content">
            <a class="header" @click="getFavoriteId(item.id)">
              {{ item.title }}</a
            >
          </div>
        </div>
      </div>
      <div class="actions">
        <div class="ui green ok inverted button" @click="collectArticle()">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import E from "wangeditor";
export default {
  name: "specific",
  data() {
    return {
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
      nowUser: sessionStorage.getItem("nickname"),
      favoriteList: [],
      /* ---------------------------------- */
      likeNumber: "",
      dislikeNumber: "",
      commentList: "",
      parent: "comment",
      child: "child comment",
      /* ----------------------------- */
      content: "",
    };
  },
  created() {
    var that = this;
    axios
      .get("/customer/personal")
      .then(function (response) {
        console.log(response.data.data.user);
        sessionStorage["nickname"] = response.data.data.user.nickname;
      })
      .catch(function (error) {
        console.log(error);
      });
    axios
      .get("/question/" + this.$route.query.articleId)
      .then(function (response) {
        that.template = response.data.data.template;
        (that.approved = that.template.approved),
          (that.disapproved = that.template.disapproved),
          (that.collected = that.template.collected),
          (that.articleContent = that.template.content);
        that.articleLikeNumber = that.template.likesNum;
        that.articleCollectNumber = that.template.collectedNum;
        that.articleDislikeNumber = that.template.disLikesNum;
        sessionStorage["articleId"] = that.template.id;
        console.log(that.template);
      })
      .catch(function (error) {
        console.log(error);
      });
    /* ------------------------- */
    axios
      .get("/question/" + this.$route.query.articleId + "/comments")
      .then(function (response) {
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
      axios
        .get("/customer/favorites")
        .then(function (response) {
          that.favoriteList = response.data.data.favorites;
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
      $(".ui.basic.modal").modal("show");
    },
    getFavoriteId(id) {
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
          that.$router.push("/helloWorld/BBS");
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
    replyComment(id) {
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
    },
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
          .post(
            "/question/" +
              sessionStorage.getItem("articleId") +
              "/comment/post",
            {
              content: that.phoneEditor.txt.html(),
              answer: true,
              parentCommentId0: "-1",
            }
          )
          .then(function (response) {
            console.log(response.data);
            that.commentList = that.flatten(response.data.data.comments);
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
} /* --------------------------- */
.child.comment {
  margin-left: 50px;
}
</style>
