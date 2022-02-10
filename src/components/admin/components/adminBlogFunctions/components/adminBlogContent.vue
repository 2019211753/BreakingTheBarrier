<template>
  <div>
    <div class="ui basic segment">
      <div class="ui large feed">
        <div class="event">
          <div class="label">
            <img :src="template.avatar" alt=""/>
          </div>
          <div class="content">
            <div class="summary">
              <a class="user">
                {{ template.nickname }}
              </a>

              <div class="date">{{ template.createTime }}</div>
            </div>
          </div>
        </div>
      </div>
      <h3 class="title">{{ template.title }}</h3>
      <div class="ui divider"></div>
      <div v-html="blogContent">{{ blogContent }}</div>
    </div>
  </div>
</template>

<script>

export default {
  name: "adminBlogContent",
  data() {
    return {
      template: "",
      blogContent: "",
      nowUser: "",
    };
  }, methods: {
    getblog() {
      var that = this;
      that.$api.userArticle
        .showBlog(that.$store.state.blogId)
        .then(function (response) {
          that.template = response.data.data.template;
          that.blogContent = that.template.details;
          /*that.loading = false;*/
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  },
  created() {
    var that = this;
    that.getblog();
  },
};
</script>

<style scoped>
h3:nth-child(2) {
  text-align: center;
}
</style>
