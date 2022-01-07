import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

const data = () => import('../components/literature/data/data')
const information = () => import('../components/literature/Information/Information')
const literature = () => import('../components/literature/literature')

export default new Router({
        mode: "history",
        routes: [{
                path: '/',
                name: 'helloWorld',
                component: () =>
                    import ("../components/helloWorld")
            }, {
                path: '/helloWorld/logIn',
                name: 'logIn',
                component: () =>
                    import ("../components/logIn")
            },
            {
                path: '/helloWorld/register',
                name: 'register',
                component: () =>
                    import ("../components/register")
            },
            {
                path: '/helloWorld/admin/logIn',
                name: 'adminLogIn',
                component: () =>
                    import ("../components/admin/adminLogIn")
            },
            {
                path: '/helloWorld/model',
                name: 'model',
                component: () =>
                    import ("../components/user/model"),
                children: [{
                        path: '/helloWorld/admin',
                        name: 'admin',
                        component: () =>
                            import ("../components/admin/admin"),
                        children: [{
                            path: '/helloWorld/admin/adminTag',
                            name: 'adminTag',
                            component: () =>
                                import ("../components/admin/components/adminTag")
                        }, {
                            path: '/helloWorld/admin/adminUser',
                            name: 'adminUser',
                            component: () =>
                                import ("../components/admin/components/adminUser")
                        }, {
                            path: '/helloWorld/admin/adminBlog',
                            name: 'adminBlog',
                            component: () =>
                                import ("../components/admin/components/adminBlogFunctions/adminBlog")
                        }, {
                            path: '/helloWorld/admin/adminArticle',
                            name: 'adminArticle',
                            component: () =>
                                import ("../components/admin/components/adminArticleFunctions/adminArticle")
                        }, ]
                    }, {
                        path: '/helloWorld/home',
                        name: 'home',
                        component: () =>
                            import ("../components/user/home/home")
                    },
                    {
                        path: '/helloWorld/BBS',
                        name: 'BBS',
                        component: () =>
                            import ("../components/user/BBS/BBS"),
                        children: [{
                                path: '/helloWorld/BBS/question',
                                name: 'article',
                                component: () =>
                                    import ("../components/user/BBS/components/article/article"),
                                children: [{
                                        path: '/helloWorld/BBS/questions',
                                        name: 'articleContents',
                                        component: () =>
                                            import ("../components/user/BBS/components/article/components/articleContents")
                                    },
                                    {
                                        path: '/helloWorld/BBS/searchArticleResult',
                                        name: 'searchArticleResult',
                                        component: () =>
                                            import ("../components/user/BBS/components/article/components/searchArticleResult")
                                    }
                                ]
                            },
                            {
                                path: '/helloWorld/BBS/blog',
                                name: 'blog',
                                component: () =>
                                    import ("../components/user/BBS/components/blog/blog"),
                                children: [{
                                    path: '/helloWorld/BBS/blogs',
                                    name: 'blogContents',
                                    component: () =>
                                        import ("../components/user/BBS/components/blog/components/blogContents")
                                }, {
                                    path: '/helloWorld/BBS/searchBlogResult',
                                    name: 'searchBlogResult',
                                    component: () =>
                                        import ("../components/user/BBS/components/blog/components/searchBlogResult")
                                }]
                            },
                            {
                                path: '/helloWorld/BBS/article/addArticle',
                                name: 'addArticle',
                                component: () =>
                                    import ("../components/user/BBS/components/article/components/addArticle")
                            },
                            {
                                path: '/helloWorld/BBS/blog/addBlog',
                                name: 'addBlog',
                                component: () =>
                                    import ("../components/user/BBS/components/blog/components/addBlog")
                            }, {
                                path: '/helloWorld/BBS/rank',
                                name: 'rank',
                                component: () =>
                                    import ("../components/user/rank/rank")
                            }
                        ]
                    },
                    {
                        path: '/helloWorld/BBS/articleContent',
                        name: 'articleContent',
                        component: () =>
                            import ("../components/user/content/articleComponents/articleContent")
                    },
                    {
                        path: '/helloWorld/BBS/blogContent',
                        name: 'blogContent',
                        component: () =>
                            import ("../components/user/content/blogComponents/blogContent")
                    },
                    {
                        path: '/helloWorld/mine',
                        name: 'mine',
                        component: () =>
                            import ("../components/user/mine/mine"),
                        children: [{
                                path: '/helloWorld/mine/contents',
                                name: 'contents',
                                component: () =>
                                    import ("../components/user/mine/components/contents/contents"),
                                children: [{
                                    path: '/helloWorld/mine/contents/blogFiles',
                                    name: 'blogFiles',
                                    component: () =>
                                        import ("../components/user/mine/components/contents/blogFiles/blogFiles")
                                }, {
                                    path: '/helloWorld/mine/contents/questionFiles',
                                    name: 'questionFiles',
                                    component: () =>
                                        import ("../components/user/mine/components/contents/questionFiles/questionFiles")
                                }, {
                                    path: '/helloWorld/mine/contents/commentFiles',
                                    name: 'commentFiles',
                                    component: () =>
                                        import ("../components/user/mine/components/contents/commentFiles")
                                }]
                            },
                            {
                                path: '/helloWorld/mine/friends/following',
                                name: 'following',
                                component: () =>
                                    import ("../components/user/mine/components/friends/components/following")
                            }, {
                                path: '/helloWorld/mine/friends/followed',
                                name: 'followed',
                                component: () =>
                                    import ("../components/user/mine/components/friends/components/followed")
                            }, {
                                path: '/helloWorld/mine/information',
                                name: 'information',
                                component: () =>
                                    import ("../components/user/mine/components/information/information")
                            },
                            {
                                path: '/helloWorld/mine/message',
                                name: 'message',
                                component: () =>
                                    import ("../components/user/mine/components/message/message"),
                                children: [{
                                    path: '/helloWorld/mine/message/read',
                                    name: 'read',
                                    component: () =>
                                        import ("../components/user/mine/components/message/read/read")
                                }, {
                                    path: '/helloWorld/mine/message/unread',
                                    name: 'unread',
                                    component: () =>
                                        import ("../components/user/mine/components/message/unread/unread")
                                }]
                            },

                            {
                                path: '/helloWorld/mine/collections',
                                name: 'collections',
                                component: () =>
                                    import ("../components/user/mine/components/collections/collections")
                            },
                            {
                                path: '/helloWorld/mine/collections/favorite',
                                name: 'favorite',
                                component: () =>
                                    import ("../components/user/mine/components/collections/components/favorite")
                            }
                        ]
                    }, {
                        path: '/helloWorld/visitor',
                        name: 'visitor',
                        component: () =>
                            import ("../components/user/visitor/visitor"),
                        children: [{
                            path: '/helloWorld/visitor/questions',
                            name: 'questions',
                            component: () =>
                                import ("../components/user/visitor/components/questions")
                        }, {
                            path: '/helloWorld/visitor/blogs',
                            name: 'blogs',
                            component: () =>
                                import ("../components/user/visitor/components/blogs")
                        }, {
                            path: '/helloWorld/visitor/collections',
                            name: 'collections',
                            component: () =>
                                import ("../components/user/visitor/components/collections")
                        }, {
                            path: '/helloWorld/visitor/following',
                            name: 'following',
                            component: () =>
                                import ("../components/user/visitor/components/following")
                        }, {
                            path: '/helloWorld/visitor/collections/favorite',
                            name: 'favorite',
                            component: () =>
                                import ("../components/user/visitor/components/favorite")
                        }]

                    },
                    {
                        path: '/helloWorld/wikipedia',
                        name: 'wikipedia',
                        component: () =>
                            import ("../components/user/wikipedia/wikipedia")
                    },
                  {
                    path: '/helloWorld/literature',
                    redirect: '/helloWorld/literature/data',
                    component: literature,
                    children: [
                      {
                        path: '/helloWorld/literature/data',
                        component: data,
                      },
                      {
                        path: '/helloWorld/literature/information',
                        component: information
                      }
                    ]
                  }
                ]
            }
        ]
    })
    /*children: [{
                                path: '/helloWorld/visitor/blogFiles',
                                name: 'visitorBlogFiles',
                                component: () =>
                                    import ("../components/user/visitor/components/blogs")
                            },
                          {
                                path: '/helloWorld/visitor/questionFiles',
                                name: 'visitorQuestionFiles',
                                component: () =>
                                    import ("../components/user/visitor/components/questions")
                            },
                          {
                                path: '/helloWorld/visitor/following',
                                name: 'visitorFollowing',
                                component: () =>
                                    import ("../components/user/visitor/components/following")
                            },
                            {
                                path: '/helloWorld/visitor/collections',
                                name: 'visitorCollections',
                                component: () =>
                                    import ("../components/user/visitor/components/collections")
                            },
                            {
                                path: '/helloWorld/visitor/collections/favorite',
                                name: 'visitorFavorite',
                                component: () =>
                                    import ("../components/user/visitor/components/favorite")
                            }
                        ]*/
