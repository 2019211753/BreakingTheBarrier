import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    text: "",
    articleId: "",
    blogId: "",
    me: {
      /*token: "",*/
      id: "",
      /* admin: "", */
      canSpeak: "",
      avatar: "",
      payCode: "",
      nickname: "",
      academy: "",
      email: "",
      major: "",
      sex: "",
      qqId: "",
      wechatId: "",
      personalSignature: "",
      donation: "",
      followedUserNum: "",
      followingUserNum: "",
      academies: []
    },
    others: {
      nickname: "",
      avatar: "",
      personalSignature: "",
      questionsNum: "",
      blogsNum: "",
      commentsNum: "",
      followingUserNum: "",
      followedUserNum: ""
    },
  },
  mutations: {
    getText(state, text) {
      state.text = text;
    },
    getArticleId(state, articleId) {
      state.articleId = articleId;
    },
    getBlogId(state, blogId) {
      state.blogId = blogId;
    },
    /*getToken(state, token) {
                state.me.token = token;
                console.log(state.me.token)
            },*/
    /* getCanSpeak(state, CanSpeak) {
                state.me.avatar = avatar;
            }, */
    getMyAvatar(state, avatar) {
      state.me.avatar = avatar;
    },
    getMyPayCode(state, payCode) {
      state.me.payCode = payCode;
    },
    getMyNickname(state, nickname) {
      state.me.nickname = nickname;
    },
    getMyFollowingNum(state, followingNum) {
      state.me.followingUserNum = followingNum;
    },
    getOtherFollowedNum(state, followedNum) {
      state.others.followedUserNum = followedNum;
    },
    getMySex(state, sex) {
      state.me.sex = sex;
    },
    getMyEmail(state, email) {
      state.me.email = email;
    },
    getMyQQ(state, qqId) {
      state.me.qqId = qqId;
    },
    getMyAcademy(state, academy) {
      state.me.academy = academy;
    },
    getMyMajor(state, major) {
      state.me.major = major;
    },
    getMyPersonalSignature(state, personalSignature) {
      state.me.personalSignature = personalSignature;
    },
    getMyFile1(state, data) {
      state.me.nickname = data.nickname;
      state.me.sex = data.sex;
      state.me.email = data.email;
      state.me.qqId = data.qqId;
      state.me.academy = data.academy;
      state.me.major = data.major;
      state.me.personalSignature = data.personalSignature;
    },
    getMyFile2(state, data) {
      state.me.id = data.user.id;
      state.me.canSpeak = data.user.canSpeak;
      state.me.admin = data.user.admin;
      state.me.academies = data.ACADEMIES;
      state.me.donation = data.user.donation;
      state.me.followedUserNum = data.user.followedUserNum;
      state.me.followingUserNum = data.user.followingUserNum;
    },
    getOthersFile(state, data) {
      state.others.nickname = data.userInfo.nickname;
      state.others.avatar = data.userInfo.avatar;
      state.others.personalSignature = data.userInfo.personalSignature;
      state.others.questionsNum = data.questions.length;
      state.others.blogsNum = data.blogs.length;
      state.others.commentsNum = data.comments.length;
      state.others.followingUserNum = data.userInfo.followingUserNum;
      state.others.followedUserNum = data.userInfo.followedUserNum;
    },
    turnRead(state) {
      state.readFlag = true;
    }, //效果相反了，我也不知道为啥
    turnUnread(state) {
      state.readFlag = false;
    }
  },
  actions: {}
});
