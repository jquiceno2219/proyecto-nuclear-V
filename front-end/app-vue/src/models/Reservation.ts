export interface Reservation {
    id: number;
    bookDate: string;
    payDate: string;
    startDate: string;
    endDate: string;
    reservationStatus: ReservationStatus; // Updated to use ReservationStatus
    payMethod: { id: number };
    parkingSpot: { id: number };
    fee: { id: number };
}

export interface ReservationStatus {
    id: number;
    name: string;
}
