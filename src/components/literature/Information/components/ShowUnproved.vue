<template>
  <div v-if="flag">
    <h2>{{content.title}}</h2>
    <h4>当前内容</h4>
    <span>{{content.currentContent}}</span>
    <h4>更新内容</h4>
    <span>{{content.newContent}}</span>
    <span style="display: none">{{content.id}}</span>
    <button class="ui button" style="display: block" @click="pass">通过</button>
<!--    <button class="ui button" @click="update">更新</button>-->
    <hr>
  </div>
</template>

<script>
  import axios from "axios";
  import $ from "jquery";
  export default {
    name: "ShowUnproved",
    props: {
      content: Object,
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
        // axios.post('/infoEntry/' + entryId + '/approve', ).then(res => {
        //   console.log(res);
        //   if(res.code = 200) {
        //     this.flag = 0
        //   }
        //   else
        //     alert('审核失败')
        // }).catch(err => {
        //   console.log(err);
        //   alert(err)
        // })

        let that = this
        that.$api.infoApprove
          .entryApprove(entryId)
          .then(res => {
            // console.log('---****');
            console.log(res);
            if(res.code = 200) {
              this.flag = 0
            }
            else
              alert('审核失败')
          })
          .catch(err => {
            // console.log(err);
            alert(err)
          })
      },
      update() {
        this.$emit('showUpdateInput', this.id)
      }
    }
  }
</script>

<style scoped>

</style>
