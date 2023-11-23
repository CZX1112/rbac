import Vue from 'vue'
import VueRouter from 'vue-router'

/* Layout */
import Layout from '@/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout, // 修改为 Layout 组件
    children: [
      {
        path: '',
        component: () => import( '../views/HomeView.vue'),
        name: 'home'
      },
    ],
  },
  {
    path: '/rbac',
    component: Layout,
    children: [{
        path: 'user/list',
        component:() => import( '../views/User/List.vue')
      },
      {
        path: 'user/add',
        component:() => import( '../views/User/Add.vue')
      },
      {
        path: 'user/manage',
        component:() => import( '../views/User/Manage.vue')
      },
      {
        path: 'role/list',
        component:() => import( '../views/Role/List.vue')
      },
      {
        path: 'role/manage',
        component:() => import( '../views/Role/Manage.vue')
      },
      {
        path: 'role/add',
        component:() => import( '../views/Role/Add.vue')
      },
      {
        path: 'permission/list',
        component:() => import( '../views/Permission/List.vue')
      },
      {
        path: 'permission/add',
        component:() => import( '../views/Permission/Add.vue')
      },
      {
        path: 'permission/manage',
        component:() => import( '../views/Permission/Manage.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}


export default router
