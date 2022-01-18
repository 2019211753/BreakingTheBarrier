/**
 * api接口的统一出口
 */
// 文章模块接口
import adminBlog from "./api/admin/adminBlog";
import adminQuestion from "./api/admin/adminQuestion";
import adminTag from "./api/admin/adminTag";
import adminUser from "./api/admin/adminUser";
import personalArchive from "./api/personal/personalArchive";
import personalBlog from "./api/personal/personalBlog";
import personalFavorite from "./api/personal/personalFavorite";
import personalFollow from "./api/personal/personalFollow";
import personalInformation from "./api/personal/personalInformation";
import personalMessage from "./api/personal/personalMessage";
import personalQuestion from "./api/personal/personalQuestion";
import userArticle from "./api/user/userArticle";
import userComment from "./api/user/userComment";
import userFavorite from "./api/user/userFavorite";
import userLike from "./api/user/userLike";
import userRank from "./api/user/userRank";
import userSocial from "./api/user/userSocial";
import userTag from "./api/user/userTag";
import logIn from "./api/logIn";
import register from "./api/register";



// 导出接口
export default {
    adminBlog,
    adminQuestion,
    adminTag,
    adminUser,
    personalArchive,
    personalBlog,
    personalFavorite,
    personalFollow,
    personalInformation,
    personalMessage,
    personalQuestion,
    userArticle,
    userComment,
    userFavorite,
    userLike,
    userRank,
    userSocial,
    userTag,
    logIn,
    register
}