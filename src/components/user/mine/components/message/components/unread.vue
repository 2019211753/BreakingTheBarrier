<template>
  <div class="ui segment">
    <div class="ui feed">
      <el-empty
        :image-size="100"
        v-if="
          !unLookedLikes.length && !unLookedComments.length && loading == false
        "
        description="暂无消息"
      ></el-empty>
      <div
        class="event"
        v-if="unLookedComments"
        v-for="item in unLookedComments"
      >
        <div class="label">
          <img :src="item.avatar" alt=""/>
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a>
            在 <a class="user"> {{ getInnerText(item.parentContent) }} </a> 中评论了你：
            <a href="" @click="readComment(item.id)"
            >
              <router-link
                v-if="item.parentType == '问题'"
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: {
                    articleId: item.parentId,
                    postUserId: item.parentUserId,
                  },
                }"
              >
                {{ getInnerText(item.content) }}
              </router-link
              >
              <router-link
                v-else
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.content) }}
              </router-link
              >
            </a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
      <div class="event" v-if="unLookedLikes" v-for="item in unLookedLikes">
        <div class="label">
          <img :src="item.avatar" alt=""/>
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 点赞了你的{{
              item.parentType
            }}：
            <a href="" @click="readLike(item.id)"
            >
              <router-link
                v-if="
                  item.parentType == '博客' || item.parentType == '博客的评论'
                "
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.parentContent) }}
              </router-link
              >
              <router-link
                v-else
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: { articleId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.parentContent) }}
              </router-link
              >
            </a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
      >
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
      </template>
    </el-skeleton>
  </div>
</template>

<script>

export default {
  name: "unread",
  data() {
    return {loading: true, unLookedComments: [], unLookedLikes: []};
  },
  created() {
    var that = this;
    that.$api.personalMessage.getAllMessage()
      .then(function (response) {
        that.loading = false;
        that.unLookedComments = response.data.data.unLookedComments;
        that.unLookedLikes = response.data.data.unLookedLikes;

      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    getInnerText(content) {
      var oDiv = document.createElement("div");
      oDiv.innerHTML = content;

      return oDiv.innerText;
    },
    readLike(id) {
      var that = this;
      that.$api.personalMessage.readLike(id)
        .then(function (response) {

        })
        .catch(function (error) {
          console.log(error);
        });
    },
    readComment(id) {
      var that = this;
      that.$api.personalMessage.readComment(id)
        .then(function (response) {

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
