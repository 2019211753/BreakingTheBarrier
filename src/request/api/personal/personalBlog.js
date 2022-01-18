/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalBlog = {
    serchBlogs(data) {
        return axios
            .post(base.base1 + "/searchBlogs/", { query: data })
    },
    postBlog(data) {
        return axios
            .post(base.base1 + "/customer/blog/post", data)
    },
    deleteBlog(data) {
        return axios
            .get(base.base1 + "/customer/blog/" + data + "/delete")
    }
}

export default personalBlog;