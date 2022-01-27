<template>
  <div class="ui basic segment">
    <div class="ui segment">
      <div class="frameworkBody">
        <h3 class="ui left floated header">热门话题</h3>
        <el-skeleton
          style="width: 240px"
          :loading="loading"
          animated
          :count="5"
        >
          <template slot="template"
            ><div class="ui feed">
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
        <div>
          <div class="ui feed">
            <div class="event" v-for="item in impactList">
              <div class="label">
                <img :src="item.avatar" alt="" />
              </div>
              <div class="content">
                <!-- <div class="date">{{ item.createTime }}</div> -->
                <div class="summary">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/BBS/articleContent',
                      query: {
                        articleId: item.id,
                        postUserId: item.postUserId,
                      },
                    }"
                    >{{ item.title }}</router-link
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "hotArticle",
  data() {
    return { loading: true, impactList: [] };
  },
  created() {
    var that = this;
    that.$api.userArticle
      .getQuestions(0)
      .then(function (response) {
        that.loading = false;
        that.impactList = response.data.data.impacts;

      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.frameworkBody {
  width: 90%;
  /* height: 200px; */
  margin: auto;
}
</style>
