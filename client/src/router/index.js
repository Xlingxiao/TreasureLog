import Vue from 'vue'
import Router from 'vue-router'
import treasure from 'views/treasure/Treasure'

Vue.use(Router)

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
	return routerPush.call(this, location).catch(error => error)
}

const routes = [
    {
        path: '/',
        redirect: '/index'
    }, {
        path: '/index',
        component: () => import('layout/Layout'),
        redirect: '/index/treasure',
        children: [
            {
                path: 'treasure',
                component: treasure,
            }, {
                path: 'invest',
                redirect: 'invest/fund',
            }, {
                path: 'invest/fund',
                component: () => import('views/invest/Fund')
            }, {
                path: 'invest/stock',
                component: () => import('views/invest/Stock')
            }, {
                path: 'consumeInfo',
                component: () => import('views/expend/ConsumeInfo'),
            }, {
                path: 'extend',
                component: () => import('views/expend/Expend'),
            }, {
                path: 'log/*',
                component: () => import('views/log/Log')
            }, {
                path: 'wealthCurve',
                component: () => import('views/wealth/WealthCurve'),
            }, {
                path: 'text/main',
                component: () => import('views/text/TextMain'),
            }, {
                path: 'text/show',
                component: () => import('views/text/TextShow'),
            }, {
                path: 'edit',
                component: () => import('views/edit/EditRowData'),
            },
        ]
    }, {
        path: '/login',
        component: () => import('views/login/Login'),
    },
]

const router = new Router({
    routes
})

export default router
