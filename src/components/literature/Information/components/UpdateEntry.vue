<template>
  <div v-show="showUpdateFlag">
    <h2>更新词条</h2>
    <create :flag="0"
      @makeSureUpdate="makeSureUpdate">
      <span slot="subText">更新</span>
    </create>
  </div>
</template>

<script>
  import Create from "./Create";
  import axios from 'axios'
  export default {
    name: "UpdateEntry",
    updated() {
      console.log(this.showUpdateFlag);
    },
    props: {
      showUpdateFlag: {
        type: Boolean,
        default: 0
      }
    },
    methods: {
      makeSureUpdate() {
        axios.post('infoEntry/23/update',{
          'title': '',
          'newContent': '',
        })
      }
    },
    components: {
      Create
    }
  }
</script>

<style scoped>

</style>
