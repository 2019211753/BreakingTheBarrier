<template>
  <div class="body">
    <div class="main">
      <div class="ui segment">
        <!-- <el-skeleton :loading="loading" animated v-if="loading == true">
          <template slot="template">
            <el-row :gutter="24">
              <el-col :span="6">
                <el-skeleton-item
                  style="
                    width: 130px;
                    height: 130px;
                    margin-left: 20px;
                    margin-top: 10px;
                  "
                  variant="circle image"
                /> </el-col
              ><el-col :span="1" style="height: 1px"> </el-col>
              <el-col :span="17">
                <el-skeleton-item
                  style="height: 30px; margin-top: 20px"
                  variant="text"
                />
                <el-skeleton-item variant="text" style="margin-top: 5px" />
                <el-skeleton-item
                  style="height: 20px; margin-top: 5px"
                  variant="text"
                />
                <el-skeleton-item style="height: 20px" variant="text" />
              </el-col>
            </el-row>
          </template>
        </el-skeleton> -->
        <el-row :gutter="20">
          <el-col :span="6"
            ><div class="avatar" style="margin-left: 20px; margin-top: 10px">
              <div class="ui inverted dimmer">
                <div class="content">
                  <el-upload
                    class="avatar-uploader"
                    action=""
                    :http-request="handleTestSuccess"
                    :show-file-list="false"
                  >
                    <div class="ui blue small icon button">
                      <i class="ui edit icon"></i>
                    </div>
                  </el-upload>
                </div>
              </div>
              <!-- <img v-if="imageUrl" :src="imageUrl" class="avatar" /> -->
              <img
                :src="'data:image/jpg;base64,' + $store.state.me.avatar"
                alt=""
                class="ui small circular image"
              />
            </div>
          </el-col>
          <el-col :span="14">
            <div class="information" style="margin-top: -5px">
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
import recommend from "./components/recommend";
import axios from "axios";
import $ from "jquery";
/* import jwtDecode from "jwt-decode"; */
export default {
  components: { recommend },
  name: "mine",
  computed: {
    headers() {
      return {
        Authorization: sessionStorage.getItem("token"), // 直接从本地获取token就行
      };
    },
  },
  data() {
    return {
      loading: true,
    };
  },
  created() {
    var that = this;
    $(function () {
      $(".avatar").dimmer({
        on: "hover",
      });
    });
   /*  window.addEventListener("setItem", (e) => {
      if (e.key == "nickname") {
        console.log(e.newValue);
        that.nickname = e.newValue;
      }
    });
    window.addEventListener("setItem", (e) => {
      if (e.key == "personalSignature") {
        console.log(e.newValue);
        that.personalSignature = e.newValue;
      }
    }); */
    /* window.addEventListener("setItem", (e) => {
      that.nickname = e.newValue;
    }); */
    /* window.addEventListener("setItem", (e) => {
      that.personalSignature = e.newValue;
    }); */
  },
  methods: {
    /* handleRemove(file, fileList) {
      console.log("____________________");
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log("____________________");
      console.log("file.url");
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    }, */
    /* beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 0.0977;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 100kb!");
      }
      return isJPG && isLt2M;
    }, */

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
      axios
        .post("/customer/uploadAvatar", formData)
        .then((res) => {
          if (res.status === 200) {
            var that = this;
            console.log(res.data);
            that.resetSetItem("avatar", res.data.data.avatar);
            that.$message.success("上传头像成功!");
            that.$store.commit("getMyAvatar", res.data.data.avatar);

            /* var decode = jwtDecode(res.data.data);
            console.log(decode);
            that.nickname = decode.nickname; */
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
  /* background-color: aqua; */
}

.main {
  margin-top: 20px;
  width: 64%;
  float: left;
  /* height: 600px; */
  /* background-color: bisque; */
}

.side {
  margin-top: 20px;
  width: 35%;
  float: left;
  margin-left: 1%;
  height: 380px;
  /* background-color: bisque; */
}

.avatar {
  width: 95%;
  /* height: 150px; */
  /* background-color: rgb(82, 168, 168); */
  float: left;
}

.information {
  width: 400px;
  height: 150px;
  margin-left: 20px;
  /* background-color: rgb(50, 236, 236); */
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
