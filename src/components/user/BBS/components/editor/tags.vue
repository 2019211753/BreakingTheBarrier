<template>
  <div class="ui basic segment">
    <div class="ui two column very relaxed grid">
      <div class="column">
        <div class="ui basic segment">
          <h3>选择标签</h3>
          <div class="ui divider"></div>
          <div>
            <el-tree
              :data="tagList"
              :props="defaultProps"
              @node-click="handleNodeClick"
            >
            </el-tree>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="ui basic segment">
          <h3>已选择</h3>
          <div class="ui divider"></div>
          <div>
            <a class="ui label" v-for="item in chooseTagList">
              {{ item.name }}
              <i class="delete icon" @click="deleteTag(item.id)"></i>
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="ui vertical divider"></div>
  </div>
</template>

<script>
export default {
  name: "tags",
  data() {
    return {
      tagList: [],
      chooseTagList: [],
      chooseTagIdList: [],
      defaultProps: {
        children: "childTags",
        label: "name",
      },
    };
  },
  created() {
    var that = this;
    that.$api.userTag
      .getTags()
      .then(function (response) {

        that.tagList = response.data.data.tags;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    handleNodeClick(data) {
      var that = this;
      var tagFlag = 0;

      for (var i = 0; i < that.chooseTagList.length; i++) {
        if (that.chooseTagList[i].id === data.id) {
          tagFlag = 1;
        }
      }
      if (tagFlag === 0) {
        that.chooseTagList.push(data);
        that.chooseTagIdList.push(data.id);

        sessionStorage["chooseTagIdList"] = that.chooseTagIdList;
      }
    },
    deleteTag(id) {
      var that = this;
      for (var i = 0; i < that.chooseTagList.length; i++) {
        if (that.chooseTagList[i].id === id) {
          that.chooseTagList.splice(i, 1);
        }
        if (that.chooseTagIdList[i] === id) {
          that.chooseTagIdList.splice(i, 1);
        }
      }

      sessionStorage["chooseTagIdList"] = that.chooseTagIdList;
    },
  },
};
</script>

<style scoped>
.label {
  margin-top: 5px;
}
</style>
