<template>
  <div class="body">
    <div class="main">
      <div class="ui segment">
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="avatar" style="margin-left: 20px; margin-top: 10px">
              <div class="ui inverted dimmer">
                <div class="content">
                  <el-upload
                    class="avatar-uploader"
                    action=""
                    :before-upload="beforeAvatarUpload"
                    :http-request="handleTestSuccess"
                    :show-file-list="false"
                  >
                    <div class="ui blue small icon button">
                      <i class="ui edit icon"></i>
                    </div>
                  </el-upload>
                </div>
              </div>
              <img
                :src="$store.state.me.avatar"
                alt=""
                class="ui small circular image"
              />
            </div>
          </el-col>
          <el-col :span="14">
            <div class="information">
              <h2>{{ $store.state.me.nickname }}</h2>
              <p style="color: grey" v-if="$store.state.me.personalSignature">
                {{ $store.state.me.personalSignature }}
              </p>
              <p style="color: grey" v-else>暂无签名</p>
              <div class="data">
                <div class="ui four mini statistics">
                  <div class="statistic">
                    <div class="value">3</div>
                    <div class="label" style="margin-top: 3px">内容</div>
                  </div>
                  <div class="statistic">
                    <div class="value">{{ $store.state.me.donation }}</div>
                    <div class="label" style="margin-top: 3px">贡献度</div>
                  </div>
                  <div class="statistic">
                    <div class="value">
                      {{ $store.state.me.followedUserNum }}
                    </div>
                    <div class="label" style="margin-top: 3px">粉丝</div>
                  </div>
                  <div class="statistic">
                    <div class="value">
                      {{ $store.state.me.followingUserNum }}
                    </div>
                    <div class="label" style="margin-top: 3px">关注</div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div>
        <router-view></router-view>
      </div>
    </div>
    <div class="side">
      <recommend></recommend>
    </div>
  </div>
</template>

<script>
const imageConversion = require("image-conversion");
import recommend from "./components/recommend";
import $ from "jquery";

export default {
  components: { recommend },
  name: "mine",
  data() {
    return {
      loading: true,
    };
  },
  created() {
    $(function () {
      $(".avatar").dimmer({
        on: "hover",
      });
    });
  },
  methods: {
    beforeAvatarUpload(file) {
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/bmp" ||
        file.type === "image/gif" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        this.$message.error("上传头像图片只能是 JPG 或 PNG 格式!");
        return false;
      }
      return new Promise((resolve) => {
        imageConversion.compressAccurately(file, 100).then((res) => {
          resolve(res);
        });
        //compressAccurately有多个参数时传入对象
        //imageConversion.compressAccurately(file, {
        // size: 1024, //图片大小压缩到1024kb
        // width:1280 //宽度压缩到1280
        //}).then(res => {
        //resolve(res)
        //})
      });
    },

    handleTestSuccess(file) {
      if (file.file.type.indexOf("image") == -1) {
        this.$message.error("请上传图片类型的文件");
        this.$refs.upload_img.uploadFiles =
          this.$refs.upload_img.uploadFiles.filter((item) => {
            return file.file.name != item.name;
          });
        return;
      }
      const formData = new FormData();
      formData.append("file", file.file);
      var that = this;
      that.$api.personalInformation
        .uploadAvatar(formData)
        .then((res) => {
          if (res.status === 200) {
            var that = this;

            that.$message.success("上传头像成功!");
            that.$store.commit("getMyAvatar", res.data.data.avatar);
          }
        })
        .catch((err) => {
          /* this.$refs.upload_img.uploadFiles =
            this.$refs.upload_img.uploadFiles.filter((item) => {
              return file.file.name != item.name;
            });
          this.$message.error("上传失败!"); */
        });
    },
  },
};
</script>

<style scoped>
.body {
  width: 80%;
  margin: auto;
  height: 760px;
}

.main {
  margin-top: 20px;
  width: 64%;
  float: left;
}

.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 1%;
  height: 380px;
}

.avatar {
  width: 95%;
  float: left;
}

.information {
  width: 400px;
  height: 150px;
  margin-left: 20px;
  float: left;
}

h2 {
  margin-top: 20px;
  margin-left: 5%;
}

p {
  margin-left: 5%;
}

.data {
  width: 50%;
}
</style>
