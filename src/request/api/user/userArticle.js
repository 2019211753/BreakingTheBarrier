/**
 * article模块接口列表
 */

import base from "../../base";

import instance from "../../http";

const userArticle = {
    getQuestions(data) {
        return instance.get("/listQuestions/?page=" + data);
    },
    getBlogs(data) {
        return instance.get("/listBlogs/?page=" + data);
    },
    showQuestion(data) {
        return instance.get("/question/" + data);
    },
    showBlog(data) {
        return instance.get("/blog/" + data);
    },
    searchQuestions(data) {
        return instance.post("/searchQuestions", data);
    },
    serchBlogs(data) {
        return instance.post("/searchBlogs", data);
    },
    uploadPicture(data) {
        return instance.post("/uploadPictures", data);
    }
};

export default userArticle;