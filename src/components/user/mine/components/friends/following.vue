<template>
  <div class="ui segment">
    <div class="ui large feed">
      <el-empty
        :image-size="100"
        v-if="!list.length && loading == false"
        description="暂无关注者"
      ></el-empty>
      <div class="event" v-for="item in list">
        <div class="label">
          <img :src="item.avatar" alt=""/>
        </div>
        <div class="content">
          <div class="summary">
            <a @click="turnToOthers(item.id)"> {{ item.nickname }} </a>
            <div class="date">{{ item.personalSignature }}</div>
            <div class="buttons">
              <button class="ui mini disabled blue icon button">
                <i class="envelope icon"></i>
              </button>
              <button
                class="ui mini red icon button"
                @click="unfollow(item.id)"
              >
                <i class="heart icon"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
      >
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <el-skeleton-item variant="circle image"/>
            </div>
            <div class="content">
              <div class="summary">
                <el-skeleton-item variant="text"/>
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>


<script>
export default {
  name: "following",
  data() {
    return {loading: true, list: []};
  },
  created() {
    var that = this;
    that.$api.personalFollow
      .getFollowInfo()
      .then(function (response) {
        that.loading = false;
        console.log(response)
        that.list = response.data.data.followingUsers;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    unfollow(id) {
      var that = this;
      that.$api.userSocial
        .followOther(id)
        .then(function (response) {
          console.log(response)
          /* for (var i = 0; i < that.list.length; i++) {
            if (that.list[i].id == id) {
              that.list.splice(that.list[i], 1);
              break;
            }
          } */
          that.$store.commit(
            "getMyFollowingNum",
            response.data.data.myFollowingNum
          );
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    turnToOthers(id) {
      var that = this;
      that.$api.userSocial
        .getOtherFile(id)
        .then(function (response) {
          that.$store.commit("getOthersFile", response.data.data);
          that.$router.push({
            path: "/BreakingTheBarrier/visitor/questions",
            query: {userId: id},
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
