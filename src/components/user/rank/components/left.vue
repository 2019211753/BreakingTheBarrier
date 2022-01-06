<template>
  <!-- <div class="ui one cards">
    <div class="ui basic segment">
      <div class="content">
        <div class="header">{{ item.nickname }}</div>
        <div class="description">{{ item.personalSignature }}</div>
      </div>
      <div class="ui bottom attached button">
        <i class="add icon"></i>
        关注
      </div>
    </div>
  </div> -->
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
      key="index"
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
                  <img :src="'data:image/jpg;base64,' + item.avatar" />
                </div>
                <div class="content">
                  <div class="summary">
                    <a class="user"
                      ><font style="vertical-align: inherit"
                        ><font style="vertical-align: inherit">
                          {{ item.nickname }}
                        </font></font
                      ></a
                    >
                    <!-- <div class="date">
                      <font style="vertical-align: inherit"
                        ><font style="vertical-align: inherit">
                          1小时前
                        </font></font
                      >
                    </div> -->
                  </div>
                  <!-- <div class="meta">
                    <a class="like">
                      <i class="like icon"></i
                      ><font style="vertical-align: inherit"
                        ><font style="vertical-align: inherit">
                          4个赞
                        </font></font
                      ></a
                    >
                  </div> -->
                </div>
              </div>
            </div>
          </el-col>
          <el-col :span="5"
            ><h1>{{ item.donation }}</h1></el-col
          >
        </el-row>
      </div>
      <!-- <el-aside width="500px">
        <div class="ui small five statistics">
          <div class="statistic">
            <div class="value">5</div>
            <div class="label">内容</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.donation }}</div>
            <div class="label">贡献度</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.followedUserNum }}</div>
            <div class="label">粉丝</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.followingUserNum }}</div>
            <div class="label">关注</div>
          </div>
        </div>
      </el-aside>
      <el-main>
        <button
          style="margin-top: -10px"
          class="ui circular red icon button"
          @click="follow(item.id)"
        >
          <i class="heart icon"></i>
        </button>
      </el-main> -->
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "left",
  data() {
    return { loading: true, userList: [] };
  },
  created() {
    var that = this;
    axios
      .get("/rank")
      .then(function (response) {
        that.loading = false;
        console.log(response.data);
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
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
