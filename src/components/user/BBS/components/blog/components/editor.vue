<template>
  <div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">标题</div>
      <input type="text" placeholder="" v-model="title" />
    </div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">概述</div>
      <input type="text" placeholder="" v-model="description" />
    </div>
    <div id="websiteEditorElem"></div>
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
  </div>
</template>

<script>
import E from "wangeditor";
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "editor",
  data() {
    return {
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
  /* props: ["chooseTagIdList"], */
  mounted() {
    // wangeditor
    this.phoneEditor = new E("#websiteEditorElem");
    this.phoneEditor.config.zIndex = 500;
    this.phoneEditor.config.height = 500;
    // this.phoneEditor.onchange = function () {
    //   this.formData.phone = this.$txt.html()
    // }
    // 上传图片到服务器，base64形式
    this.phoneEditor.config.uploadImgShowBase64 = true;
    // 隐藏网络图片
    //this.phoneEditor.customConfig.showLinkImg = false;
    // 创建一个富文本编辑器
    // 配置alt选项

    this.phoneEditor.create();
    // 富文本内容
    this.phoneEditor.txt.html();
  },
  methods: {
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
      /*      alert(that.chooseTagIdList); */
      /* if(that.original==false){} */
      if (that.original == false) {
        if (
          that.title &&
          that.statement &&
          that.description &&
          that.phoneEditor.txt.html()
        ) {
          if (!sessionStorage.getItem("chooseTagIdList")) {
            this.$message({
              message: "请选择标签",
              type: "warning",
            });
          } else {
            axios
              .post("/customer/blog/post", {
                title: that.title,
                description: that.description,
                content: that.phoneEditor.txt.html(),
                tagIds: sessionStorage.getItem("chooseTagIdList"),
                origin: that.original,
                appreciation: that.appreciationAllowed,
                open: that.open,
                commentAllowed: that.commentAllowed,
                transferStatement: that.statement,
              })
              .then(function (response) {
                console.log(response);
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
                  that.$router.push("/helloWorld/BBS/blogs");
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
        if (that.title && that.description && that.phoneEditor.txt.html()) {
          if (!sessionStorage.getItem("chooseTagIdList")) {
            this.$message({
              message: "请选择标签",
              type: "warning",
            });
          } else {
            axios
              .post("/customer/blog/post", {
                title: that.title,
                description: that.description,
                content: that.phoneEditor.txt.html(),
                tagIds: sessionStorage.getItem("chooseTagIdList"),
                origin: that.original,
                appreciation: that.appreciationAllowed,
                open: that.open,
                commentAllowed: that.commentAllowed,
                transferStatement: that.statement,
              })
              .then(function (response) {
                console.log(response);
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
                  that.$router.push("/helloWorld/BBS/blogs");
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
.input {
  margin-top: 20px;
}
#websiteEditorElem {
  margin-top: 20px;
}
.button {
  margin-top: 20px;
}
.el-switch {
  margin-left: 10px;
  margin-top: 30px;
}
</style>
