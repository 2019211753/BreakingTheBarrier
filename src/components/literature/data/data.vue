<template>
  <div v-loading="uploading">
    <div class="mask"></div>
    <div class="title">
      <h1 class="btb-h1">资料文献</h1>
      <div class="operate">
        <div class="ui small inverted buttons">
          <div class="ui red inverted button" @click="showUploadInout" id="showUploadBtn">
            <i class="upload icon"></i>上传
          </div>
          <div class="or"></div>
          <div class="ui pink inverted button" @click="showSearchInput">
            <i class="search icon"></i>搜索
          </div>
        </div>
      </div>
      <div class="ui icon input" id="input">
        <input type="text" placeholder="搜索..." @keyup.enter="searchFile(query,pageIndex)" v-model="query">
        <i class="inverted circular search link icon" @click="searchFile(query,pageIndex)"></i>
      </div>
    </div>
    <el-empty v-if="files.length <= 0"></el-empty><!--files.length <= 0-->
    <div v-else>
      <el-skeleton :rows="6" animated :loading="isLoading"/>
      <div class="mainBox">
        <div class="uploadBox">
          <div class="input">
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
          </div>
          <div class="tags">
            <!--标签输入框-->
            <div class="ui right labeled left icon input"
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
          <div class="sureUpload">
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
        <!--这个div是单个文件(封面)-->
        <div class="ui segment" style="height: 305px"
             v-for="(item,index) in files"
             :key="index">
          <a class="ui red ribbon label">
              <span v-for="(tags,index) in item.fileTags" style="margin-right: 3px">
                {{ tags.name }}&nbsp
              </span>
          </a>
          <FilesCover :file-info="item" :cover-index="Math.floor(Math.random() * files.length)">

          </FilesCover>
        </div>
      </div>
      <div class="el-page">
        <el-pagination
          @current-change="handleCurrentChange"
          class="el-page"
          layout="prev, pager, next"
          :page-count="totalPages"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import SearchFile from "../components/SearchFile";
  import FileDisplay from "../FileDisplay";
  import FileDisplayCItem from "../Information/FileDisplayCItem";
  import btbDescription from "../../user/home/components/btbDescription";
  import btbDescriptionItem from "../../user/home/components/btbDescriptionItem";
  import $ from "jquery";
  import FilesCover from "./FilesCover";
  export default {
    name: "Data",
    data() {
      return {
        pageIndex: 0,
        totalPages: 0,
        files: [],
        query: '',
        tags: [],
        tag_input: '',
        isShowUpload: false,
        isLoading: false,
        fileName: '',
        flagUpload: false,
        uploading: false,
      }
    },
    created() {
      this.searchFile(this.query, this.pageIndex)
    },
    methods: {
      showSearchInput() {
        if (!this.isShowSearch) {
          $('#input').animate({
            top: '0',
            opacity: '1',
            // zIndex: '1'
          })
          this.isShowSearch = true
        }
        else {
          $('#input').animate({
            top: '-80px',
            opacity: '0',
            // zIndex: '-1'
          })
          this.query = ''
          this.isShowSearch = false
        }
      },
      showUploadInout() {
        if (!this.isShowUpload) {
          $('.uploadBox').animate({
            top: '0',
            opacity: '1',
            zIndex: '5'
          })
          $('.mask').animate({
            opacity: '1',
            height: '100vh'
            // zIndex: '3',
          }).attr('style','z-index:3')
          $('#showUploadBtn').animate({
            zIndex: '4'
          }).text('返回')
          this.isShowUpload = true
        }
        else {
          $('.uploadBox').animate({
            top: '-500px',
            opacity: '0',
            zIndex: '2'
          })
          $('.mask').animate({
            opacity: '0',
          }).attr('style','')
          $('#showUploadBtn').animate({
            zIndex: '0'
          }).text('上传')

          this.isShowUpload = false
        }
      },
      showTagInput() {
        $('#addTag').transition('scale')
      },
      addTag() {
        let isHave = this.tags.some(item => {
          return item === this.tag_input
        })
        if (!isHave) {
          this.tags.push(this.tag_input)
          this.tag_input = ''
        }
        else
          this.$message.error('该标签已存在')
      },
      delCurrentTag(index) {
        this.tags.splice(index,1)
      },
      searchFile(query,pageIndex) {
        this.isLoading = true
        this.$api.dataFind.findFiles(query, pageIndex)
          .then(res => {
            this.isLoading = false
            this.files = []
            if(res.data.data.page.content.length >= 1) {
              this.files = res.data.data.page.content
              this.totalPages = res.data.data.page.totalPages
            }
            else
              this.$message.error('没有您要找的文件')
          })
        .catch(err => {
          this.$message.error(err)
        })
      },
      showFileName() {
        this.flag = true
        this.flagUpload = true
        this.flagFileInfo = true
        let fileObj = document.getElementById('file').files[0]
        this.fileName = fileObj.name
      },
      divClick() {
        document.getElementById('file').click()
      },
      upFile() {
        this.uploading = true
        let fileObj = document.getElementById('file').files[0];  //获取文件对象
        let formData = new FormData()
        //获取所上传文件的表单数据
        // let tagSelected = this.getTag()
        // console.log(JSON.stringify(tagSelected));
        formData.append("uploadFile",fileObj)
        formData.append("fileTagName",JSON.stringify(this.tags))
        formData.append("uploadUser",'')
        // console.log(formData);
        if (this.fileName && this.tags.length > 0 ) {
          this.$api.dataUpload
            .upFile(formData)
            .then(res => {
              this.uploading = false
              if(res.data.code === 200) {
                this.$message.success('上传成功')
                $('#showUploadBtn').click()
                document.getElementById('file').value = ''
                this.fileName = ''
                this.flagFileInfo = false
                this.flag = false //flag用于决定输入标签的输入框是否显示
              }
              else
                this.$message.error('上传失败:' + res.data.msg)
            })
            .catch(err => {
              this.uploading = false
              this.$message(err)
            })
        }
        else {
          this.uploading = false
          this.$message.error('文件或标签不能为空')
        }
      },
      handlePage(page) {
        {
          //保留子组件fileDisplay传输的数据
          // console.log(page);
          this.page.totalElements = page.totalElements
          this.page.totalPages = page.totalPages
          this.page.numberOfElements = page.numberOfElements
          // console.log(this.page.numberOfElements);
          // console.log(this.page);
        }
      },
      handleCurrentChange(val) {
        this.pageIndex = val - 1
        this.searchFile(this.query,this.pageIndex)
      },

    },
    components: {
      SearchFile,
      FileDisplay,
      FileDisplayCItem,
      btbDescription,
      btbDescriptionItem,
      FilesCover
    }
  }
