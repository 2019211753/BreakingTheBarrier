<template>
  <div>
    <div class="ui icon input">
      <div class="ui right labeled left icon input">
        <i class="clipboard icon"></i>
        <input type="text" placeholder="输入标题" v-model="title">
        <a class="ui tag label">
          标题
        </a>
      </div>
    </div>
    <div class="ui icon input">
      <div class="ui right labeled left icon input">
        <i class="clipboard icon"></i>
        <input type="text" placeholder="输入内容" v-model="newContent" @>
        <a class="ui tag label">
          内容
        </a>
      </div>
    </div>
    <button class="ui button" @click="btnClick">创建</button>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "Create",
    data() {
      return {
        res: '',
        title: '',
        newContent: ''
      }
    },
    methods: {
      btnClick() {
        this.$emit('create',this.title,this.newContent)
      }
    }
  }
</script>

<style scoped>

</style>
