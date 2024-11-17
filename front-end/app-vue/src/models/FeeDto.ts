import type {VehicleTypeDto} from "@/models/VehicleTypeDto";
import type {ParkingFacility} from "@/models/ParkingFacility";

export interface FeeDto {
    id: number;
    name: string;
    price: number;
    status: boolean;
    parkingFacility: { id: number };
    VehicleTypeDto: {id: number};
}