/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const userTag = {
    getTags() {
        return instance
            .get("/tags/")
    },
}

export default userTag;