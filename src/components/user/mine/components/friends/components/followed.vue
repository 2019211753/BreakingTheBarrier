<template>
  <div class="ui segment">
    <div class="ui large feed">
      <el-empty
        :image-size="100"
        v-if="!list.length && loading == false"
        description="暂无粉丝"
      ></el-empty>
      <div class="event" v-for="item in list">
        <div class="label">
          <img :src=" item.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            <a
              ><router-link
                :to="{
                  path: '/helloWorld/visitor',
                  query: { userId0: item.id },
                }"
                >{{ item.nickname }}
              </router-link></a
            >
            <div class="date">{{ item.personalSignature }}</div>
            <!-- <div class="buttons">
              <button class="ui disabled circular blue icon button">
                <i class="envelope icon"></i>
              </button>
              <button
                class="ui circular blue icon button"
                @click="unfollow(item.id)"
              >
                <i class="heart icon"></i>
              </button>
            </div> -->
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
        ><div class="ui large feed">
          <div class="event">
            <div class="label">
              <el-skeleton-item variant="circle image" />
            </div>
            <div class="content">
              <div class="summary">
                <el-skeleton-item variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "followed",
  data() {
    return { loading: true, list: [] };
  },
  created() {
    var that = this;
    axios
      .get("/customer/followInfo")
      .then(function (response) {
        that.loading = false;
        console.log(response.data);
        that.list = response.data.data.followedUsers;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    unfollow(id) {
      var that = this;
      axios
        .get("/follow/" + id)
        .then(function (response) {
          console.log(response.data);
          for (var i = 0; i < that.list.length; i++) {
            if (that.list[i].id == id) {
              that.list.pop(that.list[i]);
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.ui.feed {
  margin-top: 10px;
}
.event {
  margin-top: 10px;
}
.buttons {
  float: right;
}
</style>
