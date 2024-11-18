// src/models/User.ts

export interface User {
    id: number;
    userName: string;
    password: string;
    name: string;
    idNumber: string;
    phoneNumber: string;
    email: string;
    status: boolean;
    parkingFacility: { id: number };
    userRole: { id: number };
}
