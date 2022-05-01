<template>
  <div>
    <div class="btn-box">
      <button class="ui button mini" @click="btnClick">提交</button>
      <button class="ui button mini">保存</button>
    </div>
    <div class="ui divider"></div>

    <!--总盒子-->
    <div class="main-box" id="mainBox">

      <!--左边栏-->
      <div class="left-box">
        <div class="left-box-item">
          <h2 class="ui header">词条分类</h2>
          <div class="ui label">竞赛</div>
          <div class="ui label">科研</div>
        </div>

        <div class="left-box-item">
          <h3 class="ui header">目录</h3>
          <div class="ui divider"></div>
        </div>
      </div>

      <!--主区-->
      <div class="middle-box">
        <!--主编辑区-->
        <div class="first-module">
          <h3 class="ui header">词条</h3>
          <span class=" ">义项名: </span>
          <div class="ui input">
            <input type="text" placeholder="相同含义的名词或别名" v-model="title">
          </div>
          <br>
          <br>
          <div class="ui form">
            <div class="field">
              <textarea style="margin-top: 0px; margin-bottom: 0px; height: 112px;width: 100%"
                        placeholder="请输入词条简介.." v-model="description">

              </textarea>
            </div>
          </div>
        </div>

        <br>
        <br>
        <div class="second-module">
          <editor class="editor"></editor>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery';
  import Editor from "../../user/BBS/components/editor/editor";
  export default {
    name: "EntryCreate",
    components: {Editor},
    data() {
      return {
        id: 20,
        entryTagName: '科普',
        title: '',
        description: '',
        newContent: 0,
        submitMsg: '',
      }
    },
    created() {
      // console.log(this.$route.query);
      // if(!this.$route.query) {
      this.id = this.$route.query.id
      // this.title = this.$route.query.title
      // this.newContent = this.$route.query.currentContent
      // }

    },
    methods: {
      createEntry(entryTagName, title, description) {
        // console.log(this.title);
        if (this.id === undefined) {
          //为undefined -> 是创建
          if (this.$store.state.text && this.title && this.description && this.entryTagName) {//若表单各项参数都不为空
            let data = {
              entryTagName: entryTagName,
              body: {
                title: title,
                alias: 'none',
                description: description,
                newContent: this.$store.state.text,
              }
            }
            console.log(this.$store.state.text);
            console.log(this.$store.state);
            let that = this
            that.$api.infoCreate
              .createEntry(data)
              .then(res => {
                this.submitMsg = res.data.msg
                if (res.data.code === 200) {
                  this.title = ""
                  this.newContent = ""
                }
              })
              .catch(err => {
                this.submitMsg = err
              })
          }
          else
            this.$message.error('标题或简介或标签或内容不能为空')
        }
        else { //是更新词条
          console.log(this.id);
          let data = {
            id: this.id,
            title: this.title,
            newContent: this.newContent
          }
          let that = this
          that.$api.infoUpdate.entryUpdate(data)
            .then((res) => {
              this.submitMsg =res.data.msg
            })
          .catch(err => {
            this.submitMsg = err
          })
        }
      },
      btnClick() {
        this.createEntry(this.entryTagName, this.title, this.description)
      },
    }
  }
</script>

<style scoped lang="scss">
  .btn-box {
    display: flex;
    justify-content: end;
  }

  .main-box {
    /*width: 1200px;*/
    display: flex;
    justify-content: space-between;
    .left-box {
      width: 365px;
      padding: 0 10px 10px 10px;
      .left-box-item {
        margin: 0 10px 20px 10px;
        border: 1px solid rgba(1,1,1,.1);
        box-sizing: border-box;
        border-radius: 5px;
        padding: 20px;
      }
    }
    .middle-box {
      width: 800px;
      box-sizing: border-box;
      .first-module {
        padding: 20px;
        border: 1px solid rgba(1,1,1,.1);
        box-sizing: border-box;
        border-radius: 5px;
        margin-bottom: -30px;
      }
      .second-module {
        .editor {
          width: 100%;
          border: 1px solid rgba(1,1,1,.1);
          box-sizing: border-box;
          border-radius: 5px;
          overflow: hidden;
        }
      }
    }
  }

</style>
