<template>
  <div>
    <br />
    <div class="ui four stackable cards">
      <div class="card" v-for="item in favoriteList">
        <div class="ui slide masked reveal image">
          <img src="../../../../../assets/bg.jpg" class="visible content" />
          <div class="hidden content">
            <br />
            <br />
            <div class="first ui icon button" @click="editFavorite(item.id)">
              <i class="edit icon"></i>
            </div>
            <div class="second ui icon button" @click="deleteFavorite(item.id)">
              <i class="trash icon"></i>
            </div>
          </div>
        </div>
        <div class="content">
          <a class="header"
            ><router-link
              :to="{
                path: '/helloWorld/mine/collections/favorite',
                query: { favoriteId: item.id },
              }"
              >{{ item.title }}</router-link
            ></a
          >
        </div>
        <!--  <div class="extra content">
          {{ item.createTime }}
        </div> -->
      </div>
      <div class="card">
        <!-- <div class="image"><img src="../../../../../assets/bg.jpg" /></div> -->
        <div class="ui button" style="height: 100%" @click="createFavorites()">
          创建收藏夹
        </div>
      </div>
    </div>
    <br />
    <div class="ui basic modal">
      <div class="ui icon header">
        <i class="archive icon"></i>
        创建收藏夹
        <br />
        <br />
        <div class="ui labeled input">
          <div class="ui label">名称</div>
          <input type="text" placeholder="" v-model="fileName" />
        </div>
      </div>
      <div class="actions">
        <div class="ui toggle checkbox">
          <input type="checkbox" name="public" />
          <label>是否公开</label>
        </div>
        <div class="ui green ok inverted button" @click="sure()">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "collections",
  data() {
    return { favoriteList: [], fileName: "" };
  },
  created() {
    var that = this;
    axios
      .get("/customer/favorites")
      .then(function (response) {
        that.favoriteList = response.data.data.favorites;
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    createFavorites() {
      $(".ui.basic.modal").modal("show");
    },
    sure() {
      var that = this;
      axios
        .post("/customer/favorite/create", { title: that.fileName, open: true })
        .then(function (response) {
          console.log(response.data);
          that.favoriteList.push(response.data.data.favorite);
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    editeFavorite(id) {},
    deleteFavorite(id) {
      var that = this;
      axios
        .post("/customer/favorite/" + id + "/delete")
        .then(function (response) {
          console.log(response.data);
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
.cards {
  border: none;
}
.first {
  margin-left: 27px;
}
</style>
