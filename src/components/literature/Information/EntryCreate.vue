<template>
  <div>
    <el-skeleton :rows="6" animated :loading="isLoading"/>
    <div class="ui divider"></div>

    <!--主区-->
    <div class="middle-box">
      <!--主编辑区-->
      <div class="first-module">
        <div class="title">
          <span class=" "><i class="heading icon"></i>词条名: </span>
          <div class="ui input">
            <input type="text" placeholder="词条名" v-model="title">
          </div>
        </div>
        <div class="alias"><span class=" "><i class="book icon"></i>别名: </span>
          <div class="ui input">
            <input type="text" placeholder="相同含义的名词或别名" v-model="alias">
          </div>
        </div>
        <div class="tags">
          <span><i class="tags icon"></i></span>
          <div class="ui icon button"
               data-content="创建标签"
               id="addTagButton"
               @click="showTagInput"
               style="margin-bottom: 5px;">
            <i class="add icon"></i>
          </div>
          <!--标签输入框-->
          <div class="ui right labeled left icon input"
               v-show="false"
               id="addTag">
            <i class="tags icon"></i>
            <input type="text"
                   placeholder="输入标签"
                   id="fileTagName"
                   @keyup.enter="addTag"
                   v-model="tag_input">
            <a class="ui tag label" @click="addTag">添加</a>
          </div>
          <!--当前的标签数组-->
          <div
            class="currentTags"
            v-show="tags.length > 0">
            <div v-for="(item,index) in tags">
              <a class="ui blue label"><i class="tag icon"></i>{{ item }}</a>
              <span class="delTag" @click="delCurrentTag(index)"><i class="x icon"></i></span>
            </div>
          </div>
        </div>
        <div class="ui form">
          <div class="field">
            <span><i class="info icon"></i>简述:</span>
            <textarea style="margin-top: 0px; margin-bottom: 0px; height: 112px;width: 100%"
                      placeholder="请输入词条简介.." v-model="description">

              </textarea>
          </div>
        </div>
      </div>
      <br>
      <div class="second-module">
        <div><i class="bars icon"></i>正文:</div>
        <i style="font-size: 12px">支持markdown格式,其简单语法见</i>
        <a href="https://markdown.com.cn/basic-syntax/"><i style="font-size: 12px">官方文档</i></a>
        <editor class="editor" style="margin-top: 10px"></editor>
      </div>
    </div>
    <div class="btn-box">
      <button class="ui right labeled icon button" @click="btnClick">
        <i class="right arrow icon"></i>
        提交
      </button>
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
        id: undefined,
        title: '',
        alias: '',
        description: '',
        newContent: '',
        tags: [],
        tag_input: '',
        isLoading: true
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
    mounted() {
      this.isLoading = false
    },
    methods: {
      submitEntry() {
        // console.log(this.title);
        if (this.id === undefined) {
          //为undefined -> 是创建
          if (this.$store.state.text && this.title && this.description && this.tags.length > 0) {
            //若表单各项参数都不为空
            let data = {
              entryTags: JSON.stringify(this.tags),
              title: this.title,
              alias: this.alias,
              discription: this.description,
              newContent: this.$store.state.text,
            }
            // console.log(this.$store.state.text);
            // console.log(this.$store.state);
            this.$api.infoCreate
              .createEntry(data)
              .then(res => {
                this.submitMsg = res.data.msg
                if (res.data.code === 200) {
                  this.title = ""
                  this.newContent = ""
                  this.$message.success(res.data.msg)
                  setTimeout(() => {
                    location.reload()
                  },500)
                }
              })
              .catch(err => {
                console.log(err);
                if(err === '我也不知道发生什么了...')
                  this.$message.error('创建失败,可能已存在相同词条')
                else
                  this.$message.error('创建失败,' + err)
              })
          }
          else
            this.$message.error('词条名、别名、简介、标签或正文不能为空')
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
      showTagInput() {
        $('#addTag').transition('scale')
      },
      addTag() {
        let isHave = this.tags.some(item => {
          return item === this.tag_input
        })
        if (!isHave && (this.tag_input !== '')) {//输入的标签不为空且没有重复
          this.tags.push(this.tag_input)
          this.tag_input = ''
        }
        else if(this.tag_input === '')
          this.$message.warning('不能添加空标签')
        else
          this.$message.error('该标签已存在')
      },
      delCurrentTag(index) {
        this.tags.splice(index,1)
      },
      btnClick() {
        this.submitEntry()
      }
    }
  }
</script>

<style scoped lang="scss">
.first-module > div {
  margin-bottom: 10px;
}
.delTag {
  cursor: pointer;
  color: grey;
}
.delTag:hover {
  color: rgba(0.5,0.5,0.5,1);
}
.currentTags {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 10px;
  > div {
    margin-right: 10px;
    margin-bottom: 5px;
  }
}
  .btn-box {
    display: flex;
    justify-content: center;
    margin-top: 10px;

  }

  .main-box {
    /*width: 1200px;*/
    display: flex;
    justify-content: space-between;
    .middle-box {
      width: 100%;
      box-sizing: border-box;
      overflow: hidden;
      text-overflow: ellipsis;
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
