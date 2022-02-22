/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const userComment = {
    postQuestionComment(data1, data2) {
        return instance.post("/question/" + data1 + "/comment/post", data2);
    },
    postBlogComment(data1, data2) {
        return instance.post("/blog/" + data1 + "/comment/post", data2);
    },
    getAllQuestionComments(data) {
        return instance.get("/question/" + data + "/comments");
    },
    getAllBlogComments(data) {
        return instance.get("/blog/" + data + "/comments");
    },
    getChildComments(data) {
        return instance.get("/comment/" + data);
    },
    deleteQuestionComment(data1, data2) {
        return instance.get("/question/" + data1 + "/comment/" + data2 + "/delete");
    },
    deleteBlogComment(data1, data2) {
        return instance.get(
            "/blog/" + data1 + "/comment/" + data2 + "/delete"
        );
    },
    getCommentSelected(data1, data2) {
        return instance.get("/question/" + data1 + "/comment/" + data2 + "/select");
    }
};

export default userComment;
