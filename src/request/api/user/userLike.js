/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const userLike = {
    likeQuestion(data) {
        return instance.get("/question/" + data + "/approve");
    },
    likeBlog(data) {
        return instance.get("/blog/" + data + "/approve");
    },
    likeComment(data) {
        return instance.get("/comment/" + data + "/approve");
    },
    dislikeQuestion(data) {
        return instance.get("/question/" + data + "/disapprove");
    },
    dislikeBlog(data) {
        return instance.get("/blog/" + data + "/disapprove");
    },
    dislikeComment(data) {
        return instance.get("/comment/" + data + "/disapprove");
    }
};

export default userLike;