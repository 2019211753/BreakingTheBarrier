/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userArticle = {
    getQuestions(data) {
        return axios
            .get(base.base1 + "/listQuestions/?page=" + data)
    },
    getBlogs(data) {
        return axios
            .get(base.base1 + "/listBlogs/?page=" + data)
    },
    showQuestion(data) {
        return axios
            .get(base.base1 + "/question/" + data)
    },
    showBlog(data) {
        return axios
            .get(base.base1 + "/blog/" + data)
    },
    searchQuestions(data) {
        return axios
            .post(base.base1 + "/searchQuestions", data)
    },
    serchBlogs(data) {
        return axios
            .post(base.base1 + "/searchBlogs", data)
    }
}

export default userArticle;