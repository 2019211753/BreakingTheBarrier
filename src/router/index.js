import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const data = () =>
    import ("../components/literature/data/data");
const information = () =>
    import ("../components/literature/Information/Information");
const literature = () =>
    import ("../components/literature/literature");

export default new Router({
    mode: "history",
    routes: [{
            path: "/",
            name: "BreakingTheBarrier",
            component: () =>
                import ("../components/BreakingTheBarrier")
        },
        {
            path: "/BreakingTheBarrier/logIn",
            name: "logIn",
            component: () =>
                import ("../components/logIn")
        },
        {
            path: "/BreakingTheBarrier/register",
            name: "register",
            component: () =>
                import ("../components/register")
        },
        {
            path: "/BreakingTheBarrier/model",
            name: "model",
            component: () =>
                import ("../components/user/model"),
            children: [{
                    path: "/BreakingTheBarrier/admin",
                    name: "admin",
                    component: () =>
                        import ("../components/admin/admin"),
                    children: [{
                            path: "/BreakingTheBarrier/admin/adminTag",
                            name: "adminTag",
                            component: () =>
                                import ("../components/admin/components/adminTag")
                        },
                        {
                            path: "/BreakingTheBarrier/admin/adminUser",
                            name: "adminUser",
                            component: () =>
                                import ("../components/admin/components/adminUser")
                        },
                        {
                            path: "/BreakingTheBarrier/admin/adminBlog",
                            name: "adminBlog",
                            component: () =>
                                import (
                                    "../components/admin/components/adminBlogFunctions/adminBlog"
                                )
                        },
                        {
                            path: "/BreakingTheBarrier/admin/adminArticle",
                            name: "adminArticle",
                            component: () =>
                                import (
                                    "../components/admin/components/adminArticleFunctions/adminArticle"
                                )
                        }
                    ]
                },
                {
                    path: "/BreakingTheBarrier/home",
                    name: "home",
                    component: () =>
                        import ("../components/user/home/home")
                },
                {
                    path: "/BreakingTheBarrier/BBS",
                    name: "BBS",
                    component: () =>
                        import ("../components/user/BBS/BBS.vue"),
                    children: [{
                            path: "/BreakingTheBarrier/BBS/question",
                            name: "article",
                            component: () =>
                                import ("../components/user/BBS/components/article/article"),
                            children: [{
                                    path: "/BreakingTheBarrier/BBS/questions",
                                    name: "articleContents",
                                    component: () =>
                                        import (
                                            "../components/user/BBS/components/article/components/articleContents"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/BBS/searchArticleResult",
                                    name: "searchArticleResult",
                                    component: () =>
                                        import (
                                            "../components/user/BBS/components/article/components/searchArticleResult"
                                        )
                                }
                            ]
                        },
                        {
                            path: "/BreakingTheBarrier/BBS/blog",
                            name: "blog",
                            component: () =>
                                import ("../components/user/BBS/components/blog/blog"),
                            children: [{
                                    path: "/BreakingTheBarrier/BBS/blogs",
                                    name: "blogContents",
                                    component: () =>
                                        import (
                                            "../components/user/BBS/components/blog/components/blogContents"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/BBS/searchBlogResult",
                                    name: "searchBlogResult",
                                    component: () =>
                                        import (
                                            "../components/user/BBS/components/blog/components/searchBlogResult"
                                        )
                                }
                            ]
                        },
                        {
                            path: "/BreakingTheBarrier/BBS/article/addArticle",
                            name: "addArticle",
                            component: () =>
                                import (
                                    "../components/user/BBS/components/article/components/addArticle"
                                )
                        },
                        {
                            path: "/BreakingTheBarrier/BBS/blog/addBlog",
                            name: "addBlog",
                            component: () =>
                                import (
                                    "../components/user/BBS/components/blog/components/addBlog"
                                )
                        },
                        {
                            path: "/BreakingTheBarrier/BBS/rank",
                            name: "rank",
                            component: () =>
                                import ("../components/user/rank/rank")
                        }
                    ]
                },
                {
                    path: "/BreakingTheBarrier/BBS/articleContent",
                    name: "articleContent",
                    component: () =>
                        import (
                            "../components/user/content/articleComponents/articleContent"
                        )
                },
                {
                    path: "/BreakingTheBarrier/BBS/blogContent",
                    name: "blogContent",
                    component: () =>
                        import ("../components/user/content/blogComponents/blogContent")
                },
                {
                    path: "/BreakingTheBarrier/mine",
                    name: "mine",
                    component: () =>
                        import ("../components/user/mine/mine"),
                    children: [{
                            path: "/BreakingTheBarrier/mine/contents",
                            name: "contents",
                            component: () =>
                                import ("../components/user/mine/components/contents/contents"),
                            children: [{
                                    path: "/BreakingTheBarrier/mine/contents/blogFiles",
                                    name: "blogFiles",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/contents/blogFiles"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/mine/contents/questionFiles",
                                    name: "questionFiles",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/contents/questionFiles"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/mine/contents/commentFiles",
                                    name: "commentFiles",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/contents/commentFiles"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/mine/contents/likeFiles",
                                    name: "likeFiles",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/contents/likeFiles"
                                        )
                                }
                            ]
                        },
                        {
                            path: "/BreakingTheBarrier/mine/friends/following",
                            name: "following",
                            component: () =>
                                import ("../components/user/mine/components/friends/following")
                        },
                        {
                            path: "/BreakingTheBarrier/mine/friends/followed",
                            name: "followed",
                            component: () =>
                                import ("../components/user/mine/components/friends/followed")
                        },
                        {
                            path: "/BreakingTheBarrier/mine/information",
                            name: "information",
                            component: () =>
                                import (
                                    "../components/user/mine/components/information/information"
                                )
                        },
                        {
                            path: "/BreakingTheBarrier/mine/message",
                            name: "message",
                            component: () =>
                                import ("../components/user/mine/components/message/message"),
                            children: [{
                                    path: "/BreakingTheBarrier/mine/message/read",
                                    name: "read",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/message/components/read"
                                        )
                                },
                                {
                                    path: "/BreakingTheBarrier/mine/message/unread",
                                    name: "unread",
                                    component: () =>
                                        import (
                                            "../components/user/mine/components/message/components/unread"
                                        )
                                }
                            ]
                        },

                        {
                            path: "/BreakingTheBarrier/mine/collections",
                            name: "collections",
                            component: () =>
                                import (
                                    "../components/user/mine/components/collections/collections"
                                )
                        },
                        {
                            path: "/BreakingTheBarrier/mine/collections/favorite",
                            name: "favorite",
                            component: () =>
                                import (
                                    "../components/user/mine/components/collections/components/favorite"
                                )
                        }
                    ]
                },
                {
                    path: "/BreakingTheBarrier/visitor",
                    name: "visitor",
                    component: () =>
                        import ("../components/user/visitor/visitor"),
                    children: [{
                            path: "/BreakingTheBarrier/visitor/questions",
                            name: "questions",
                            component: () =>
                                import ("../components/user/visitor/components/questions")
                        },
                        {
                            path: "/BreakingTheBarrier/visitor/blogs",
                            name: "blogs",
                            component: () =>
                                import ("../components/user/visitor/components/blogs")
                        },
                        {
                            path: "/BreakingTheBarrier/visitor/collections",
                            name: "collections",
                            component: () =>
                                import ("../components/user/visitor/components/collections")
                        },
                        {
                            path: "/BreakingTheBarrier/visitor/following",
                            name: "following",
                            component: () =>
                                import ("../components/user/visitor/components/following")
                        },
                        {
                            path: "/BreakingTheBarrier/visitor/collections/favorite",
                            name: "favorite",
                            component: () =>
                                import ("../components/user/visitor/components/favorite")
                        }
                    ]
                },
                {
                    path: "/BreakingTheBarrier/wikipedia",
                    name: "wikipedia",
                    component: () =>
                        import ("../components/user/wikipedia/wikipedia")
                },
                {
                    path: "/BreakingTheBarrier/literature",
                    redirect: "/BreakingTheBarrier/literature/data",
                    component: literature,
                    children: [
                      {
                      path: "/BreakingTheBarrier/literature/data",
                      component: data
                      },
                      {
                        path: "/BreakingTheBarrier/literature/information",
                        component: information,
                        // children: [
                        //   {
                        //     path: "/BreakingTheBarrier/literature/information/EntryCreate",
                        //     component: () => import("../components/literature/Information/EntryCreate")
                        //   },
                        //   {
                        //     path: "/BreakingTheBarrier/literature/information/EntryUnapproved",
                        //     component: () => import("../components/literature/Information/components/ShowUnproved")
                        //   },
                        //   {
                        //     path: "/BreakingTheBarrier/literature/information/EntryDisplay",
                        //     component: () => import("../components/literature/Information/components/EntryDisplay")
                        //   }
                        //   // {
                        //   //    path: "/BreakingTheBarrier/literature/information/EntryUnapproved",
                        //   //    component: () => import("../components/literature/Information/components/ShowUnproved")
                        //   // }
                        // ]
                      },
                      {
                        path: "/BreakingTheBarrier/literature/information/EntryCreate",
                        component: () => import("../components/literature/Information/EntryCreate")
                      },
                      {
                        path: "/BreakingTheBarrier/literature/information/EntryItem",
                        component: () => import("../components/literature/Information/EntryItem")
                      }

                    ]
                },
            ]
        }
    ]
});
