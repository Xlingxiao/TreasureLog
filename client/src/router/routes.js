import layout from '../components/Layout'
import treasure from '../components/page/treasure'

const routes = [
    {
        path: '/',
        redirect: '/treasure' 
    }, {
        path: '/treasure',
        component: treasure,
    }, {
        path:'/invest',
        redirect:'/invest/fund',
    },  {
        path: '/invest/fund',
        component:() => import('../components/page/invest/fund')
    },{
        path: '/invest/stock',
        component:() => import('../components/page/invest/stock')
    },{
        path: '/extend',
        component: () => import('../components/page/expend'),
    },{
        path:'/log/*',
        component:() => import('../components/page/log')
    },{
        path: '/wealthCurve',
        component: () => import('../components/page/wealthCurve'),
    },
    // {
    //     path:'/login',
    //     component:() => import('../components/auth/login')
    // }
]
export default routes