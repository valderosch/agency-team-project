export class Category {
  constructor(category: Category | null = null) {
    this.id = category?.id ?? '';
    this.name = category?.name ?? '';  
  }

  id: string;
  name: string;
}
