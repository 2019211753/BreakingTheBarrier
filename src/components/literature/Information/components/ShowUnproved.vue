<template>
  <div v-if="flag">
      <strong>{{content}}</strong>
      <span style="display: none">{{id}}</span>
      <button class="ui button" @click="pass">通过</button>
  </div>
</template>

<script>
  import axios from "axios";
  import $ from "jquery";

  export default {
    name: "ShowUnproved",
    props: {
      content: String,
      id: Number
    },
    data() {
      return {
        flag: 1
      }
    },
    methods: {
      pass(event) {
        // console.log(event.target);
        let domTemp =event.target
        let entryId = $(domTemp).prev('span').text()
        console.log(entryId);
        axios.post('/infoEntry/' + entryId + '/approve', ).then(res => {
          console.log(res);
          if(res.code = 200) {
            this.flag = 0
          }
          else
            alert('审核失败')
        }).catch(err => {
          console.log(err);
          alert(err)
        })
      }
    }
  }
</script>

<style scoped>

</style>
