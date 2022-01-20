<template>
  <div class="ui segment">
    <el-skeleton :loading="loading" v-if="loading == true" animated :count="5">
      <template slot="template"
        ><el-skeleton-item
          variant="text"
          style="height: 50px; margin-top: 15px"
        />
      </template>
    </el-skeleton>
    <el-container v-for="(item, index) in favoriteList" :key="index">
      <el-aside width="70px"><i class="huge yellow folder icon"></i></el-aside>
      <el-main
        ><el-row :gutter="20">
          <el-col :span="19">
            <h4>
              <a class="header"
                ><router-link
                  :to="{
                    path: '/BreakingTheBarrier/visitor/collections/favorite',
                    query: {
                      userId: userId,
                      favoriteId: item.id,
                    },
                  }"
                  >{{ item.title }}</router-link
                ></a
              >
            </h4></el-col
          >
          <el-col :span="5"></el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>

export default {
  name: "visitorCollections",

  data() {
    return {
      loading: true,
      userId: this.$route.query.userId,
      favoriteList: [],
    };
  },
  created() {
    var that = this;
    that.$api.userSocial.getOtherFile(that.$route.query.userId)
      .then(function (response) {
        console.log(response.data);
        /* that.loading = false; */
        that.favoriteList = response.data.data.favorites;
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
