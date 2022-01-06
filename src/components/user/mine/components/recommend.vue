<template>
  <div class="user ui segment">
    <div class="ui vertical fluid buttons">
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/information"> 个人资料 </router-link>
      </div>
      <div class="ui button" style="background-color: white">浏览历史</div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/message/read"> 我的消息 </router-link>
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
      <div class="ui button" style="background-color: white">我的点赞</div>
      <div class="ui button" style="background-color: white">
        <router-link to="/helloWorld/mine/contents/questionFiles">
          我的内容
        </router-link>
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
