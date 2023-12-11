export class TourSaveRequest {
    constructor(
        categoryId: string,
        name: string,
        description: string,
        imagePath: string,
        departureId: string,
        destinationId: string,
        price: number,
        discountPercentage: number,
        numOfPeople: number,
        availableOrderCount: number,
        status: string,
        startDate: Date,
        endDate: Date
    ) { }
}