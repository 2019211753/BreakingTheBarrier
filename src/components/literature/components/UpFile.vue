<template>
  <div>
    <div>
    </div>
    <div style="text-align: center">
      <!-- 上传按钮-->
      <div class="ui animated button"
           style="max-width: 300px;
                      width: 255px;"
           @click="divClick">
        <div class="visible content">上传</div>
        <div class="hidden content"
             style="max-width: 300px;
                       width: 255px;">
          <i class="upload icon"></i>
        </div>
      </div>

      <!--用于显示文件名-->
      <div id="fileName">
        <strong>{{fileName}}</strong>
      </div>

      <!--实际上传文件的input-->
      <input type="file"
             id="file"
             @input="showFileName"
             style="display: none">

      <!--显示所有文件标签 以下内容点击上传按钮后才会显示-->
      <div v-show="flagFileInfo">
        <div id="showTags" @click="getTag">
          <a class="ui grey label"
             @click="selectTag($event)" v-for="item in tags">
            <i class="tag icon"></i>{{item}}</a>
        </div>
        <!--这个div是+按钮-->
        <div class="ui icon button"
             data-content="创建标签"
             id="addTagButton"
             @click="addTag"
             style="margin-bottom: 5px;">
          <i class="add icon"></i>
        </div>
        <!--这个div是标签输入框-->
        <div class="ui right labeled left icon input"
             v-show="false"
             id="addTag">
          <i class="tags icon"></i>
          <input type="text"
                 placeholder="输入标签"
                 id="fileTagName"
                 v-model="tagSelected">
          <a class="ui tag label" @click="addTagBtnClick">添加标签</a>
        </div>
        <div class="ui animated button"
             tabindex="0"
             @click="upFile" v-show="flagUpload">
          <div class="visible content">确认上传</div>
          <div class="hidden content">
            <i class="thumbs up icon"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import $ from "jquery";
  export default {
    name: "UpFile",
    data() {
      return {
        flag: false,
        flagUpload: false,
        flagFileInfo: false,
        flagAddTag: false,
        tags: [],
        uploadFile: '',
        tagSelected: [],
        fileData: '',
        fileName: ''
      }
    },
    // created() {
    //   axios('/files/getAllTags')
    //     .then(res => {
    //     console.log(res.data.code);
    //     if (res.data.code = 200) {
    //       for (let i in res.data.data.fileTags) {
    //         this.tags.push(res.data.data.fileTags[i].name) //把一个个文件标签塞进数组tags里
    //       }
    //     }
    //   })
    //
    // },
    mounted() {
      $('.button').popup()
    },
    methods: {
      divClick() {
        document.getElementById('file').click()
      },
      showFileName() {
        this.flag = true
        this.flagUpload = true
        this.flagFileInfo = true
        let fileObj = document.getElementById('file').files[0]
        this.fileName = fileObj.name
        console.log(this.fileName);
      },
      upFile() {
        let fileObj = document.getElementById('file').files[0];  //获取文件对象
        let formData = new FormData()
        //获取所上传文件的表单数据
        let tagSelected = this.getTag()
        // console.log(JSON.stringify(tagSelected));
        formData.append("uploadFile",fileObj)
        formData.append("fileTagName",JSON.stringify(tagSelected))
        formData.append("uploadUser",'')
         // console.log(formData);
         if (formData) {
           axios({
             url: 'files/upload',
             method: "post",
             headers: {
               'Content-Type': 'multipart/form-data'
             },
             data: formData,
           })
             .then(res => {
               // console.log(res);
               // console.log(res.data);
               // console.log(res.data.msg);
               // console.log('--------------'+fileObj)
               if(res.data.code == 200 && tagSelected) {
                 alert(res.data.msg)
                 document.getElementById('file').value = ''
                 this.fileName = ''
                 this.flagFileInfo = false
                 this.flag = false //flag用于决定输入标签的输入框是否显示
               }
               else if(tagSelected.length == 0)
                 alert('请填写文件标签')
               else
                 alert('上传失败\n' + res.data.msg)
             })
             .catch(err => {
               console.log(err);
             })
         }
         else
           alert('不对')
       },
      addTag() {
        $('#addTag').transition('scale')
      },
      getTag() {
        let tagSelected = []
        let array = $('#showTags').children()
        // console.log($(array).length);
        for(let i = 0; i < $(array).length; i++) {
          let currentDom = $('#showTags').children()[i]
          let currentTag
          if($(currentDom).attr('class') == 'ui blue label') {
            currentTag = $(currentDom).text()
            tagSelected.push(currentTag)
          }
        }
        console.log(tagSelected);
        return tagSelected
      },
      selectTag(event) {
        let dom = event.currentTarget
        let presetTag //预设的class
        let currentTag = $(dom).attr('class') //当前的class
        if(currentTag == 'ui grey label')
          presetTag = 'ui blue label'
        else
          presetTag = 'ui grey label'
        $(dom).attr('class', presetTag)
      },
      addTagBtnClick() {//手动增加标签 下一步解决标签重复问题//已解决
        let currentTags = []        //获取当前有哪些标签
        let aDom = $('#showTags').children()
        let aDomTag = ''
        for(let i = 0; i<aDom.length; i++) {
          aDomTag = $(aDom[i]).text()
          currentTags.push(aDomTag)
        }
        console.log('---'+currentTags);
        let newTag = $('#fileTagName').val()
        if (newTag) {
          let flag = currentTags.some(item => {
            return item == newTag
          })
          console.log(flag);
          if (!flag) { //如果原标签不含newTag 才添加newTag
            $('#showTags').append(`<a class="ui blue label" @click="selectTag($event)"><i class="tag icon"></i></a>`)
            let domTemp = $('#showTags').children()
            $(domTemp[domTemp.length - 1]).append(newTag)
            $(domTemp[domTemp.length - 1]).on('click', domTemp[domTemp.length - 1], function (event) {
              let dom = event.target
              let presetTag //预设的class
              let currentTag = $(dom).attr('class') //当前的class
              if(currentTag == 'ui grey label')
                presetTag = 'ui blue label'
              else
                presetTag = 'ui grey label'
              $(dom).attr('class', presetTag)
            })
          }
          else
            alert('该标签已存在')
        }
      }
    }
  }
</script>

<style scoped>
  #showTags {
    margin-bottom: 5px;
  }
  div#showTags > a {
    margin: 4px;
  }
</style>
