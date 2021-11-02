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
    <button class="ui button" @click="btnClick"><slot name="subText"><span>创建</span></slot></button>
  </div>
</template>

<script>
  export default {
    name: "Create",
    props: {
      flag: {
        type: Number,
        default: 1
      }
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
        this.$emit('makeSureUpdate')
      },
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
