
export interface Reservation {
    id: number;
    bookDate: Date;
    payDate: Date;
    startDate: Date;
    endDate: Date;
    reservationStatus: {id: number};
    payMethod: {id: number};
    parkingSpot: {id: number};
    fee: {id: number};
}