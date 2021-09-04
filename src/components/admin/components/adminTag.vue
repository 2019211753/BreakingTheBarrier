<template>
  <div class="custom-tree-container">
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
  </div>
</template>
<script>
let id = 1000;
import axios from "axios";
export default {
  name: "adminTag",
  created() {
    var that = this;
    axios
      .get("/tags/")
      .then(function (response) {
        console.log(response.data);
        that.tagList = response.data.data.tags;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  data() {
    return {
      tagList: [],
      newTagId: "",
      defaultProps: {
        children: "sonTags",
        label: "name",
      },
    };
  },

  methods: {
    append(data) {
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
    },
  },
};
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>