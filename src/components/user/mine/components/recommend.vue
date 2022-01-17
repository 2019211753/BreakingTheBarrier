<template>
  <div class="user ui segment">
    <div class="ui vertical fluid buttons">
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/information"> 个人资料 </router-link>
      </div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/message/unread">
          我的消息
        </router-link>
      </div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/collections"> 我的收藏 </router-link>
      </div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/friends/following">
          我的关注
        </router-link>
      </div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/friends/followed">
          粉丝列表
        </router-link>
      </div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/contents/questionFiles">
          我的内容
        </router-link>
      </div>
      <div
        class="ui button"
        v-if="$store.state.me.admin == true"
        style="background-color: white"
      >
        <router-link to="/helloWorld/admin/adminTag"> 后台管理 </router-link>
      </div>
      <div class="ui disabled button" style="background-color: white">
        浏览历史
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "recommend",
};
</script>

<style scoped>
.user {
  width: 90%;
  margin: auto;
  /* background-color: blue; */
}
</style>
