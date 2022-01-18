/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const adminBlog = {
    serchBlogs(data) {
        return axios
            .post(base.base1 + "/admin/searchBlogs", {
                query: data,
            })
    }
}

export default adminBlog;