/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const personalArchive = {
    archiveBlog() {
        return instance.get("/customer/archivesBlog");
    },
    archiveQuestion() {
        return instance.get("/customer/archivesQuestion");
    },
    archiveComment() {
        return instance.get("/customer/archiveComment");
    },
    archiveLike() {
        return instance.get("/customer/archiveLike");
    }
};

export default personalArchive;