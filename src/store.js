import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
    state: {
        articleId: "",
        blogId: "",
        me: {
            /* token: "", */
            id: "",
            /* admin: "", */
            canSpeak: "",
            avatar: "",
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
            contentsNum: "",
            followingUserNum: "",
            followedUserNum: "",
        }
    },
    mutations: {
        getArticleId(state, articleId) {
            state.articleId = articleId;
        },
        getBlogId(state, blogId) {
            state.blogId = blogId;
        },
        /* getToken(state, token) { state.me.token = token }, */
        /* getCanSpeak(state, CanSpeak) {
            state.me.avatar = avatar;
        }, */
        getMyAvatar(state, avatar) {
            state.me.avatar = avatar;
        },
        getMyNickname(state, nickname) {
            state.me.nickname = nickname;
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
            state.me.academies = data.academies;
            state.me.donation = data.user.donation;
            state.me.followedUserNum = data.user.followedUserNum;
            state.me.followingUserNum = data.user.followingUserNum
        },
        getOthersFile(state, data) {
            state.others.nickname = data.nickname;
            state.others.avatar = data.avatar;
            state.others.personalSignature = data.personalSignature;
            state.others.contentsNum = data.blogs.length + data.comments.length + data.questions.length;
            state.others.followingUserNum = data.followingUserNum;
            state.others.followedUserNum = data.followedUserNum
        }
    },
    actions: {}
})