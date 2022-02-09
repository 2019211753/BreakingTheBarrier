/**
 * article模块接口列表
 */

import base from "../base";
import axios from "axios";

const logIn = {
    logIn(data) {
        return axios.post(base.base1 + "/login", data);
    }
};

export default logIn;