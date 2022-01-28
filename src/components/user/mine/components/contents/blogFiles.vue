<template>
  <div>
    <div class="ui segment" v-if="loading == false && count == 0">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <el-skeleton :loading="loading" v-if="loading == true" animated :count="5">
      <template slot="template"
        ><div class="ui segment">
          <div class="ui large feed">
            <div class="event">
              <div class="label">
                <el-skeleton-item
                  variant="circle image"
                  style="margin-top: 10px"
                />
              </div>
              <div class="content">
                <div class="summary">
                  <el-skeleton-item variant="text" /><el-skeleton-item
                    variant="text"
                  />
                </div>
                <el-skeleton-item variant="text" /><el-skeleton-item
                  variant="text"
                /><el-skeleton-item variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
    <div class="ui segment" v-for="(item, index) in contentList[0]">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 40px"
            :src="$store.state.me.avatar"
        /></el-aside>
        <el-main>
          <h4 style="margin-top: -20px">{{ $store.state.me.nickname }}</h4>
          <p
            style="margin-top: -10px; color: grey; font-size: 13px"
            v-if="$store.state.me.personalSignature"
          >
            {{ $store.state.me.personalSignature }}
          </p>
          <p style="margin-top: -10px; color: grey; font-size: 13px" v-else>
            暂无签名
          </p>
        </el-main>
      </el-container>
      <el-row :gutter="20" style="margin-top: -10px">
        <el-col :span="20"
          ><h4>
            <a>
              <router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.id, postUserId: $store.state.me.id },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h4></el-col
        >
      </el-row>
      <el-row :gutter="20" style="margin-top: 5px">
        <el-col :span="8"><img class="ui rounded fluid image" /></el-col>
        <el-col :span="16"
          ><h5 style="color: grey">
            {{ item.description }}
          </h5></el-col
        >
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "blogFiles",
  data() {
    return {
      loading: true,
      contentList: [],
      count: 0,
    };
  },

  created() {
    var that = this;
    that.$api.personalArchive
      .archiveBlog()
      .then(function (response) {
        that.loading = false;
        that.count = response.data.data.count;
        for (var year = 2022; year <= 2025; year++) {
          for (var month = 1; month <= 12; month++) {
            if (response.data.data.archiveMap[year][month]) {
              that.contentList.push(response.data.data.archiveMap[year][month]);
            }
          }
        }
      })
      .catch(function (error) {
        console.log(error);
      });
    window.addEventListener("setItem", (e) => {
      if (e.key == "avatar") {
        that.avatar = e.newValue;
      }
    });
  },
};
</script>

<style scoped>
.framework {
  margin-top: 20px;
  height: 200px;
}

.frameworkBody {
  width: 90%;
  margin: auto;
}

.title {
  margin-top: 0px;
}

.ui.label {
  margin-top: 5px;
}

img {
  height: 120px;
}

.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
