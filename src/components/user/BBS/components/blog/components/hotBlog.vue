<template>
  <div class="ui basic segment" style="padding: 0">
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
            <div class="event" style="margin: 5px 0" v-for="item in impactList">
              <div class="label" style="height: 35px">
                <img :src="item.avatar" alt="" style="height: 100%" />
              </div>
              <div class="content">
                <div class="summary">
                  <router-link
                    :to="{
                      path: '/BreakingTheBarrier/BBS/blogContent',
                      query: {
                        blogId: item.id,
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
  name: "hotBlog",
  data() {
    return { loading: true, impactList: [] };
  },
  created() {
    var that = this;
    that.$api.userArticle
      .getBlogs(0)
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
  margin: auto;
}
</style>
