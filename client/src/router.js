import Vue from 'vue'
import Router from 'vue-router'
import routes from './router/routes'

Vue.use(Router)

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
	return routerPush.call(this, location).catch(error => error)
}

const router = new Router({
    routes
})

// router.beforeEach((to,from,next)=>{
//     if(to.matched.some((route)=>route.meta.Auth)){
//         next({
//             path:'/login',
//             query:{
//                 returnURL:to.path
//             }
//         })
//     }else{
//          next()
//     }
   
// })
export default router