import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [{
    path: '/',
    name: 'helloWorld',
    component: () => import("../components/helloWorld")
  }, {
    path: '/helloWorld/logIn',
    name: 'logIn',
    component: () => import("../components/logIn")
  },
    {
      path: '/helloWorld/register',
      name: 'register',
      component: () => import("../components/register")
    },
    {
      path: '/helloWorld/admin/logIn',
      name: 'adminLogIn',
      component: () => import("../components/admin/adminLogIn")
    },
    {
      path: '/helloWorld/admin',
      name: 'admin',
      component: () => import("../components/admin/admin")
    }, {
      path: '/helloWorld/admin/adminBlog',
      name: 'adminBlog',
      component: () => import("../components/admin/components/adminBlogFunctions/adminBlog")
    }, {
      path: '/helloWorld/admin/adminArticle',
      name: 'adminArticle',
      component: () => import("../components/admin/components/adminArticleFunctions/adminArticle")
    }, {
      path: '/helloWorld/model',
      name: 'model',
      component: () => import("../components/user/model"),
      children: [{
        path: '/helloWorld/home',
        name: 'home',
        component: () => import("../components/user/home/home")
      },
        {
          path: '/helloWorld/BBS',
          name: 'BBS',
          component: () => import("../components/user/BBS/BBS"), children: [{
            path: '/helloWorld/BBS/question',
            name: 'article',
            component: () => import("../components/user/BBS/components/article/article"),
            children: [{
              path: '/helloWorld/BBS/questions',
              name: 'articleContents',
              component: () => import("../components/user/BBS/components/article/components/articleContents")
            },
              {
                path: '/helloWorld/BBS/searchArticleResult',
                name: 'searchArticleResult',
                component: () => import( "../components/user/BBS/components/article/components/searchArticleResult")
              }
            ]
          }, {
            path: '/helloWorld/BBS/blog',
            name: 'blog',
            component: () => import("../components/user/BBS/components/article/article"),
            children: [{
              path: '/helloWorld/BBS/blogs',
              name: 'blogContents',
              component: () => import("../components/user/BBS/components/blog/components/blogContents")
            }, {
              path: '/helloWorld/BBS/searchBlogResult',
              name: 'searchBlogResult',
              component: () => import("../components/user/BBS/components/blog/components/searchBlogResult")
            }]
          }, {
            path: '/helloWorld/BBS/article/addArticle',
            name: 'addArticle',
            component: () => import("../components/user/BBS/components/article/components/addArticle")
          }, {
            path: '/helloWorld/BBS/blog/addBlog',
            name: 'addBlog',
            component: () => import("../components/user/BBS/components/blog/components/addBlog")
          }]
        },
        {
          path: '/helloWorld/BBS/articleContent',
          name: 'articleContent',
          component: () => import("../components/user/content/articleComponents/articleContent")
        },
        {
          path: '/helloWorld/BBS/blogContent',
          name: 'blogContent',
          component: () => import("../components/user/content/blogComponents/blogContent")
        },
        {
          path: '/helloWorld/mine',
          name: 'mine',
          component: () => import("../components/user/mine/mine"),
          children: [{
            path: '/helloWorld/mine/blogFiles',
            name: 'blogFiles',
            component: () => import("../components/user/mine/components/blogFiles/blogFiles")
          }, {
            path: '/helloWorld/mine/friends',
            name: 'friends',
            component: () => import("../components/user/mine/components/friends/friends"),
            children: [{
              path: '/helloWorld/mine/friends/following',
              name: 'following',
              component: () => import("../components/user/mine/components/friends/components/following")
            }, {
              path: '/helloWorld/mine/friends/followed',
              name: 'followed',
              component: () => import("../components/user/mine/components/friends/components/followed")
            }]
          }, {
            path: '/helloWorld/mine/information',
            name: 'information',
            component: () => import("../components/user/mine/components/information/information")
          }, {
            path: '/helloWorld/mine/message',
            name: 'message',
            component: () => import("../components/user/mine/components/message/message")
          }, {
            path: '/helloWorld/mine/questionFiles',
            name: 'questionFiles',
            component: () => import("../components/user/mine/components/questionFiles/questionFiles")
          }, {
            path: '/helloWorld/mine/collections',
            name: 'collections',
            component: () => import("../components/user/mine/components/collections/collections")
          }, {
            path: '/helloWorld/mine/collections/favorite',
            name: 'favorite',
            component: () => import("../components/user/mine/components/collections/components/favorite")
          }]
        },
        {
          path: '/helloWorld/wikipedia',
          name: 'wikipedia',
          component: () => import("../components/user/wikipedia/wikipedia")
        },
        {
          path: '/helloWorld/rank',
          name: 'rank',
          component: () => import("../components/user/rank/rank")
        }
      ]
    }
  ]
})
