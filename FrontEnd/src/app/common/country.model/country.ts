export class Country {
  constructor(country: Country | null = null) {
    this.id = country?.id ?? '';
    this.name = country?.name ?? '';
  }

  id: string;
  name: string;
}
