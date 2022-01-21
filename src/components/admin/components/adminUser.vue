<template>
  <div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." v-model="searchContent"/>
      <i class="inverted circular search link icon" @click="search()"></i>
    </div>
    <!--    <div class="ui segment" v-if="loading == true" style="height: 590px"></div>-->
    <div
      class="ui segment"
      style="height: 350px"
      v-if="!nowUser && loading == false"
    >
      <el-empty :image-size="150" description="暂无结果"></el-empty>
    </div>
    <div class="ui segment" v-if="nowUser">
      <el-row :gutter="24">
        <el-col :span="4"
        ><h4>id：{{ nowUser.id }}</h4></el-col
        ><!-- <el-col :span="10"><h4>用户名：{{ nowUser.nickname }}</h4></el-col> -->
        <el-col :span="10"
        ><h4>昵称：{{ nowUser.nickname }}</h4></el-col
        >
        <el-col :span="10"
        ><h4>注册时间：{{ nowUser.registerTime }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="5"
        ><h4>
          头像：<img
          class="ui mini right floated circular image"
          :src=nowUser.avatar
          alt=""
        /></h4
        ></el-col>
        <el-col :span="5"
        ><h4>管理员：{{ nowUser.admin }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>是否能发言：{{ nowUser.canSpeak }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>贡献值：{{ nowUser.donation }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="24"
        ><h4>个性签名：{{ nowUser.personalSignature }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="10"
        ><h4>学院：{{ nowUser.academy }}</h4></el-col
        >
        <el-col :span="10"
        ><h4>专业：{{ nowUser.major }}</h4></el-col
        >
        <el-col :span="4"
        ><h4>性别：{{ nowUser.sex }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="7"
        ><h4>QQ：{{ nowUser.qqId }}</h4></el-col
        >
        <el-col :span="7"
        ><h4>微信：{{ nowUser.wechatId }}</h4></el-col
        >
        <el-col :span="10"
        ><h4>邮箱：{{ nowUser.email }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="6"
        ><h4>粉丝：{{ nowUser.followedUserNum }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>关注：{{ nowUser.followingUserNum }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>privacyType：{{ nowUser.privacyType }}</h4></el-col
        >
        <el-col :span="6"
        ><h4>availableNum：{{ nowUser.availableNum }}</h4></el-col
        >
      </el-row>
      <br/>
      <el-row :gutter="24">
        <el-col :span="12"
        ><h4>joinedTeams：{{ nowUser.joinedTeams }}</h4></el-col
        >
        <el-col :span="12"
        ><h4>leadingTeams：{{ nowUser.leadingTeams }}</h4></el-col
        >
      </el-row>
      <div class="ui divider"></div>
      <button
        :class="nowUser.canSpeak == true ? Y : N"
        @click="userCanSpeak(nowUser.id)"
      >
        <i class="microphone slash icon"></i>
      </button>
      <button
        class="ui disabled blue icon button"
        @click="deleteUser(nowUser.id)"
      >
        <i class="trash icon"></i>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "adminUser",
  data() {
    return {
      loading: false,
      searchContent: "",
      nowUser: "",
      Y: "ui right floated icon button",
      N: "ui blue right floated icon button",
    };
  },
  methods: {
    search() {
      var that = this;
      if (that.searchContent) {
        that.loading=true;
        var data = {nickname: that.searchContent};
        that.$api.adminUser.serchUser(data)
          .then(function (response) {
            console.log(response.data);
            that.nowUser = response.data.data.user;
            that.loading = false;
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
      that.$api.adminUser.controlSpeak(id)
        .then(function (response) {
          console.log(response.data);
          that.nowUser.canSpeak = response.data.data.canSpeak;
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
/* h4 {
  color: grey;
} */
</style>
