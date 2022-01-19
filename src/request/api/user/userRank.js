/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const userRank = {
    getRank() {
        return instance.get("/rank")
    }
}

export default userRank;