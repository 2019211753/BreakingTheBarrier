<template>
  <div>
    <div class="item" v-for="item in tagList" :key="item.id">
      <i class="tag icon"></i>
      <div class="content">
        <div class="ui mini icon right floated button" @click="showEditTag()">
          <i class="ui edit icon"></i>
        </div>
        <div class="ui mini icon right floated button">
          <i class="ui trash icon"></i>
        </div>
        <div class="ui mini icon right floated button">
          <i class="ui plus icon"></i>
        </div>
        <div class="header">{{ item.name }}</div>
        <createTags
          v-if="item.childTags"
          :tagList="item.childTags"
        ></createTags>
        <div class="list"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "createTags",
  props: ["msg"],
  data() {
    return { tagList: "" };
  },
  mounted() {
    /* this.tagList = JSON.parse(sessionStorage.getItem("tagList")); */
  },
  methods: {
    showEditTag() {
      $(".modal").modal("show");
    },
  },
};
</script>

<style scoped>
</style>
