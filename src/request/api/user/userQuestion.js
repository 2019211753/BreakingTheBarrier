/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const userQuestion = {
    serchQuestions(data) {
        return instance
            .post("/searchQuestions/", data)
    },
    postQuestion(data) {
        return instance
            .post("/customer/question/post", data)
    },
    deleteQuestion(data) {
        return instance
            .get("/customer/question/" + data + "/delete")
    }
}

export default userQuestion;