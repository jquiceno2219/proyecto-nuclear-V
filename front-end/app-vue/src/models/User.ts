// src/models/User.ts
import type {ParkingFacility} from "@/models/ParkingFacility";
import type {UserRole} from "@/models/UserRole";

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
