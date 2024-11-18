// src/router/index.ts

import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomeView from "@/views/HomeView.vue";

import Administration from "@/components/Administration.vue";
import UserView from "@/views/UserView.vue";
import RoleView from "@/views/UserRoleView.vue";
import LoginView from "@/views/LoginView.vue";
import ParkingFacilityView from "@/views/ParkingFacilityView.vue";
import ParkingSpotView from "@/views/ParkingSpotView.vue";
import ScheduleVIew from "@/views/ScheduleVIew.vue";
import DayOfWeekView from "@/views/DayOfWeekView.vue";
import DailyScheduleView from "@/views/DailyScheduleView.vue";
import AddServiceView from "@/views/AddServiceView.vue";
import FeeView from "@/views/FeeView.vue";
import PayMethodView from "@/views/PayMethodView.vue";
import ReservationView from "@/views/ReservationView.vue";
import ServiceReservationView from "@/views/ServiceReservationView.vue";
import VehicleTypeView from "@/views/VehicleTypeView.vue";
import ReservationStatusView from "@/views/ReservationStatusView.vue";


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
            {
                path: 'parking-spots',
                name: 'ParkingSpot',
                component: ParkingSpotView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'schedules',
                name: 'Schedule',
                component: ScheduleVIew,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'days-week',
                name: 'DayOfWeeks',
                component: DayOfWeekView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'daily-schedules',
                name: 'DailySchedule',
                component: DailyScheduleView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'additional-services',
                name: 'AdditionalServices',
                component: AddServiceView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'fees',
                name: 'Fee',
                component: FeeView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'pay-methods',
                name: 'PayMethod',
                component: PayMethodView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'reservations',
                name: 'Reservation',
                component: ReservationView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'service-reservations',
                name: 'ServiceReservation',
                component: ServiceReservationView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'vehicle-types',
                name: 'VehicleType',
                component: VehicleTypeView,
                meta: {
                    requiresAuth: true,
                }
            },
            {
                path: 'reservations-status',
                name: 'ReservationStatus',
                component: ReservationStatusView,
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