/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const adminQuestion = {
    searchQuestions(data) {
        return axios
            .post(base.base1 + "/admin/searchQuestions", {
                query: data,
            })
    },
    editeQuestion() {}

}

export default adminQuestion;