<template>
  <button @click="updateEntry">更新词条</button>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "Update",
    entryId: '',
    methods: {
      updateEntry() {
        axios.post('infoEntry/' + this.entryId + 'update', {
          infoEntry: {
            a_infoEntry: '更新_测试'
          }
        })
      }
    }

  }
</script>

<style scoped>

</style>
