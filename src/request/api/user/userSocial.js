/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const userSocial = {
    getOtherFile(data) {
        return instance
            .get("/visit/" + data)
    },
    followOther(data) {
        return instance
            .get("/follow/" + data)
    }
}

export default userSocial;