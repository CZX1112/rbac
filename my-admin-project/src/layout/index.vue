<template>
    <div>
        <div>
            <div class="side-bar">
                <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff"
                    active-text-color="#ffd04b">
                    <el-menu-item index="/" @click="$router.push('/')">
                        <i class="el-icon-house"></i>                        
                        <span slot="title">首页</span>
                    </el-menu-item>
                    <el-menu-item v-for="menu in userMenus" :key="menu.id" :index="menu.url"
                        @click="handleMenuClick(menu.url)">
                        <!-- <i class="el-icon-house"></i> -->
                        <i :class="[getIconClass(menu.menu_name)]"></i>
                        <span slot="title">{{ menu.menu_name }}</span>
                    </el-menu-item>

                    <el-menu-item index="/logout" @click="logout">
                        <i class="el-icon-setting"></i>
                        <span slot="title">退出</span>
                    </el-menu-item>

                </el-menu>

                <div class="main-content">
                    <router-view />
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import { removeToken, getAccessToken } from '@/utils/auth'
import { getMenu } from '@/api/user.js'
import {logout} from '@/api/login.js'
export default {
    name: "MyComponent",
    data() {
        return {
            userMenus: [
            ]
        }
    },
    methods: {
        getIconClass(menuName) {
            if (menuName.includes('用户')) {
                return 'el-icon-user'; // 用户风格的图标
            } else if (menuName.includes('角色')) {
                return 'el-icon-suitcase'; // 角色风格的图标
            } else if (menuName.includes('权限')) {
                return 'el-icon-lock'; // 权限风格的图标
            } else {
                return 'el-icon-house'; // 默认图标
            }
        },
        logout() {
            this.$confirm('确定注销并退出系统吗？', '提示').then(() => {
                logout().catch((e) => {console.log(e)})
                removeToken() // 清除token
                this.$router.push({ path: '/login' }) // 重定向到登录页面
            }).catch(() => { });
        },
        fetchUserMenus(token) {
            getMenu(token).then((res) => {
                this.userMenus = res.data;
            })
        },
        handleMenuClick(url) {
            this.$router.push(url)
        }
    },
    mounted() {
        this.fetchUserMenus(getAccessToken());
    },
};
</script>


<style lang="scss" scoped>
.side-bar {
    display: flex;
    height: 100vh;
}

.main-content {
    flex: 1;
    padding: 20px;
}
</style>
  