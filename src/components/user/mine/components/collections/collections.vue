<template>
  <div class="ui segment">
    <el-container v-for="(item, index) in favoriteList" :key="index">
      <el-aside width="70px"><i class="huge yellow folder icon"></i></el-aside>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="19">
            <h4>
              <a class="header">
                <router-link
                  :to="{
                    path: '/BreakingTheBarrier/mine/collections/favorite',
                    query: { favoriteId: item.id },
                  }"
                  >{{ item.title }}
                </router-link> </a
              ><a
                v-if="item.open == false"
                class="ui small blue label"
                style="margin-left: 20px"
                >私密</a
              >
            </h4>
          </el-col>
          <el-col :span="5">
            <div
              class="ui mini icon button"
              style="background-color: white"
              @click="editFavorite(item.id, item.title, item.open)"
            >
              <i class="ui edit icon"></i>
            </div>
            <div
              class="ui mini icon button"
              style="background-color: white"
              @click="deleteFavorite(item.id)"
            >
              <i class="ui trash icon"></i>
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <el-container v-if="loading == false">
      <el-aside width="70px"><i class="huge yellow folder icon"></i></el-aside>
      <el-main>
        <div
          class="ui teal icon button"
          style="margin-top: -10px"
          @click="createFavorites()"
        >
          <i class="plus icon"></i>
        </div>
      </el-main>
    </el-container>
    <el-dialog width="400px" :visible.sync="dialogFormVisible1"
      ><div class="ui icon header">
        <i class="archive icon"></i>
        创建收藏夹
        <br />
        <br />
        <div class="ui labeled input">
          <div class="ui label">收藏夹名称</div>
          <input type="text" placeholder="" v-model="fileName" />
        </div>
      </div>
      <div class="actions" style="width: 172px; margin: auto">
        <div :class="privateButton" @click="setPrivate()">私密</div>
        <div class="ui teal button" @click="sureCreateFavorites()">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div></el-dialog
    >
    <el-dialog width="400px" :visible.sync="dialogFormVisible2"
      ><div class="ui icon header">
        <i class="archive icon"></i>
        修改信息
        <br />
        <br />
        <div class="ui labeled input">
          <div class="ui label">收藏夹名称</div>
          <input
            type="text"
            :placeholder="nowFavoriteName"
            v-model="fileName"
          />
        </div>
      </div>
      <div class="actions" style="width: 172px; margin: auto">
        <div
          :class="nowPrivate == false ? publicButton : privateButton"
          @click="editPrivate()"
        >
          私密
        </div>
        <div class="ui teal button" @click="sureEditFavorites()">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div></el-dialog
    >
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template">
        <el-skeleton-item
          variant="text"
          style="height: 50px; margin-top: 15px"
        />
      </template>
    </el-skeleton>
  </div>
</template>

<script>
export default {
  name: "collections",
  data() {
    return {
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      open: false,
      isPrivate: false,
      privateButton: "ui button",
      loading: true,
      favoriteList: [],
      fileName: "",
      publicButton: "ui blue button", //两个颜色是反过来的
      nowFavoriteId: "",
      nowFavoriteName: "",
      nowPrivate: false,
    };
  },
  created() {
    var that = this;
    that.$api.personalFavorite
      .getFavorites()
      .then(function (response) {
        that.loading = false;
        that.favoriteList = response.data.data.favorites;

      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    createFavorites() {
      var that = this;
      that.dialogFormVisible1 = true;
    },
    editFavorite(id, name, open) {
      var that = this;
      that.nowFavoriteId = id;
      that.nowFavoriteName = name;
      that.nowPrivate = open;
      that.dialogFormVisible2 = true;
    },
    editPrivate() {
      var that = this;
      that.nowPrivate = !that.nowPrivate;
    },
    setPrivate() {
      var that = this;
      if (that.isPrivate == false) {
        that.privateButton = "ui blue button";
        that.isPrivate = true;
      } else {
        that.privateButton = "ui button";
        that.isPrivate = false;
      }
    },
    sureCreateFavorites() {
      var that = this;
      var data = {
        title: that.fileName,
        open: !that.isPrivate,
      };
      that.$api.personalFavorite
        .createFavorite(data)
        .then(function (response) {

          that.favoriteList.push(response.data.data.favorite);
          that.$message({
            message: response.data.msg,
            type: "success",
          });
          that.privateButton = "ui button";
          that.isPrivate = false;
          that.fileName = "";
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    sureEditFavorites() {
      var that = this;
      var data = {
        title: that.fileName,
        open: that.nowPrivate,
        id: that.nowFavoriteId,
      };
      that.$api.personalFavorite
        .editFavorite(data)
        .then(function (response) {

          that.$message({
            message: "修改成功",
            type: "success",
          });
          for (var i = 0; i < that.favoriteList.length; i++) {
            if (that.favoriteList[i].id == response.data.data.favorite.id) {
              that.favoriteList[i].open = response.data.data.favorite.open;
              that.favoriteList[i].title = response.data.data.favorite.title;
              break;
            }
          }
          that.fileName = "";
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    deleteFavorite(id) {
      var that = this;
      that.$api.personalFavorite
        .deleteFavorite(id)
        .then(function (response) {

          for (var i = 0; i < that.favoriteList.length; i++) {
            if (that.favoriteList[i].id == id) {
              that.favoriteList.splice(i, 1);
              break;
            }
          }
          that.$message({
            message: response.data.msg,
            type: "success",
          });
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
