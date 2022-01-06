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
    <div class="ui green right floated button" @click="sure()">确定</div>
  </div>
</template>

<script>
import E from "wangeditor";
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "editor",
  data() {
    return { title: "", description: "" };
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
    this.phoneEditor.create();
    // 富文本内容
    this.phoneEditor.txt.html();
  },
  methods: {
    sure() {
      var that = this;
      /*      alert(that.chooseTagIdList); */
      if (that.title && that.description && that.phoneEditor.txt.html()) {
        if (!sessionStorage.getItem("chooseTagIdList")) {
          this.$message({
            message: "请选择标签",
            type: "warning",
          });
        } else {
          axios
            .post("/customer/question/post", {
              title: that.title,
              description: that.description,
              content: that.phoneEditor.txt.html(),
              tagIds: sessionStorage.getItem("chooseTagIdList"),
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
                that.$router.push("/helloWorld/BBS/questions");
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
</style>
