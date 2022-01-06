<template>
  <div>
    <div class="ui segment">
      <div class="ui large feed">
        <div class="event">
          <div class="label">
            <img :src="'data:image/jpg;base64,' + template.avatar" alt=""/>
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
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "adminArticleContent",
  data() {
    return {
      template: "",
      articleContent: "",
      nowUser: "",
    };
  },
  created() {
    var that = this;
    axios
      .get("/question/" + sessionStorage.getItem("articleId"))
      .then(function (response) {
        that.template = response.data.data.template;
        that.articleContent = that.template.details;
        console.log(that.template);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
h3:nth-child(2) {
  text-align: center;
}
</style>
