/**
 * article模块接口列表
 */

import base from "../../base";

import instance from "../../http";

const userBlog = {
    serchBlogs(data) {
        return instance.post("/searchBlogs/", data)
    },
    postBlog(data) {
        return instance
            .post("/customer/blog/post", data)
    },
    deleteBlog(data) {
        return instance
            .get("/customer/blog/" + data + "/delete")
    }
}

export default userBlog;