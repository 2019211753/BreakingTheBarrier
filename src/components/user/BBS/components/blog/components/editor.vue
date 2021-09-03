<template>
  <div>
    <div class="ui fluid labeled input">
      <div class="ui label">标题</div>
      <input type="text" placeholder="" v-model="title" />
    </div>
    <div class="ui fluid labeled input">
      <div class="ui label">概述</div>
      <input type="text" placeholder="" v-model="description" />
    </div>
    <div id="websiteEditorElem"></div>
    <el-switch
      class="el-switch"
      v-model="origin"
      active-color="#13ce66"
      inactive-color="grey"
      active-text="原创"
    >
    </el-switch>
    <el-switch
      class="el-switch"
      v-model="open"
      active-color="#13ce66"
      inactive-color="grey"
      active-text="公开"
    >
    </el-switch
    ><el-switch
      class="el-switch"
      v-model="commentAllowed"
      active-color="#13ce66"
      inactive-color="grey"
      active-text="允许评论"
    >
    </el-switch
    ><el-switch
      class="el-switch"
      v-model="appreciation"
      active-color="#13ce66"
      inactive-color="grey"
      active-text="接受赞赏"
    >
    </el-switch>
    <div class="ui right floated button" @click="sure()">确定</div>
  </div>
</template>

<script>
import E from "wangeditor";
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "editor",
  data() {
    return {
      title: "",
      description: "",
      origin: true,
      appreciation: true,
      open: true,
      commentAllowed: true,
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
    this.phoneEditor.create();
    // 富文本内容
    this.phoneEditor.txt.html();
  },
  methods: {
    getOrigin(name) {
      alert(name);
    },
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
            .post("/customer/blog/post", {
              title: that.title,
              description: that.description,
              content: that.phoneEditor.txt.html(),
              tagIds: sessionStorage.getItem("chooseTagIdList"),
              origin: that.origin,
              appreciation: that.appreciation,
              open: that.open,
              commentAllowed: that.commentAllowed,
            })
            .then(function (response) {
              console.log(response);
            })
            .catch(function (error) {
              console.log(error);
            });
          that.$message({
            message: "发布成功",
            type: "success",
          });
          this.$router.push("/helloWorld/BBS/blog");
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
.el-switch {
  margin-left: 10px;
  margin-top: 30px;
}
</style>
