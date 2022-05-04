<template>
  <div class="body">
    <button id="openHotBtn" class="ui mini blue button" @click="openHot"><i class="id card icon"></i></button>
    <div class="mask" style="display: block"></div>
    <div class="main">
      <div class="ui segment">
        <el-row :gutter="20" style="display: flex;margin: 0">
          <el-col :span="6" style="padding: 0">
            <div class="avatar" style="margin-left: 0; margin-top: 0">
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
              <div style="width: 70px;height: 70px;margin: 0 auto"><img
                style="height: 100%;width: 100%"
                class="ui tiny centered circular image"
                :src="$store.state.me.avatar"
                alt="avatar"
              /></div>
            </div>
          </el-col>
          <el-col :span="14" >
            <div class="information" style="margin-top: 0">
              <h2>{{ $store.state.me.nickname }}</h2>
              <p style="color: grey" v-if="$store.state.me.personalSignature">
                {{ $store.state.me.personalSignature }}
              </p>
              <p style="color: grey" v-else>暂无签名</p>
            </div>
          </el-col>
        </el-row>
        <div class="data">
          <div class="ui four mini statistics btb-h3"
               style="display: flex;
               flex-wrap: nowrap;
               white-space: nowrap;margin-top: 10px">
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
      <div>
        <router-view></router-view>
      </div>
    </div>
    <div class="side" style="">
      <recommend></recommend>
    </div>
  </div>
</template>

<script>
const imageConversion = require("image-conversion");
import recommend from "./components/recommend";
import $ from "jquery";

export default {
  components: {recommend},
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
  mounted() {
    window.addEventListener('resize',() => {
      if(this.$route.path.lastIndexOf('mine') !== -1) {
        let mask = document.getElementsByClassName('mask')[0]
        let hot = document.getElementsByClassName('side')[0]
        if(window.innerWidth > 550) {
          mask.style.cssText = 'opacity: 0;z-index:-1'
          hot.style.cssText = ''
        }
        else
          mask.style.cssText = ''
      }
    })
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
    openHot() {
      let mask = document.getElementsByClassName('mask')[0]
      if (!this.isShow) {
        mask.style.cssText = 'z-index: 1'
        $('div.side').animate({
          top: '72px',
          opacity: '1',
          zIndex: '2'
        })
        $('div.mask').animate({
          opacity: '1'
        })
        this.isShow = true
      }
      else if(this.isShow){
        $('div.side').animate({
          top: '-520px',
          opacity: 0
        })
        $('div.mask').animate({
          opacity: '0',
          zIndex: '-1'
        })
        this.isShow = false
      }
    }
  },
};
</script>

<style scoped lang="scss">
.body {
  width: 80vw;
  margin: auto;
  display: flex;
  flex-direction: row;
  min-height: 80vh;
  > button.button {
    z-index: 10;
    top: 0;
    display: none;
    margin-bottom: 5px;
  }
  .mask {
    position: fixed;
    z-index: -1;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(38, 50, 56,.8);
    opacity: 0;
  }
  .main {
    margin-top: 20px;
    width: 73%;
    .specific {
      width: 98%;
      margin: auto;
    }
  }
  .side {
    //width: 27%;
    z-index: 2;
    width: 30%;
    margin-top: 20px;
    box-sizing: border-box;
  }
}
@media screen and (max-width: 700px){
  .body {
    flex-direction: column;
    align-items: center;
    width: 100%;
    > button.button {
      display: block;
      width: 100%;
    }
    .main {
      width: 100%;
      margin-top: 0;
    }
    .side {
      width: 102%;
      position: absolute;
      top: 0;
      //right: -380px;
      opacity: 0;
      z-index: -1;
    }
  }

}
</style>
