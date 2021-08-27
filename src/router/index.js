import Vue from 'vue'
import Router from 'vue-router'
import helloWorld from "../components/helloWorld";
import logIn from "../components/logIn";
import register from "../components/register";
import model from "../components/user/model";
import home from "../components/user/home/home";

import mine from "../components/user/mine/mine";
import wikipedia from "../components/user/wikipedia/wikipedia";
import BBS from "../components/user/BBS/BBS";
import details from "../components/user/content/articleComponents/content";
import addArticle from "../components/user/BBS/components/addArticle";
import blogFiles from "../components/user/mine/components/blogFiles/blogFiles";
import friends from "../components/user/mine/components/friends/friends";
import information from "../components/user/mine/components/information/information";
import message from "../components/user/mine/components/message/message";
import questionFiles from "../components/user/mine/components/questionFiles/questionFiles";
import collections from "../components/user/mine/components/collections/collections";
import favorite from "../components/user/mine/components/collections/components/favorite";
import rank from "../components/user/rank/rank";

import articleContents from "../components/user/BBS/components/articleContents";
import blogContents from "../components/user/BBS/components/blogContents";
Vue.use(Router)

export default new Router({
    mode: "history",
    routes: [{
            path: '/',
            name: 'helloWorld',
            component: helloWorld,
        }, {
            path: '/helloWorld/logIn',
            name: 'logIn',
            component: logIn
        },
        {
            path: '/helloWorld/register',
            name: 'register',
            component: register
        }, {
            path: '/helloWorld/model',
            name: 'model',
            component: model,
            children: [{
                path: '/helloWorld/home',
                name: 'home',
                component: home
            }, {
                path: '/helloWorld/BBS',
                name: 'BBS',
                component: BBS,
                children: [{
                    path: '/helloWorld/BBS/question',
                    name: 'articleContents',
                    component: articleContents
                }, {
                    path: '/helloWorld/BBS/blog',
                    name: 'blogContents',
                    component: blogContents
                }]
            }, {
                path: '/BBS/content',
                name: 'details',
                component: details
            }, {
                path: '/BBS/addArticle',
                name: 'addArticle',
                component: addArticle
            }, {
                path: '/helloWorld/mine',
                name: 'mine',
                component: mine,
                children: [{
                    path: '/helloWorld/mine/blogFiles',
                    name: 'blogFiles',
                    component: blogFiles
                }, {
                    path: '/helloWorld/mine/friends',
                    name: 'friends',
                    component: friends
                }, {
                    path: '/helloWorld/mine/information',
                    name: 'information',
                    component: information
                }, {
                    path: '/helloWorld/mine/message',
                    name: 'message',
                    component: message
                }, {
                    path: '/helloWorld/mine/questionFiles',
                    name: 'questionFiles',
                    component: questionFiles
                }, {
                    path: '/helloWorld/mine/collections',
                    name: 'collections',
                    component: collections
                }, {
                    path: '/helloWorld/mine/collections/favorite',
                    name: 'favorite',
                    component: favorite
                }]
            }, {
                path: '/helloWorld/wikipedia',
                name: 'wikipedia',
                component: wikipedia
            }, {
                path: '/helloWorld/rank',
                name: 'rank',
                component: rank
            }]
        }
    ]
})
