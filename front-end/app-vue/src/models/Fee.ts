
export interface Fee{
    id: number;
    name: string;
    price: number;
    status: boolean;
    parkingFacility: {id: number}
    vehicleType: {id: number}
}