/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalFollow = {
    getFollowInfo() {
        return axios
            .get(base.base1 + "/customer/followInfo")
    }
}

export default personalFollow;