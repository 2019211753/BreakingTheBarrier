/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userLike = {
    likeQuestion(data) {
        return axios
            .get(base.base1 + "/question/" + data + "/approve")
    },
    likeBlog(data) {
        return axios
            .get(base.base1 + "/blog/" + data + "/approve")
    },
    likeComment(data) {
        return axios
            .get(base.base1 + "/comment/" + data + "/approve")
    },
    dislikeQuestion(data) {
        return axios
            .get(base.base1 + "/question/" + data + "/disapprove")
    },
    dislikeBlog(data) {
        return axios
            .get(base.base1 + "/blog/" + data + "/disapprove")
    },
    dislikeComment(data) {
        return axios
            .get(base.base1 + "/comment/" + data + "/disapprove")
    }


}

export default userLike;