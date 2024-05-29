import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [

    // ==== 登陆 ===
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue')
  },

    // ==== 主页 ===
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children:[  //子路由

      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/HomeView.vue')
      },

      //用户User
      {
        path: 'myuser',
        name: 'MyUser',
        component: () => import('@/views/user/User.vue')
      },

      {
        path: 'addUser',
        name: 'AddUser',
        component: () => import('@/views/user/AddUser.vue')
      },

      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('@/views/user/EditUser.vue')
      },

      //管理员Admin
      {
        path: 'myadmin',
        name: 'MyAdmin',
        component: () => import('@/views/admin/Admin.vue')
      },

      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('@/views/admin/AddAdmin.vue')
      },

      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin/EditAdmin.vue')
      },

    ]
  },

    // ==== 404界面 ===
  {
    path:'*',
    name: '404',
    component: () => import('@/views/404.vue')
  }
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{

  if(to.path === "/login"){
    next()
  }else{
    let admin = Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}

    if(admin.isAuth === 1){
      next()
    }else{
      next("/login")
    }
  }

})

export default router
