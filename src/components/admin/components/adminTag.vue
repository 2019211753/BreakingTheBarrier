<template>
  <div class="ui basic segment">
    <createTags :msg="tagList"></createTags>
    <!-- <div class="custom-tree-container">
      <div class="block">
        <el-tree
          :data="tagList"
          :props="defaultProps"
          show-checkbox
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          :render-content="renderContent"
        >
        </el-tree>
      </div>
    </div> -->
    <div class="ui modal" style="width: 400px">
      <div class="ui icon header">
        <i class="tags icon"></i>
        修改标签
        <br />
        <br />
        <div class="ui labeled input">
          <div class="ui label">新名称</div>
          <input type="text" placeholder="" v-model="fileName" />
        </div>
      </div>
      <div class="actions">
        <div class="ui green ok inverted button" @click="sure()">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div>
    </div>
  </div>
</template>
<script>
/* let id = 1000; */
import axios from "axios";
import createTags from "./createTags";

export default {
  name: "adminTag",
  components: { createTags },
  data() {
    return {
      tagList: [],
      newTagId: "",
      fileName: "",
      /* defaultProps: {
        children: "sonTags",
        label: "name",
      }, */
    };
  },
  created() {
    this.tagList = [
      {
        id: 1,
        parentTagId0: -1,
        name: "test_1",
        childTags: [
          {
            id: 3,
            parentTagId0: 1,
            name: "test_3",
            childTags: [],
          },
        ],
      },
      {
        id: 2,
        parentTagId0: -1,
        name: "test_2",
        childTags: [],
      },
      {
        id: 4,
        parentTagId0: -1,
        name: "testtest1",
        childTags: [],
      },
    ];
    /* var that = this;
    axios
      .get("/tags/")
      .then(function (response) {
        console.log(response.data);
      
        that.tagList = response.data.data.tags;
       

        console.log(that.tagList);
      })
      .catch(function (error) {
        console.log(error);
      }); */
  },
};
</script>

<style>
/* .custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
} */
</style>
