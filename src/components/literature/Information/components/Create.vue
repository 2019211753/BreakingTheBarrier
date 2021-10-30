<template>
  <button @click="createEntry">创建词条</button>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "Create",
    data() {
      return {
        res: ''
      }
    },
    methods: {
      createEntry() {
        axios.post('infoEntry/create', {
          params: {
            infoEntry: {
              a_infoEntry: '测试'
            }
          }
        }).then(res => {
          console.log(res);
        }).catch(err => {
          alert(err)
        })
      }
    }
  }
</script>

<style scoped>

</style>
