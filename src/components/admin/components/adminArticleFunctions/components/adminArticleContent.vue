<template>
  <div class="ui basic segment">
    <div class="ui large feed">
      <div class="event">
        <div class="label">
          <img :src="template.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user">
              {{ template.nickname }}
            </a>
            <div
              v-if="template.solved == true"
              class="ui green top right attached label"
            >
              已解决
            </div>
            <div
              v-if="template.solved == false"
              class="ui red top right attached label"
            >
              未解决
            </div>
            <div class="date">{{ template.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
    <h3 class="title">{{ template.title }}</h3>
    <div class="ui divider"></div>
    <div v-html="articleContent">{{ articleContent }}</div>
  </div>
</template>

<script>
export default {
  name: "adminArticleContent",
  data() {
    return {
      id: this.$store.state.articleId,
      template: "",
      articleContent: "",
      nowUser: "",
    };
  },
  methods: {
    getArticle() {
      var that = this;
      that.$api.userArticle
        .showQuestion(that.$store.state.articleId)
        .then(function (response) {
          that.template = response.data.data.template;
          that.articleContent = that.template.details;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  created() {
    var that = this;
    that.getArticle();
  },
  watch: { id: "getArticle" },
};
</script>

<style scoped>
h3:nth-child(2) {
  text-align: center;
}
</style>
