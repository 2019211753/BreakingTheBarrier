<template>
  <div>
    <div class="mainContainer">
      <div>
        <span><i class="user icon"></i>用户昵称</span>
        <div class="ui fluid input">
          <input
            type="text"
            :placeholder="$store.state.me.nickname"
            v-model="nickname"
          />
        </div>
      </div>
      <div>
        <span><i class="venus mars icon"></i>性别</span>
        <div class="ui fluid selection dropdown">
          <input type="hidden" name="gender"/>
          <i class="dropdown icon"></i>
          <div class="default text" v-if="$store.state.me.sex">
            <div v-if="$store.state.me.sex === 'true'">男</div>
            <div v-if="$store.state.me.sex === 'false'">女</div>
          </div>
          <div class="default text" v-else>无</div>
          <div class="menu">
            <div class="item" @click="boy()">男</div>
            <div class="item" @click="girl()">女</div>
          </div>
        </div>
      </div>
      <div>
        <span><i class="envelope icon"></i>邮箱</span>
        <div class="ui fluid input">
          <input
            type="text"
            :placeholder="$store.state.me.email"
            v-model="email"
          />
        </div>
      </div>
      <div>
        <span><i class="qq icon"></i>QQ</span>
        <div class="ui fluid input">
          <input
            type="text"
            style="margin-left: -3px"
            :placeholder="$store.state.me.qqId"
            v-model="QQ"
          />
        </div>
      </div>
      <div>
        <span><i class="users icon"></i>学院</span>
        <div class="ui fluid selection dropdown">
          <input type="hidden" name="gender"/>
          <i class="dropdown icon"></i>
          <div class="default text" v-if="$store.state.me.academy">
            {{ $store.state.me.academy }}
          </div>
          <div class="default text" v-else>无</div>
          <div class="menu">
            <div
              v-for="(item, index) in academyList"
              class="item"
              @click="selectAcademy(index)"
            >
              {{ index }}
            </div>
          </div>
        </div>
      </div>
      <div>
        <span><i class="user md icon"></i>专业</span>
        <div class="ui fluid selection dropdown">
          <input type="hidden" name="gender"/>
          <i class="dropdown icon"></i>
          <div class="default text" v-if="$store.state.me.major">
            {{ $store.state.me.major }}
          </div>
          <div class="default text" v-else>无</div>
          <div class="menu">
            <div
              class="item"
              v-for="(item, index) in majorList"
              @click="selectMajor(item)"
            >
              {{ item }}
            </div>
          </div>
        </div>
      </div>
      <div>
        <span><i class="key icon"></i>修改密码</span>
        <div class="ui fluid input">
          <input type="text" placeholder="长度为7至11" v-model="passWord"/>
        </div>
      </div>
      <div>
        <span><i class="shield alternate icon"></i>确认密码</span>
        <div class="ui fluid input">
          <input
            type="text"
            placeholder="再次输入确认"
            v-model="surePassWord"
          />
        </div>
      </div>
      <div>
        <span><i class="flag icon"></i>签名</span>
        <div class="ui fluid input">
          <input
            type="text"
            :placeholder="$store.state.me.personalSignature"
            v-model="personalSignature"
          />
        </div>
      </div>
      <div>
        <div class="upQrCode">
          <el-upload
            class="avatar-uploader"
            action=""
            :before-upload="beforeAvatarUpload"
            :http-request="handleTestSuccess"
            :show-file-list="false"
          >
            <button class="ui compact labeled icon button" @click="uploadWX()">
              <i class="upload icon"></i>
              上传收款码
            </button>

          </el-upload>
        </div>
        <div class="sure" @click="sure()">
          <button class="ui right labeled icon button">
            <i class="right arrow icon"></i>
            确定
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const imageConversion = require("image-conversion");

export default {
  name: "information",
  data() {
    return {
      nickname: "",
      personalSignature: "",
      sex: "",
      email: "",
      QQ: "",
      academy: "",
      major: "",
      academyList: "",
      majorList: "",
      imageUrl: "",
      passWord: "",
      surePassWord: "",
      value: "",
      leseThan700: false
    };
  },
  created() {
    this.academyList = this.$store.state.me.academies;
    jQuery(function () {
      jQuery(".ui.dropdown").dropdown();
    });
  },
  methods: {
    boy() {
      var that = this;
      that.sex = true;
    },
    girl() {
      var that = this;
      that.sex = false;
    },
    selectAcademy(selected) {
      var that = this;
      that.academy = selected;
      for (var item in that.academyList) {
        if (selected == item) {
          that.majorList = that.academyList[item];
          break;
        }
      }
    },
    selectMajor(selected) {
      var that = this;
      that.major = selected;
    },
    logOut() {
      sessionStorage.clear();
      history.pushState(null, null, document.URL);
      window.addEventListener("popstate", function () {
        history.pushState(null, null, document.URL);
      });
    },
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
        .uploadWX(formData)
        .then((res) => {
          if (res.status === 200) {
            var that = this;
            that.$message.success("上传收款码成功!");
            that.$store.commit("getMyPayCode", res.data.data.payCode);
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
    uploadWX() {
    },
    sure() {
      var that = this;
      if (that.passWord === that.surePassWord) {
        var data = {
          nickname: that.nickname,
          personalSignature: that.personalSignature,
          sex: that.sex,
          email: that.email,
          qqId: that.QQ,
          academy: that.academy,
          major: that.major,
          password: that.passWord,
          wechatId: "",
        };
        that.$api.personalInformation
          .modifyAllInformation(data)
          .then(function (response) {
            if (response.data.code == 200) {
              if (that.passWord && that.surePassWord) {
                that.$message({
                  message: "修改信息成功，请重新登录",
                  type: "success",
                });
                that.logOut();
              } else {
                that.$message({
                  message: "修改信息成功",
                  type: "success",
                });
                that.resetSetItem("token", response.data.data.token);
                if (that.nickname) {
                  that.$store.commit("getMyNickname", that.nickname);
                }
                if (that.academy) {
                  that.$store.commit("getMyAcademy", that.academy);
                }
                if (that.email) {
                  that.$store.commit("getMyEmail", that.email);
                }
                if (that.major) {
                  that.$store.commit("getMyMajor", that.major);
                }
                if (that.sex) {
                  that.$store.commit("getMySex", that.sex);
                }
                if (that.QQ) {
                  that.$store.commit("getMyQQ", that.QQ);
                }
                if (that.personalSignature) {
                  that.$store.commit(
                    "getMyPersonalSignature",
                    that.personalSignature
                  );
                }
              }
            } else {
              that.$message({
                message: response.data.msg,
                type: "warning",
              });
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message.error("两次输入的密码不相同");
      }
    },
  },
};
</script>

<style scoped lang="scss">
.mainContainer {
  display: flex;
  flex-direction: column;
  > div {
    display: flex;
    flex-direction: row;
    margin-bottom: 10px;
    align-items: center;
    > span {
      width: fit-content;
      margin-right: 10px;
      color: grey;
      > i {
        color: grey;
      }
    }
    > div {
      flex: 1;
    }
    .upQrCode {
      width: 50%;
    }

  }
  > div:last-child {
    margin-top: 10px;
    .sure {
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>
