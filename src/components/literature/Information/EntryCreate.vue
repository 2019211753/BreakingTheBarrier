<template>
  <div class="ui container" style="width: 300px">
    <button class="ui button mini" @click="btnClick">提交</button>
    <button class="ui button mini">保存</button>
    <h2 class="ui header">富文本编辑器</h2>

    <div class="ui divider"></div>
    <div class="ui segment" style="width: 500px;margin: auto">
      <div class="ui left dividing rail">
        <div class="ui segment">
          <h2 class="ui header">词条分类</h2>
          <div class="ui label">竞赛</div>
          <div class="ui label">科研</div>
        </div>

        <div class="ui segment">
          <h3 class="ui header">目录</h3>
          <div class="ui divider"></div>
        </div>
      </div>

      <div>
        <!--主编辑区-->
        <h3 class="ui header">词条</h3>
        <span class=" ">义项名: </span>
        <div class="ui input" >
          <input type="text" placeholder="相同含义的名词或别名" v-model="title" :key="key1" id="key1">
        </div>
        <br>
        <br>
        <div class="ui input " style="min-height: 200px;width: 470px" >
          <input type="text" placeholder="请输入词条简介......" v-model="newContent" :key="key2" id="key2">
        </div>

        <br>
        <br>
        <div></div>
      </div>
    </div>

<!--弹窗-->
    <div class="ui basic modal">
      <div class="ui icon header">
        <i class="archive icon"></i>
        提示
      </div>
      <div class="content">
        <p>
          {{submitMsg}}
        </p>
      </div>
      <div class="actions">

        <div class="ui green ok inverted button">
          <i class="checkmark icon"></i>
          是的
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  export default {
    name: "EntryCreate",
    data() {
      return {
        title: "",
        newContent: '',
        submitMsg: '',
        key1: 0,
        key2: 10,
      }
    },
    methods: {
      createEntry(title, newContent) {
        //若两个都不为空
        if(newContent && title) {
          let data = {
            'title': title,
            'newContent': newContent
          }
          let that = this
          that.$api.infoCreate
            .createEntry(data)
            .then(res => {
              // console.log(res);
              // alert(res.data.msg)
              this.title = ""
              this.newContent = ""
              // this.forceUpdate()
              this.submitMsg = res.data.msg
              // if(res.data.code == 200)
              //   this.forceUpdate()
            })
            .catch(err => {
              // alert(err)
              this.submitMsg = err
            })
        }
        else
          this.submitMsg = '标题或简介不能为空'
      },
      btnClick() {
        this.createEntry(this.title, this.newContent)

        $('.ui.modal')
          .modal('show')
      },
      forceUpdate() {

      }
    }
  }
</script>

<style scoped>
  /*#placeholder::placeholder {*/
  /*  text-align: left;*/
  /*}*/
</style>
