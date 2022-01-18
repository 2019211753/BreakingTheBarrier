/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userFavorite = {
    collectQuestion(data1, data2) {
        return axios
            .get(base.base1 + "/customer/favorite/" + data1 + "/modify/question/" + data2)
    },
    collectBlog(data1, data2) {
        return axios
            .get(base.base1 + "/customer/favorite/" + data1 + "/modify/blog/" + data2)
    }
}

export default userFavorite;