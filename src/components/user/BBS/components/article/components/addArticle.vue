<template>
  <div class="body">
    <div class="ui icon message" style="margin-top: 20px">
      <i class="inbox icon"></i>
      <div class="content">
        <div class="header">
          图片格式为png/bmp/gif/jpg与jpeg
        </div>
      </div>
    </div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">标题</div>
      <input type="text" placeholder="" v-model="title"/>
    </div>
    <div class="ui fluid labeled input">
      <div class="ui teal label">概述</div>
      <input type="text" placeholder="" v-model="description"/>
    </div>
    <editor></editor>
    <el-dialog width="600px" :visible.sync="dialogFormVisible">
      <tags></tags>
    </el-dialog>
    <div class="ui teal button" @click="chooseTag()" style="margin-top: 20px">选择标签</div>
    <div class="ui teal right floated button" style="margin-top: 20px" @click="sure()">确定</div>
  </div>
</template>

<script>
import tags from "../../editor/tags";
import editor from "../../editor/editor";
/*import wx from "weixin-js-sdk";*/

export default {
  name: "addArticle",
  data() {
    return {dialogFormVisible: false, title: "", description: ""};
  },
  components: {editor, tags},
  methods: {
    chooseTag() {
      var that = this;
      that.dialogFormVisible = true;
    },
    sure() {
      var that = this;
      if (that.title && that.description && that.$store.state.text) {
        if (!sessionStorage.getItem("chooseTagIdList")) {
          that.$message({
            message: "请选择标签",
            type: "warning",
          });
        } else {
          console.log(that.$store.state.text);
          var data = {
            title: that.title,
            description: that.description,
            content: that.$store.state.text,
            tagIds: sessionStorage.getItem("chooseTagIdList"),
          };
          that.$api.adminTop.getWXToken().then(function (response) {
            console.log(response.data);
          }).catch(function (error) {
            console.log(error);
          });
          that.$api.userQuestion
            .postQuestion(data)
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
                that.$router.push("/BreakingTheBarrier/BBS/questions");
                that.$store.commit("getText", "");
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

.body {
  margin: auto;
  width: 80%;
}
</style>
