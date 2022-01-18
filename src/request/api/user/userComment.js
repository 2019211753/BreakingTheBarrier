/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userComment = {
    postQuestionComment(data) {
        return axios
            .post(base.base1 + "/question/" + data +
                "/comment/post")
    },
    postBlogComment(data) {
        return axios
            .post(base.base1 + "/blog/" + data +
                "/comment/post")
    },
    getAllQuestionComments(data) {
        return axios
            .get(base.base1 + "/question/" + data + "/comments")
    },
    getAllBlogComments(data) {
        return axios
            .get(base.base1 + "/blog/" + data + "/comments")
    },
    getChildComments(data) {
        return axios
            .get(base.base1 + "/comment/" + data)
    },
    deleteQuestionComment(data1, data2) {
        return axios
            .get(base.base1 + "/question/" + data1 + "/comment/" + data2 + "/delete")
    },
    deleteBlogComment(data1, data2) {
        return axios
            .get(base.base1 + "/blog/" + data1 + "/comment/" + data2 + "/delete")
    },
    getCommentSelected(data1, data2) {
        return axios
            .get(base.base1 + "/question/" + data1 + "/comment/" +
                data2 + "/select")
    }
}

export default userComment;