// src/router/index.ts

import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomeView from "@/views/HomeView.vue";

import Administration from "@/components/Administration.vue";
import UserView from "@/views/UserView.vue";
import RoleView from "@/views/UserRoleView.vue";
import LoginView from "@/views/LoginView.vue";
import ParkingFacilityView from "@/views/ParkingFacilityView.vue";


const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: LoginView,
        meta: {
            requiresAuth: false
        }

    },
    {
        path: '/',
        name: 'home',
        component: HomeView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/administration',
        name: 'Administration',
        component: Administration,
        meta: {
            requiresAuth: true
        },
        children: [
            {
                path: 'users',
                name: 'User',
                component: UserView,
                meta: {
                    requiresAuth: true
                }
            },
            {
                path: 'roles',
                name: 'Role',
                component: RoleView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'parking-facilities',
                name: 'ParkingFacility',
                component: ParkingFacilityView,
                meta: {
                    requiresAuth: true,
                }
            },

        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // Verificar si hay token
        if (!sessionStorage.getItem('user')) { // Cambié 'basicAuth' a 'user'
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;