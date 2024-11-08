// src/router/index.ts

import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomeView from "@/Views/HomeView.vue";
import Login from "@/components/LoginComponent.vue";
import RoleComponent from "@/components/RoleComponent.vue";
import UserComponent from "@/components/UserComponent.vue";
import Administration from "@/components/Administration.vue";
import UserView from "@/Views/UserView.vue";
import RoleView from "@/Views/RoleView.vue";


const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/administration',
        name: 'Administration',
        component: Administration,
        children: [
            {
                path: 'users',
                name: 'User',
                component: UserView,
            },
            {
                path: 'roles',
                name: 'Role',
                component: RoleView,
            },

        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = !!localStorage.getItem('username');

    if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {

        next({ name: 'Login' });
    } else {
        next();
    }
});

export default router;