/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const adminQuestion = {
    searchQuestions(data) {
        return instance
            .post(base.base1 + "/admin/searchQuestions", {
                query: data,
            })
    },
    editeQuestion() {}

}

export default adminQuestion;