<template>
  <div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." v-model="searchContent" />
      <i class="inverted circular search link icon" @click="search()"></i>
    </div>
    <el-container v-if="nowUser">
      <el-aside width="200px"
        ><div class="ui large feed">
          <br />
          <div class="event">
            <div class="label">
              <img src="../../../assets/avatar.jpg" />
            </div>
            <div class="content" style="margin-top: 3px">
              <div class="summary">
                <a>{{ nowUser.nickname }}</a>
              </div>
              <div class="date" style="margin-top: 0px">
                {{ nowUser.personalSignature }}
              </div>
              <br />
              <!-- <div >
                <button class="ui circular facebook icon button">
                  <i class="facebook icon"></i>
                </button>
                <button class="ui circular twitter icon button">
                  <i class="twitter icon"></i>
                </button>
                <button class="ui circular linkedin icon button">
                  <i class="linkedin icon"></i>
                </button>
                <button class="ui circular google plus icon button">
                  <i class="google plus icon"></i>
                </button>
              </div> -->
              <!--  <div class="ui icon buttons">
                <button class="ui button">
                  <i class="align left icon"></i>
                </button>
                <button class="ui button">
                  <i class="align center icon"></i>
                </button>
                <button class="ui button">
                  <i class="align right icon"></i>
                </button>
                <button class="ui button">
                  <i class="align justify icon"></i>
                </button>
              </div> -->
            </div>
          </div>
        </div></el-aside
      >
      <el-main
        >
        <div class="ui tiny statistics">
          <div class="statistic">
            <div class="value">{{ nowUser.id }}</div>
            <div class="label">id</div>
          </div>
          <div class="statistic" v-if="nowUser.sex">
            <div class="value">{{ nowUser.sex }}</div>
            <div class="label">性别</div>
          </div>
          <div class="statistic">
            <div class="value">{{ nowUser.canSpeak }}</div>
            <div class="label">状态</div>
          </div>
          <div class="statistic">
            <div class="value">{{ nowUser.donation }}</div>
            <div class="label">贡献度</div>
          </div>

          <div class="statistic" v-if="nowUser.academy">
            <div class="value">{{ nowUser.academy }}</div>
            <div class="label">学院</div>
          </div>
          <div class="statistic" v-if="nowUser.major">
            <div class="value">{{ nowUser.major }}</div>
            <div class="label">专业</div>
          </div>
          <div class="statistic">
            <div class="value">{{ nowUser.followingUserNum }}</div>
            <div class="label">关注</div>
          </div>
          <div class="statistic">
            <div class="value">{{ nowUser.followedUserNum }}</div>
            <div class="label">粉丝</div>
          </div>
          <div class="statistic" v-if="nowUser.QQ">
            <div class="value">{{ nowUser.QQ }}</div>
            <div class="label">QQ</div>
          </div>
          <div class="statistic" v-if="nowUser.wechatId">
            <div class="value">{{ nowUser.wechatId }}</div>
            <div class="label">微信</div>
          </div>
          <div class="statistic" v-if="nowUser.email">
            <div class="value">{{ nowUser.email }}</div>
            <div class="label">邮箱</div>
          </div>
          <div class="statistic">
            <div class="value">{{ nowUserRegisterTime }}</div>
            <div class="label">注册时间</div>
          </div>
        </div></el-main
      >
      <el-aside width="100px">
        <div class="ui large feed">
          <br />
          <div class="event">
            <button
              class="ui circular icon button"
              @click="userCanSpeak(nowUser.id)"
            >
              <i class="microphone slash icon"></i>
            </button>
            <button
              class="ui disabled circular blue icon button"
              @click="deleteUser(nowUser.id)"
            >
              <i class="trash icon"></i>
            </button>
          </div></div
      ></el-aside>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "adminUser",
  data() {
    return { searchContent: "", nowUser: "", nowUserRegisterTime: "" };
  },
  methods: {
    search() {
      var that = this;
      if (that.searchContent) {
        axios
          .post("/admin/searchUser", { nickname: that.searchContent })
          .then(function (response) {
            console.log(response.data);
            that.nowUser = response.data.data.user;
            that.nowUserRegisterTime =
              response.data.data.user.registerTime.split("T")[0];
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message({
          message: "请输入查找内容",
          type: "warning",
        });
      }
    },
    userCanSpeak(id) {
      var that = this;
      axios
        .get("/admin/controlSpeak/" + id)
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    deleteUser() {
      var that = this;
      axios
        .get("/admin/searchUser")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
