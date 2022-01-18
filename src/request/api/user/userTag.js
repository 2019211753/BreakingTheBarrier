/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userTag = {
    getTags() {
        return axios
            .get(base.base1 + "/tags/")
    },
}

export default userTag;