<template>
  <div>
    <el-skeleton :loading="loading" animated :count="10">
      <template slot="template">
        <el-container style="margin-top: 20px">
          <div class="ui segment" style="width: 100%">
            <el-row :gutter="24">
              <el-col :span="3"
              >
                <el-skeleton-item variant="text" style="margin-top: 10px"
                />
              </el-col>
              <el-col :span="16">
                <div class="ui large feed">
                  <div class="event">
                    <div class="label">
                      <el-skeleton-item variant="circle image"/>
                    </div>
                    <div class="content">
                      <div class="summary">
                        <el-skeleton-item variant="text"/>
                      </div>
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col :span="5"
              >
                <el-skeleton-item variant="text" style="margin-top: 10px"
                />
              </el-col>
            </el-row>
          </div>
        </el-container>
      </template>
    </el-skeleton>
    <el-container
      v-for="(item, index) in userList"
      :key="index"
      style="margin-top: 20px"
    >
      <div class="ui segment" style="width: 100%">
        <el-row :gutter="24">

          <el-col :span="16">
            <div class="ui large feed">
              <div class="event">
                <div class="label" style="width: 40px;height: 40px">
                  <div style="width: 40px;height: 40px;margin: 0 auto"><img
                    style="height: 100%;width: 100%"
                    class="ui tiny centered circular image"
                    :src="item.avatar"
                    alt="avatar"
                  /></div>
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
          >
            <div class="ui tiny green statistic">
              <div class="value">
                {{ item.donation }}
              </div>
              <div class="label">贡献</div>
            </div>
          </el-col
          >
          <el-col :span="3"
          >
            <div v-if="index+1===1" class="ui red tiny  statistic">
              <div class="value">
                {{ index + 1 }}
              </div>
              <div class="label">排名</div>
            </div>
            <div v-else-if="index+1===2" class="ui blue tiny  statistic">
              <div class="value">
                {{ index + 1 }}
              </div>
              <div class="label">排名</div>
            </div>
            <div v-else-if="index+1===3" class="ui yellow tiny  statistic">
              <div class="value">
                {{ index + 1 }}
              </div>
              <div class="label">排名</div>
            </div>
            <div v-else class="ui tiny  statistic">
              <div class="value">
                {{ index + 1 }}
              </div>
              <div class="label">排名</div>
            </div>
          </el-col
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
    return {loading: true, userList: []};
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
        .then(function (response) {
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped lang="scss">
div.content > div.summary > div.date {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 21vw;
}

@media screen and (max-width: 580px){
  div.content > div.summary > div.date {
    width: 44vw;
  }
}
@media screen and (max-width: 370px){
  div.content > div.summary > div.date {
    width: 37vw;
  }
}
</style>
