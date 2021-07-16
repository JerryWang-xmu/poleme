import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Home from '../components/User/Home.vue'
import Mainpage from '../components/User/Mainpage.vue'
import Information from '../components/User/Information.vue'
import Management from '../components/Admain/Management.vue'
import Secret from "../components/User/Secret"
import Goods from '../components/Admain/Goods.vue'
import Star from '../components/User/Star.vue'
import Coffee from "../components/User/Coffee"
import Food from "../components/User/Food"
import Drink from "../components/User/Drink"
import ManageMenu from '../components/Admain/ManageMenu.vue'
import ShoppingCart from '../components/User/ShoppingCart.vue'
import OrderList from '../components/User/OrderLsit.vue'
import ManageOrder from "../components/Admain/ManageOrder.vue"
import Rank from "../components/Admain/Rank.vue"


Vue.use(VueRouter)

// 用于解决重复导航问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
const routes = [
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        component: Login
    },
    {
        path: "/register",
        component: Register
    },
    {
        path: "/home",
        component: Home,
        redirect: '/mainpage',

        children: [
            {path: '/mainpage', component: Mainpage},
            {path: '/information', component: Information},
            {path: '/management', component: Management},
            {path:'/secret',component: Secret},
            {path:'/goods',component: Goods},
            {path:'/star',component: Star},
            {path:'/coffee',component: Coffee},
            {path:'/food',component: Food},
            {path:'/drink',component: Drink},
            {path:'/shoppingcart',component: ShoppingCart},
            {path:'/orderlist',component: OrderList},
            {path:'/managemenu',component: ManageMenu},
            {path:'/manageorder',component: ManageOrder},
            {path:'/rank',component: Rank},
        ]
    }
]


const router = new VueRouter({
    routes
})

export default router
