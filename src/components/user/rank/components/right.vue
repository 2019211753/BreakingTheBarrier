<template>
  <div class="rankRight">
    <div class="ui segment">
      <h3>排名规则</h3>
      <h4 style="color:RGB(0,181,173)">贡献值采用积分制，每发布博客、问题、有效评论或个人博客、问题、评论被点赞，收藏，精选或关注，个人贡献值均可加2</h4>
    </div>
    <button class="ui right labeled blue icon button">
      <i class="right arrow icon"></i>
      了解更多
    </button>
  </div>
</template>

<script>
export default {
  name: "right",
};
</script>

<style scoped lang="scss">
.rankRight {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  > button.button {
    display: flex;
    justify-content: center;
  }
}

</style>
