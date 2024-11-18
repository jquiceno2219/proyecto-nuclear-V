
export interface AddService {
    id: number;
    name: string;
    price: number;
    status: boolean;
    parkingFacility: {id: number};
}