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
    <div class="ui teal right floated button" @click="sure()">确定</div>
  </div>
</template>

<script>
import axios from "axios";
import E from "wangeditor";
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

    this.phoneEditor.config.uploadImgShowBase64 = true;

    this.phoneEditor.create();

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
