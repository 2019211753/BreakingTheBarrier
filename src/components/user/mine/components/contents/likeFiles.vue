<template>
  <div class="ui segment">
    <div class="ui feed">
      <div class="event" v-for="item in contentList">
        <div class="label">
          <img :src="$store.state.me.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            赞了{{ item.parentType }}：
            <a>
              <router-link to=""></router-link>
              {{ getInnerText(item.parentContent) }}</a>
            <div class="date">{{ item.createTime }}</div>
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
      </template> </el-skeleton
    ><el-empty
      :image-size="100"
      v-if="!contentList.length && loading == false"
      description="还没有赞过任何内容"
    ></el-empty>
  </div>
</template>

<script>

export default {
  name: "likeFiles",
  data() {
    return {
      loading: true,
      contentList: [],
    };
  },
  created() {
    var that = this;
    that.$api.personalArchive.archiveLike()

      .then(function (response) {
        console.log(response.data);
        that.contentList = response.data.data.postLikes;
        that.loading = false;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    getInnerText(content) {
      var oDiv = document.createElement("div");
      oDiv.innerHTML = content;
      console.log(oDiv.innerHTML);
      return oDiv.innerText;
    },
  },
};
</script>

<style scoped>
</style>
