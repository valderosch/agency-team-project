import {Category} from "../tourCategory.model/category";
import {Location} from "../location.model/location";

export class Tour {
  constructor(tour: Tour | null = null) {
    this.id = tour?.id ?? '';
    this.category = tour?.category ?? new Category();
    this.name = tour?.name ?? '';
    this.description = tour?.description ?? '';
    this.imagePath = tour?.imagePath ?? '';
    this.departure = tour?.departure ?? new Location();
    this.destination = tour?.destination ?? new Location();
    this.initialPrice = tour?.initialPrice ?? 0;
    this.discountPercentage = tour?.discountPercentage ?? 0;
    this.numOfPeople = tour?.numOfPeople ?? 0;
    this.availableOrderCount = tour?.availableOrderCount ?? 0;
    this.status = tour?.status ?? '';
    this.startDate = tour?.startDate ?? new Date();
    this.endDate = tour?.endDate ?? new Date();
    this.createdAt = tour?.createdAt ?? new Date();
  }

  id: string;
  category: Category;
  name: string;
  description: string;
  imagePath: string;
  departure: Location;
  destination: Location;
  initialPrice: number;
  discountPercentage: number;
  numOfPeople:number;
  availableOrderCount: number;
  status: string;
  startDate: Date;
  endDate: Date;
  createdAt: Date;
}
