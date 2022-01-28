<template>
  <div class="ui basic segment">
    <el-dialog width="400px" :visible.sync="dialogFormVisible">
      <div class="ui icon header">
        <i class="tags icon"></i>
        新增标签
        <br />
        <br />
        <div class="ui labeled input">
          <div class="ui label">新标签名称</div>
          <input type="text" placeholder="请输入标签名" v-model="tagName" />
        </div>
      </div>
      <div class="actions">
        <div class="ui teal button" @click="sure()" style="margin-left: 130px">
          <i class="checkmark icon"></i>
          确定
        </div>
      </div>
    </el-dialog>
    <el-skeleton :loading="loading" animated :count="3">
      <template slot="template">
        <el-skeleton-item variant="text" />
        <el-skeleton-item variant="text" />
        <el-skeleton-item variant="text" />
      </template>
    </el-skeleton>
    <el-tree
      v-if="loading == false"
      :data="tagList"
      :render-content="renderContent"
      :default-expand-all="true"
      :expand-on-click-node="false"
      :props="defaultProps"
    >
    </el-tree>
    <div
      class="ui right floated icon button"
      style="margin-top: 20px"
      @click="createParentTag()"
    >
      <i class="plus icon"></i>
    </div>
  </div>
</template>
<script>
export default {
  name: "adminTag",
  data() {
    return {
      dialogFormVisible: false,
      loading: true,
      id: "",
      tagName: "",
      tagList: [],
      chooseTagList: [],
      chooseTagIdList: [],
      defaultProps: { id: "id", children: "childTags", label: "name" },
    };
  },
  created() {
    var that = this;
    that.getTags();
  },
  methods: {
    getTags() {
      var that = this;
      that.$api.userTag
        .getTags()
        .then(function (response) {
          that.tagList = response.data.data.tags;
          that.loading = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    createParentTag() {
      var that = this;
      that.dialogFormVisible = true;
      that.id = "-1";
    },
    appendTag(data) {
      var that = this;
      that.dialogFormVisible = true;
      that.id = data.id;
    },
    sure() {
      var that = this;
      var data = {
        name: that.tagName,
        parentTagId0: that.id,
      };
      var p1 = new Promise((resolve, reject) => {
        var data = {
          name: that.tagName,
          parentTagId0: that.id,
        };
        that.$api.adminTag
          .addTag(data)
          .then(function (response) {
            that.$message({
              message: "添加成功",
              type: "success",
            });
            that.tagName = "";
            that.id = "";
          })
          .catch(function (error) {
            console.log(error);
          });
      });
      var p2 = new Promise((resolve, reject) => {
        that.getTags();
      });
      Promise.all([p1, p2]).then((res) => {});
    },
    removeTag(node, data) {
      var that = this;
      var p1 = new Promise((resolve, reject) => {
        that.$api.adminTag
          .deleteTag(data.id)
          .then(function (response) {
            if (response.data.code == 200) {
              that.$message({
                message: "删除成功",
                type: "success",
              });
            } else {
              that.$message({
                message: response.data.msg,
                type: "warning",
              });
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      });
      var p2 = new Promise((resolve, reject) => {
        that.getTags();
      });
      Promise.all([p1, p2]).then((res) => {});
    },
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <i class="tag icon"></i>
          <span style="margin-left:5px">{node.label}</span>
          <span style="margin-left:80px">
            <span>
              <el-button
                style="color:grey"
                size="mini"
                type="text"
                on-click={() => this.appendTag(data)}
              >
                新增标签
              </el-button>
              <el-button
                style="color:grey"
                size="mini"
                type="text"
                on-click={() => this.removeTag(node, data)}
              >
                删除标签
              </el-button>
            </span>
          </span>
        </span>
      );
    },
  },
};
</script>

<style>
.custom-tree-node {
  width: auto;
  height: 25px;
  float: right;
  font-weight: 600;
  font-size: 17px;
  letter-spacing: 1px;
}
</style>
