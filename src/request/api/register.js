/**
 * article模块接口列表
 */

import base from "../base";
import axios from "axios";


const register = {
    register(data) {
        return axios
            .post(base.base1 + "/register", data)
    }
}

export default register;