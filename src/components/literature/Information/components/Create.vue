<template>
  <div>
    <div class="ui icon input" v-if="inputFlag">
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
    <button class="ui button" @click="btnClick"><slot name="subText"><span>创建</span></slot></button>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: "Create",
    props: {
      flag: {
        type: Number,
        default: 1
      },
      inputFlag: {
        type: Number,
        default: 1
      },
      entryId: Number
    },
    data() {
      return {
        res: '',
        title: '',
        newContent: ''
      }
    },
    methods: {
      makeSureUpdate() {
        if (this.newContent) {
          // axios.post('infoEntry/' + this.entryId + '/update', {
          //   'newContent': this.newContent,
          // }).then(res => {
          //   // console.log(res.data);
          //   // console.log(this.newContent);
          //   alert(res.data.msg)
          // }).catch(err => {
          //   alert(err)
          // })

          let that = this
          that.$api.infoUpdate
            .entryUpdate(that.entryId)
            .then(res => {
                // console.log(res.data);
                // console.log(this.newContent);
                alert(res.data.msg)
              }).catch(err => {
                alert(err)
              })
        }
        else
          alert('内容不能为空')
      },
      // makeSureUpdate() {
      //   this.$emit('makeSureUpdate', this.title, this.newContent)
      //
      // },
      btnClick() {
        if (this.flag) {
          this.$emit('create', this.title, this.newContent)
          console.log('Create组件里的btnClick函数被调用');
        }
        else
          this.makeSureUpdate()
      }
    }
  }
</script>

<style scoped>

</style>
