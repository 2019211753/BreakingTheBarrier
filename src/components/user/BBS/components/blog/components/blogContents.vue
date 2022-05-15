<template>
  <div>
    <div class="ui segment" v-if="loading == false && !contentList.length">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <div class="mainBox"
         v-show="!loading"
         v-for="(item,index) in contentList"
         :key="index">
      <div class="articleBox">
        <div class="top">
          <div class="userInfo">
            <div class="avatar">
              <img :src="item.avatar" :alt="item.nickname" class="">
            </div>
            <div class="info">
              <h3 class="title btb-h3"><router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: {
                    blogId: item.id,
                    postUserId: item.postUserId,
                  },
                }"
              >{{ item.title }}
              </router-link></h3>
              <div class="name">
                <i class="address book icon"></i>
                <span>{{item.nickname}}</span>
              </div>
            </div>
          </div>
          <div class="solve">
            <a class="ui orange basic label" v-if="item.origin === true"
            >原创</a
            >
            <a class="ui blue basic label" v-else>转载</a>
          </div>
        </div>
        <div class="divider"></div>
        <div class="middle">
          <div class="imgBox">
            <img :src="`https://picsum.photos/800/450/?random=${index}`" alt="">
          </div>
          <div class="desc btb-h5">{{item.description}}</div>
        </div>
        <div class="tags">
          <el-tag v-for="(tags,index) in item.tags" :key="index">{{tags.name }}</el-tag>
        </div>
        <div class="bottom">
          <div class="item">
            <i class="thumbs up icon"></i>
            <span style="margin-left: 2px">{{ item.likesNum }}</span>
          </div>
          <!--评论量-->
          <div class="item">
            <i class="comment icon"></i>
            <span style="margin-left: 2px">{{ item.commentsNum }}</span>
          </div>
          <!--浏览量-->
          <div class="item">
            <i class="eye icon"></i>
            <span style="margin-left: 2px">{{ item.view }}</span>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton style="width: 50vw" :loading="loading" animated :count="7">
      <template slot="template">
        <div class="ui segment">
          <div class="ui middle aligned grid">
            <div class="ten wide column">
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
            </div>
            <div class="six wide column">
              <el-skeleton-item
                variant="image"
                style="height: 120px; border-radius: 5px"
              />
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
    <el-pagination
      class="el-pagination"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-count="pageSize"
      :hide-on-single-page="true"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "blogContents",
  data() {
    return { loading: true, contentList: [], pageSize: 0, cover: [] };
  },
  created() {
    var that = this;
    that.$api.userArticle
      .getBlogs(0)
      .then(function (response) {
        that.loading = false;
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    getImgUrl(index) {
      var url = 'https://picsum.photos/800/450/?random=' + index;
      return url
    },
    handleCurrentChange(val) {
      var that = this;
      var nowPage = val - 1;
      that.$api.userArticle
        .getBlogs(nowPage)
        .then(function (response) {
          that.contentList = response.data.data.pages.content;
          that.pageSize = response.data.data.pages.totalPages;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped lang="scss">
.mainBox {
  display: flex;
  flex-direction: column;
  border: 1px solid rgba(38, 50, 56,.18);
  border-radius: 5px;
  margin-bottom: 10px;
  .articleBox {
    box-sizing: border-box;
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    //align-items: baseline;
    margin: 10px 0;
    > div {
      margin: 10px 0;
    }
    .top {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      position: relative;
      .userInfo {
        display: flex;
        flex-direction: row;
        align-items: center;
        .avatar {
          width: 45px;
          height: 45px;
          display: flex;
          border-radius: 100px;
          overflow: hidden;
          img {
            width: 100%;
          }
        }
        .info {
          margin-left: 10px;
          display: flex;
          flex-direction: column;
          justify-content: center;
          width: 35vw;
          .title {
            color: grey;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
          }
          .name {
            font-size: 14px;
            color: grey;
          }
        }
      }
      .solve {
        white-space: nowrap;
        position: absolute;
        right: 0;
      }
    }
    .divider {
      height: 20px;
      background-color: rgba(209, 209, 209,.5);
      margin: 0;
    }
    .middle {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      .imgBox {
        width: 40%;
        height: 100px;
        img {
          height: 100%;
        }
      }
      .desc {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 4;
        overflow: hidden;
        margin-left: 20px;
        height: fit-content;
        color: grey;
        letter-spacing: 1px;
      }
    }
    .tags {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      overflow: hidden;
      > span {
        //width: 10%;
        margin: 5px 5px;
      }
    }
    .bottom {
      display: flex;
      color: grey;
      .item {
        margin: 0 10px;
        cursor: pointer;
      }
      .item:hover {
        color: rgb(100, 100, 100)
      }
    }
  }
}
.el-pagination {
  margin: 0 auto;
  display: flex;
  justify-content: center;
}
@media screen and (max-width: 1198px){
  .mainBox .articleBox .top .userInfo .info {
    //width: 22vw;
  }
}
@media screen and (max-width: 1108px){
  .mainBox .articleBox .top .userInfo .info {
    //width: 24vw;
  }
}
@media screen and (max-width: 983px){
  .mainBox .articleBox .top .userInfo .info {
    width: 35vw;
  }
}
@media screen and (max-width: 750px){
  div.segment > div.middle > .btb-m-img {
    display: none;
  }
  .mainBox .articleBox .middle .imgBox {
    height: 80px;
  }
  .mainBox .articleBox .middle .desc {
    margin-left: 10px;
  }
}
@media screen and (max-width: 610px) {
  .mainBox .articleBox .top .solve > a {
    padding: 5px;
  }
  .mainBox .articleBox .top .userInfo .info .name {
    font-size: 12px;
  }
  .mainBox .articleBox .top .userInfo .avatar {
    width: 30px;
    height: 30px;
  }
  .mainBox .articleBox .top .userInfo .title {
    font-size: 14px;
    margin: 0;
  }
  .mainBox .articleBox .middle .desc {
  }
}
@media screen and (max-width: 550px){
  .mainBox .articleBox .top .userInfo .info {
    width: 65vw;
  }
}
@media screen and (max-width: 390px){
  .mainBox .articleBox .middle .imgBox {
    height: 60px;
  }
  .mainBox .articleBox .middle .desc {
    -webkit-line-clamp: 3;
  }
}
</style>
