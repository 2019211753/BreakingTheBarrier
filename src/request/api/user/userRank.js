/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userRank = {
    getRank() {
        return axios
            .get(base.base1 + "/rank")
    }
}

export default userRank;