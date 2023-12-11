import {Country} from "../country.model/country";

export class Location {
  constructor(location: Location | null = null) {
    this.id = location?.id ?? '';
    this.city = location?.city ?? '';
    this.country = location?.country ?? new Country();
  }

  id: string;
  city: string;
  country: Country;
}
