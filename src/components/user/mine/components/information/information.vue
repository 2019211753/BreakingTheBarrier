<template>
  <div>
    <div class="ui segment">
      <h3>基本资料</h3>
      <div class="ui divider"></div>
      <br />
      <el-row :gutter="24">
        <el-col :span="4"
          ><h4 style="margin-left: 10px; margin-top: 10px">用户昵称</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid input">
            <input
              type="text"
              :placeholder="$store.state.me.nickname"
              v-model="nickname"
            />
          </div>
        </el-col>
        <el-col :span="3"
          ><h4 style="margin-left: 15px; margin-top: 10px">性别</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid selection dropdown" style="width: 175px">
            <input type="hidden" name="gender" />
            <i class="dropdown icon"></i>
            <div class="default text" v-if="$store.state.me.sex">
              <div v-if="$store.state.me.sex == 'true'">男</div>
              <div v-if="$store.state.me.sex == 'false'">女</div>
            </div>
            <div class="default text" v-else>无</div>
            <div class="menu">
              <div class="item" @click="boy()">男</div>
              <div class="item" @click="girl()">女</div>
            </div>
          </div>
        </el-col>
      </el-row>
      <br /><el-row :gutter="24">
        <el-col :span="4"
          ><h4 style="margin-left: 10px; margin-top: 10px">修改密码</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid input">
            <input type="text" placeholder="长度为7至11" v-model="passWord" />
          </div>
        </el-col>
        <el-col :span="4"><h4 style="margin-top: 10px">确认密码</h4></el-col>
        <el-col :span="8">
          <div class="ui fluid input" style="margin-left: -26px; width: 173px">
            <input
              type="text"
              placeholder="再次输入确认"
              v-model="surePassWord"
            />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row :gutter="24">
        <el-col :span="4"
          ><h4 style="margin-left: 10px; margin-top: 10px">邮箱</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid input">
            <input
              type="text"
              :placeholder="$store.state.me.email"
              v-model="email"
            />
          </div>
        </el-col>
        <el-col :span="3"
          ><h4 style="margin-left: 15px; margin-top: 10px">QQ</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid input">
            <input
              type="text"
              style="margin-left: -3px"
              :placeholder="$store.state.me.qqId"
              v-model="QQ"
            />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row :gutter="20">
        <el-col :span="4"
          ><h4 style="margin-left: 10px; margin-top: 10px">学院</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid selection dropdown" style="width: 175px">
            <input type="hidden" name="gender" />
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
        </el-col>
        <el-col :span="3"
          ><h4 style="margin-left: 15px; margin-top: 10px">专业</h4></el-col
        >
        <el-col :span="8">
          <div class="ui fluid selection dropdown" style="width: 175px">
            <input type="hidden" name="gender" />
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
        </el-col>
      </el-row>
      <br />
      <el-row :gutter="24">
        <el-col :span="4"
          ><h4 style="margin-left: 10px; margin-top: 10px">签名</h4></el-col
        >
        <el-col :span="19">
          <div class="ui fluid input">
            <input
              type="text"
              :placeholder="$store.state.me.personalSignature"
              v-model="personalSignature"
            />
          </div>
        </el-col>
      </el-row>
      <br />
      <div style="margin-left: 83%" class="ui teal button" @click="sure()">
        确定
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
/* import eventBus from "../../../../eventBus"; */
import $ from "jquery";

/* var token = sessionStorage.getItem("token");
window.addEventListener("setItem", (e) => {
  console.log(e);
  token = e.newValue;
}); */
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "information",
  /* computed: {
    headers() {
      return {
        Authorization: sessionStorage.getItem("token"), // 直接从本地获取token就行
      };
    },
  }, */
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
    };
  },
  created() {
    this.academyList = this.$store.state.me.academies;
    console.log(this.academyList);
    /* eventBus.$on("pushMsg", (headbarMsg) => {
      this.academyList = headbarMsg;
      console.log(this.academyList);
    }); */
    $(function () {
      $(".ui.dropdown").dropdown();
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
          console.log(that.academyList[item]);
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
    sure() {
      var that = this;
      if (that.passWord === that.surePassWord) {
        var information = {
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
        axios
          .post("/customer/modifyAll", information)
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
                /* sessionStorage["avatar"] = response.data.data.user.avatar; */
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
            console.log(response.data);
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

<style scoped>
.leftSide {
  width: 60%;
  float: left;
}

.rightSide {
  width: 40%;
  height: 416px;
  float: left;
}

.framework {
  width: 80%;
  height: 220px;
  margin: auto;
}
</style>
