<template>
  <div>
    <!--搜索文章-->
    <div class="ui fluid input">
      <input type="text" placeholder="搜索..." v-model="searchContent"/>
      <router-link
        :to="{
                path: '/BreakingTheBarrier/BBS/searchArticleResult',
                query: { searchContent: this.searchContent },
              }"
      >
        <button class="ui teal button" v-if="this.searchContent">
          搜索
        </button>
      </router-link>
    </div>

    <!--发布文章-->
    <div class="ui teal fluid button" @click="turnToAddArticle()">
      发布内容
    </div>

  </div>
</template>

<script>
export default {
  name: "temp"
}
</script>

<style scoped>

</style>
