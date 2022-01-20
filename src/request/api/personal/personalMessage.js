/**
 * article模块接口列表
 */

import base from "../../base";

import instance from "../../http";

const personalMessage = {
    getAllMessage() {
        return instance
            .get("/customer/messages/")
    },
    readComment(data) {
        return instance
            .get("/customer/messages/comment/" +
                data + "/read")
    },
    readLike(data) {
        return instance
            .get("/customer/messages/likes/" + data +
                "/read")
    },
    readAllComments() {
        return instance
            .get("/customer/messages/readAllComments")
    },
    readAllLikes() {
        return instance
            .get("/customer/messages/readAllLikes")
    }
}

export default personalMessage;
