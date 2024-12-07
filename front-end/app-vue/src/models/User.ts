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
    parkingFacility: { id: number , name: string };
    userRole: { id: number , name: string};
}