</script>

<style scoped lang="scss">
.ui.buttons .or {
  z-index: 2;
}
.tags > span > i {
  color: white;

}
.delTag i:hover{
  color: white;
}
.mask {
  width: 100vw;
  //height: 100vh;
  background-color: rgba(1,1,1,.8);
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
}
.uploadBox {
  display: flex;
  flex-direction: column;
  position: absolute;
  top: -500px;
  z-index: 1;
  > div {
    margin-bottom: 20px;
  }
  #fileName {
    text-align: center;
    color: white;
  }
  .tags > div {
    margin-bottom: 10px;
  }
  .sureUpload {
    text-align: center;
  }
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
#input {
  margin-left: 10px;
  position: relative;
  top: -80px;
  opacity: 0;
  //z-index: 1;
}
.el-page {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.title {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  padding-bottom: 5px;
  border-bottom: 1px solid grey;
  > h1 {
    margin-right: 10px;
    white-space: nowrap;
    color: grey;
  }
}
.mainBox {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: baseline;
  position: relative;
  > div {
    width: 30%;
    height: 240px;
    .xxx {
      position: relative;
      > div {
        width: 100%;
        justify-content: center;
        border: none;
        span {
          text-overflow: ellipsis;
          overflow: hidden;
        }
      }
      :nth-child(2) {
        -webkit-line-clamp: 5;
        line-height: 150%;
      }
    }
    .more {
      position: absolute;
      bottom: 0;
      right: 0;
      cursor: pointer;
      > div {
        border: none;
      }
    }
  }
}
@media screen and (max-width: 568px){
  .mainBox > div {
    //height: 200px;
    //width: 46%;
  }
}
@media screen and (max-width: 550px){
  .mainBox > div {
    width: 100%;
  }
}
</style>
