<template>
  <div class="ui basic segment">
    <div class="ui segment" v-for="(item, index) in contentList">
      <div class="ui middle aligned grid">
        <div class="ten wide column">
          <h3 class="ui header">
            <a href="">
              <router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: {
                    articleId: item.id,
                    postUserId: item.postUserId,
                  },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h3>
          <p class="m-text">{{ item.description }}</p>
          <div class="ui grid">
            <div class="eleven wide column">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <img :src="item.avatar" alt="" class="ui avatar image" />
                  <div class="content">
                    <a class="header">
                      {{ item.nickname }}
                    </a>
                  </div>
                </div>
              </div>
            </div>
            <div class="sixteen wide column" style="margin-top: -20px">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <i class="calendar icon"></i>
                  <span style="margin-left: 2px">{{ item.createTime }}</span>
                </div>
                <div class="item">
                  <i class="thumbs up icon"></i>
                  <span style="margin-left: 2px">{{ item.likesNum }}</span>
                </div>
                <div class="item">
                  <i class="comment icon"></i>
                  <span style="margin-left: 2px">{{ item.commentsNum }}</span>
                </div>
                <div class="item">
                  <i class="eye icon"></i>
                  <span style="margin-left: 2px">{{ item.view }}</span>
                </div>
              </div>
            </div>
            <div class="sixteen wide column" style="margin-top: -20px">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <a class="ui teal basic label" v-for="tags in item.tags">{{
                    tags.name
                  }}</a>
                  <a class="ui teal basic label" v-if="item.solved == true"
                    >已解决</a
                  >
                  <a class="ui red basic label" v-else>未解决</a>
                </div>
              </div>
            </div>
          </div>
          <div class="ui grid">
            <!-- <div class="right aligned five wide column">
              <a
                href="/types/2"
                class="ui teal basic label m-padded-tb-tiny m-text-thin"
                >JavaSE</a
              >
            </div> -->
          </div>
        </div>
        <div class="six wide column">
          <a target="_blank">
            <img
              src="https://picsum.photos/800/450/?random=5"
              alt=""
              class="ui rounded image"
            />
          </a>
        </div>
      </div>
    </div>
    <!-- <div class="ui segment"></div> -->
    <div class="ui segment" v-if="loading == false && !contentList.length">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <el-skeleton :loading="loading" animated :count="7">
      <template slot="template">
        <div class="ui segment">
          <div class="ui middle aligned grid">
            <div class="ten wide column">
              <el-skeleton-item variant="text" /><el-skeleton-item
                variant="text"
              /><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text"
              /><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text"
              /><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text"
              />
            </div>
            <div class="six wide column">
              <el-skeleton-item
                variant="image"
                style="height: 120px; border-radius: 5px"
              />
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
    <el-pagination
      class="el-pagination"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-count="pageSize"
      :hide-on-single-page="true"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "articleContents",
  data() {
    return { loading: true, contentList: [], pageSize: 0, cover: [] };
  },
  created() {
    var that = this;
    that.$api.userArticle
      .getQuestions(0)
      .then(function (response) {
        that.loading = false;
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    handleCurrentChange(val) {
      var that = this;
      var nowPage = val - 1;
      that.$api.userArticle
        .getQuestions(nowPage)
        .then(function (response) {
          that.contentList = response.data.data.pages.content;
          that.pageSize = response.data.data.pages.totalPages;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.framework {
  margin-top: 20px;
  height: 240px;
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

.el-pagination {
  margin-top: 20px;
  margin-left: 25%;
}

.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
