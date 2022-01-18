/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalQuestion = {
    serchQuestions(data) {
        return axios
            .post(base.base1 + "/searchQuestions/", { query: data })
    },
    postQuestion(data) {
        return axios
            .post(base.base1 + "/customer/question/post", data)
    },
    deleteQuestion(data) {
        return axios
            .get(base.base1 + "/customer/question/" + data + "/delete")
    }
}

export default personalQuestion;