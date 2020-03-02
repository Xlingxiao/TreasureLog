import layout from '../components/Layout'
import treasure from '../components/page/treasure'
import fund from '../components/page/fund'

const routes = [
    {
        path: '/treasure',
        component: treasure,
    }, {
        path: '/fund',
        component: fund,
    }, {
        path: '/extend',
        component: () => import('../components/page/expend'),
    }
    // {
    //     path:'/login',
    //     component:Login
    // }
]
export default routes