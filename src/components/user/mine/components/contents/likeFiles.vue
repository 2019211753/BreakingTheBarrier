<template>
  <div class="ui segment">
    <div class="ui feed">
      <div class="event">
        <div class="label">
          <img
            :src="'data:image/jpg;base64,' + $store.state.me.avatar"
            alt=""
          />
        </div>
        <div class="content">
          <div class="summary">
            您已添加<a>Jenny Hess</a>至您的 <a>同事</a> 分组.
            <div class="date">3 天前</div>
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
import axios from "axios";
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
    axios
      .get("/customer/archiveLike")
      .then(function (response) {
        console.log(response.data);
        that.contentList = response.data.data.postLikes;
        that.loading = false;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
</style>
