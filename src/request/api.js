/**
 * api接口的统一出口
 */
// 文章模块接口
import adminBlog from "./api/admin/adminBlog";
import adminQuestion from "./api/admin/adminQuestion";
import adminTag from "./api/admin/adminTag";
import adminUser from "./api/admin/adminUser";
import personalArchive from "./api/personal/personalArchive";
import userBlog from "./api/user/userBlog";
import personalFavorite from "./api/personal/personalFavorite";
import personalFollow from "./api/personal/personalFollow";
import personalInformation from "./api/personal/personalInformation";
import personalMessage from "./api/personal/personalMessage";
import userQuestion from "./api/user/userQuestion";
import userArticle from "./api/user/userArticle";
import userComment from "./api/user/userComment";
import userFavorite from "./api/user/userFavorite";
import userLike from "./api/user/userLike";
import userRank from "./api/user/userRank";
import userSocial from "./api/user/userSocial";
import userTag from "./api/user/userTag";
import logIn from "./api/logIn";
import register from "./api/register";
import dataFind from "./api/data/dataFind";
import dataUpload from "./api/data/dataUpload";
import dataDown from "./api/data/dataDown";
import infoGetUnapro from "./api/information/infoGetUnapro";
import infoShow from "./api/information/infoShow";
import infoCreate from "./api/information/infoCreate";
import infoApprove from "./api/information/infoApprove";
import infoUpdate from "./api/information/infoUpdate";



// 导出接口
export default {
  adminBlog,
  adminQuestion,
  adminTag,
  adminUser,
  personalArchive,
  userBlog,
  personalFavorite,
  personalFollow,
  personalInformation,
  personalMessage,
  userQuestion,
  userArticle,
  userComment,
  userFavorite,
  userLike,
  userRank,
  userSocial,
  userTag,
  logIn,
  register,
  dataFind,
  dataUpload,
  dataDown,
  infoGetUnapro,
  infoShow,
  infoCreate,
  infoApprove,
  infoUpdate
}
