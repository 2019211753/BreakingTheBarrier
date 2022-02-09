<template>
  <div>
    <el-skeleton :loading="loading" animated :count="10">
      <template slot="template">
        <el-container style="margin-top: 20px">
          <div class="ui segment" style="width: 100%">
            <el-row :gutter="24">
              <el-col :span="3"
                ><el-skeleton-item variant="text" style="margin-top: 10px"
              /></el-col>
              <el-col :span="16">
                <div class="ui large feed">
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
              </el-col>
              <el-col :span="5"
                ><el-skeleton-item variant="text" style="margin-top: 10px"
              /></el-col>
            </el-row>
          </div>
        </el-container>
      </template>
    </el-skeleton>
    <el-container
      v-for="(item, index) in userList"
      :key="index"
      style="margin-top: 20px"
      ><div class="ui segment" style="width: 100%">
        <el-row :gutter="24">
          <el-col :span="3"
            ><h1>{{ index + 1 }}</h1></el-col
          >
          <el-col :span="16">
            <div class="ui large feed">
              <div class="event">
                <div class="label">
                  <img :src="item.avatar" />
                </div>
                <div class="content">
                  <div class="summary">
                    <a class="user">
                      {{ item.nickname }}
                    </a>
                    <div class="date">
                      {{ item.personalSignature }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :span="5"
            ><h1>{{ item.donation }}</h1></el-col
          >
        </el-row>
      </div>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "left",
  data() {
    return { loading: true, userList: [] };
  },
  created() {
    var that = this;
    that.$api.userRank
      .getRank()
      .then(function (response) {
        that.loading = false;
        that.userList = response.data.data.users;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    follow(id) {
      var that = this;
      axios
        .get("/follow/" + id)
        .then(function (response) {})
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
