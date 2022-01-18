/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const userSocial = {
    getOtherFile(data) {
        return axios
            .get(base.base1 + "/visit/" + data)
    },
}

export default userSocial;