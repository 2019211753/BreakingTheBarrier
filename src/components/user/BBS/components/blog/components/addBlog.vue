<template>
  <div class="body">
    <div class="ui icon message" style="margin-top: 20px">
      <i class="inbox icon"></i>
      <div class="content">
        <div class="header">
          图片格式为png/bmp/gif/jpg与jpeg，宽度不能超过554px
        </div>
      </div>
    </div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">标题</div>
      <input type="text" placeholder="" v-model="title" />
    </div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">概述</div>
      <input type="text" placeholder="" v-model="description" />
    </div>
    <editor></editor>
    <el-dialog width="600px" :visible.sync="dialogFormVisible">
      <tags></tags>
    </el-dialog>
    <div class="ui fluid labeled input" v-if="original == false">
      <div class="ui teal label">转载声明</div>
      <input type="text" placeholder="" v-model="statement" />
    </div>
    <div :class="original == true ? Y : N" @click="setOriginal()">转载</div>
    <div :class="open == true ? Y : N" @click="setOpen()">私密</div>
    <div :class="commentAllowed == true ? Y : N" @click="setCommentAllowed()">
      不允许评论
    </div>
    <div
      :class="appreciationAllowed == true ? Y : N"
      @click="setAppreciationAllowed()"
    >
      不接受赞赏
    </div>
    <div class="ui teal right floated button" @click="sure()">确定</div>
    <div class="ui right floated teal button" @click="chooseTag()">
      选择标签
    </div>
  </div>
</template>

<script>
import tags from "../../editor/tags";
import editor from "../../editor/editor";

export default {
  name: "addBlog",
  components: { editor, tags },
  data() {
    return {
      dialogFormVisible: false,
      title: "",
      description: "",
      statement: "",
      original: true,
      appreciationAllowed: true,
      open: true,
      commentAllowed: true,
      N: "ui icon blue button",
      Y: "ui icon button",
    };
  },
  methods: {
    chooseTag() {
      var that = this;
      that.dialogFormVisible = true;
    },
    getOrigin(name) {
      alert(name);
    },
    setOriginal() {
      var that = this;
      that.original = !that.original;
    },
    setOpen() {
      var that = this;
      that.open = !that.open;
    },
    setCommentAllowed() {
      var that = this;
      that.commentAllowed = !that.commentAllowed;
    },
    setAppreciationAllowed() {
      var that = this;
      that.appreciationAllowed = !that.appreciationAllowed;
    },
    sure() {
      var that = this;
      if (that.original == false) {
        if (
          that.title &&
          that.statement &&
          that.description &&
          that.$store.state.text
        ) {
          if (!sessionStorage.getItem("chooseTagIdList")) {
            this.$message({
              message: "请选择标签",
              type: "warning",
            });
          } else {
            var data = {
              title: that.title,
              description: that.description,
              content: that.$store.state.text,
              tagIds: sessionStorage.getItem("chooseTagIdList"),
              origin: that.original,
              appreciation: that.appreciationAllowed,
              open: that.open,
              commentAllowed: that.commentAllowed,
              transferStatement: that.statement,
            };
            that.$api.userBlog
              .postBlog(data)

              .then(function (response) {
                if (response.data.code == 403) {
                  that.$message({
                    message: response.data.msg,
                    type: "warning",
                  });
                } else {
                  that.$message({
                    message: "发布成功",
                    type: "success",
                  });
                  that.$router.push("/BreakingTheBarrier/BBS/blogs");
                }
              })
              .catch(function (error) {
                console.log(error);
              });
          }
        } else {
          this.$message({
            message: "请填写所有内容",
            type: "warning",
          });
        }
      } else {
        if (that.title && that.description && that.$store.state.text) {
          if (!sessionStorage.getItem("chooseTagIdList")) {
            this.$message({
              message: "请选择标签",
              type: "warning",
            });
          } else {
            var data = {
              title: that.title,
              description: that.description,
              content: that.$store.state.text,
              tagIds: sessionStorage.getItem("chooseTagIdList"),
              origin: that.original,
              appreciation: that.appreciationAllowed,
              open: that.open,
              commentAllowed: that.commentAllowed,
              transferStatement: that.statement,
            };
            that.$api.userBlog
              .postBlog(data)
              .then(function (response) {
                if (response.data.code == 403) {
                  that.$message({
                    message: response.data.msg,
                    type: "warning",
                  });
                } else {
                  that.$message({
                    message: "发布成功",
                    type: "success",
                  });
                  that.$router.push("/BreakingTheBarrier/BBS/blogs");
                }
              })
              .catch(function (error) {
                console.log(error);
              });
          }
        } else {
          this.$message({
            message: "请填写所有内容",
            type: "warning",
          });
        }
      }
    },
  },
};
</script>

<style scoped>
.body {
  margin: auto;
  width: 80%;
}
.input {
  margin-top: 20px;
}

.button {
  margin-top: 20px;
}
</style>
