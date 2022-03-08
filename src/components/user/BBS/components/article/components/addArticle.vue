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

                /*wx.config({
                  debug: false, // 开启调试模式,
                  appId: data.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
                  timestamp: data.timestamp, // 必填，生成签名的时间戳
                  nonceStr: data.nonceStr, // 必填，生成签名的随机串
                  signature: data.signature, // 必填，签名，见附录1
                  jsApiList: ["chooseImage"], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });
                wx.ready(function () {
                  alert("ready");
                  // 在这里调用 API
                  //判断当前客户端版本是否支持指定JS接口
                  wx.checkJsApi({
                    jsApiList: [
                      // 所有要调用的 API 都要加到这个列表中
                      "chooseImage"
                    ], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                    success: function (res) {
                      console.log('checkJsApi成功=====', res);
                      alert('支持chooseImage');
                      // 以键值对的形式返回，可用的api值true，不可用为false
                      // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
                    },
                    fail:function(e){
                      alert('不支持');
                    }
                  });
                });
                wx.error(function (res) {
                  // wx.config注册失败就会执行
                  alert("error");
                });*/

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
