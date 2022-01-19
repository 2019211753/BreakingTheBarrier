/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const adminBlog = {
    serchBlogs(data) {
        return instance
            .post(base.base1 + "/admin/searchBlogs", data)
    }
}

export default adminBlog;