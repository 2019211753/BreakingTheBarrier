/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const personalFollow = {
    getFollowInfo() {
        return instance.get("/customer/followInfo")
    }
}

export default personalFollow;