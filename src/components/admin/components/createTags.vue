<template>
  <div>
    <div class="item" v-for="item in tagList" :key="item.id">
      <i class="tag icon"></i>
      <div class="content">
        <div class="ui mini icon right floated button" @click="showEditTag()">
          <i class="ui edit icon"></i>
        </div>
        <div class="ui mini icon right floated button">
          <i class="ui trash icon"></i>
        </div>
        <div class="ui mini icon right floated button">
          <i class="ui plus icon"></i>
        </div>
        <div class="header">{{ item.name }}</div>
        <createTags :tagList="item.childTags">2</createTags>
        <div class="list"></div>
      </div>
    </div>
  </div>
</template>

<script>
/* import axios from "axios"; */
export default {
  name: "createTags",
  props: { msg: { type: Array, default: [] } },
  data() {
    return { tagList };
  },
  mounted() {
    console.log(this.tagList);
  },

  methods: {
    showEditTag() {
      $(".modal").modal("show");
    },
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

<style scoped>
</style>
