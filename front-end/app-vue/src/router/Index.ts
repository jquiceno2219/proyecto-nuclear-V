// src/router/index.ts

import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomeView from "@/views/HomeView.vue";

import Administration from "@/components/Administration.vue";
import UserView from "@/views/UserView.vue";
import RoleView from "@/views/UserRoleView.vue";

import ParkingFacilityView from "@/views/ParkingFacilityView.vue";
import ParkingSpotView from "@/views/ParkingSpotView.vue";

import DayOfWeekView from "@/views/DayOfWeekView.vue";
import DailyScheduleView from "@/views/DailyScheduleView.vue";
import AddServiceView from "@/views/AddServiceView.vue";
import FeeView from "@/views/FeeView.vue";
import PayMethodView from "@/views/PayMethodView.vue";
import ReservationView from "@/views/ReservationView.vue";
import ServiceReservationView from "@/views/ServiceReservationView.vue";
import VehicleTypeView from "@/views/VehicleTypeView.vue";
import ReservationStatusView from "@/views/ReservationStatusView.vue";
import ScheduleView from "@/views/ScheduleView.vue";
import NavbarSwitcher from "@/components/navbars/NavbarSwitcher.vue";
import type {User} from "@/models/User";
import UserProfileView from "@/views/UserProfileView.vue";
import LoginView from "@/views/LoginView.vue";
import PublicReservationView from "@/views/PublicReservationView.vue";
import PaymentView from "@/views/PaymentView.vue";



/**
 * Configuración de rutas de la aplicación con Vue Router.
 * Define rutas públicas y protegidas, incluyendo la sección de administración con subrutas específicas.
 * Implementa un guard para verificar autenticación antes de acceder a rutas protegidas.
 */


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
        component: Administration, // Este componente puede contener el NavbarSwitcher
        meta: {
            requiresAuth: true
        },
        children: [
            {
                path: '/home',
                name: 'AdminHome',
                component: HomeView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER']
                }
            },
            {
                path: 'users',
                name: 'User ',
                component: UserView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN']
                }
            },
            {
                path: 'roles',
                name: 'Role',
                component: RoleView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'parking-facilities',
                name: 'ParkingFacility',
                component: ParkingFacilityView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN', 'PARKING_MANAGER'] // Accesible por ADMIN y PARKING_MANAGER
                }
            },
            {
                path: 'parking-spots',
                name: 'ParkingSpot',
                component: ParkingSpotView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN', 'PARKING_MANAGER'] // Accesible por ADMIN y PARKING_MANAGER
                }
            },
            {
                path: 'schedules',
                name: 'Schedule',
                component: ScheduleView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'days-week',
                name: 'DayOfWeeks',
                component: DayOfWeekView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'daily-schedules',
                name: 'DailySchedule',
                component: DailyScheduleView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER']
                }
            },
            {
                path: 'additional-services',
                name: 'AdditionalServices',
                component: AddServiceView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'fees',
                name: 'Fee',
                component: FeeView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN','PARKING_MANAGER'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'pay-methods',
                name: 'PayMethod',
                component: PayMethodView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'reservations',
                name: 'Reservation',
                component: ReservationView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN', 'PARKING_MANAGER'] // Accesible por ADMIN y PARKING_MANAGER
                }
            },
            {
                path: 'service-reservations',
                name: 'ServiceReservation',
                component: ServiceReservationView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'vehicle-types',
                name: 'VehicleType',
                component: VehicleTypeView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN', 'PARKING_MANAGER'] // Solo accesible por ADMIN
                }
            },
            {
                path: 'reservations-status',
                name: 'ReservationStatus',
                component: ReservationStatusView,
                meta: {
                    requiresAuth: true,
                    roles: ['ADMIN'] // Solo accesible por ADMIN
                }
            }
        ]
    },
    {
        path: '/public',
        name: 'Public',
        component: NavbarSwitcher, // Usar NavbarSwitcher para las rutas públicas
        meta: {
            requiresAuth: false
        },
        children: [
            {
                path: 'parking-facilities',
                name: 'PublicParkingFacilities',
                component: ParkingFacilityView,
                meta: {
                    requiresAuth: true
                }
            },
            {
                path: 'reservations',
                name: 'PublicReservations',
                component: PublicReservationView,
                meta: {
                    requiresAuth: true
                }
            },
            {
                path: 'payment',
                name: 'Payment',
                component: PaymentView,
                meta: {
                    requiresAuth: true
                }
            },
            {
                path: 'profile',
                name: 'Profile',
                component: UserProfileView,
                meta: {
                    requiresAuth: true
                }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

// Middleware para verificar la autenticación y roles
router.beforeEach((to, from, next) => {
    const isAuthenticated = !!sessionStorage.getItem('user');
    const user: User = JSON.parse(sessionStorage.getItem('user') || '{}');
    const requiresAuth = to.meta.requiresAuth as boolean;
    const roles = (to.meta.roles as string[]) || [];

    if (requiresAuth && !isAuthenticated) {
        next({ name: 'Login' });
    } else if (roles.length && user.userRole && !roles.includes(user.userRole.name)) {
        next({ name: 'home' }); // Redirigir a home si no tiene acceso
    } else {
        next();
    }
});

export default router;
