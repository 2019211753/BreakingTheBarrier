<template>
  <div class="ui basic segment">
    <createTags></createTags>
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
    var that = this;
    axios
      .get("/tags/")
      .then(function (response) {
        console.log(response.data);
        /* that.getAllNodeId(that.tagList, response.data.data.tags); */
        that.tagList = response.data.data.tags;
        sessionStorage.setItem("tagList", JSON.stringify(that.tagList));

        console.log(that.tagList);
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    getAllNodeId(expandedKeys, moduleDataList) {
      for (let i = 0; i < moduleDataList.length; i++) {
        // console.log('i in getAllNodeId: ', i)
        expandedKeys.push(moduleDataList[i]);
        if (moduleDataList[i].childTags) {
          expandedKeys = this.getAllNodeId(
            expandedKeys,
            moduleDataList[i].childTags
          );
        }
      }
      return expandedKeys;
    },
    /*  append(data) {
      axios
        .post("/admin/tags/input", {
          name: "testtest1",
          parentTagId0: -1,
        })
        .then(function (response) {
          console.log(response);
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
      const newChild = { id: id++, name: "testtest1", sonTags: [] };
      if (!data.sonTags) {
        this.$set(data, "sonTags", []);
      }
      data.sonTags.push(newChild);
    },

    remove(node, data) {
      const parent = node.parent;
      const sonTags = parent.data.sonTags || parent.data;
      const index = sonTags.findIndex((d) => d.id === data.id);
      sonTags.splice(index, 1);
    },

    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span>
          <span>
            <el-button
              style="margin-left:5px"
              size="mini"
              type="text"
              on-click={() => this.append(data)}
            >
              Append
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.remove(node, data)}
            >
              Delete
            </el-button>
          </span>
        </span>
      );
    }, */
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
