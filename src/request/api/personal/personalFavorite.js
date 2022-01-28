/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const personalFavorite = {
    createFavorite(data) {
        return instance.post("/customer/favorite/create", data);
    },
    deleteFavorite(data) {
        return instance.get("/customer/favorite/" + data + "/delete");
    },
    editFavorite(data) {
        return instance.post("/customer/favorite/edit", data);
    },
    getFavorites() {
        return instance.get("/customer/favorites");
    },
    showFavoriteContents(data) {
        return instance.get("/customer/favorite/" + data);
    }
};

export default personalFavorite;