/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalMessage = {
    getAllMessage() {
        return axios
            .get(base.base1 + "/customer/messages/")
    },
    readComment(data) {
        return axios
            .get(base.base1 + "/customer/messages/comment/" +
                data + "/read")
    },
    readLike(data) {
        return axios
            .get(base.base1 + " /customer/messages/likes/ " + data +
                "/read")
    },
    readAllComments() {
        return axios
            .get(base.base1 + "/customer/messages/readAllComments")
    },
    readAllLikes() {
        return axios
            .get(base.base1 + "/customer/messages/readAllLikes")
    }
}

export default personalMessage;