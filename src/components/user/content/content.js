
function likeArticle(id) {
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
};

function dislikeArticle(id) {
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
};

function getArticleLikesAndDislikes(approved, disapproved, likesNum) {
  var that = this;
  that.articleApproved = approved;
  that.articleLikeNumber = likesNum;
  that.articleDisapproved = disapproved;
};

function replyArticle() {
  $(".edit.modal").modal("show");
};

function openCollections() {
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
};

function getFavoriteId(id) {
  var that = this;
  that.favoriteId = id;
  sessionStorage["favoriteId"] = id;
};

function collectArticle() {
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
};

function deleteArticle(id) {
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
};

function flatten(data) {
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
};

function getAllComments() {
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
};

function getMoreComments(id) {
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
};

function getCommentLikesAndDislikes(
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
};

function likeComment(id, flag) {
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
};

function dislikeComment(id, flag) {
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
};

function replyComment(id) {
  var that = this;
  that.parentId = id;
  $(".edit.modal").modal("show");
};

function setSelectedComment(id, flag) {
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
};

function deleteComment(id, flag) {
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
};

function sure() {
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
};
export {
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
}
