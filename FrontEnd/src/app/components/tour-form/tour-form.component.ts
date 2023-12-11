import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Country } from 'src/app/common/country.model/country';
import { City } from 'src/app/common/location.model/city';
import { Tour } from 'src/app/common/tour.model/tour';
import { TourSaveRequest } from 'src/app/common/tour.model/tour-save-request';
import { Category } from 'src/app/common/tourCategory.model/category';
import { dateRangeValidator, imageValidator } from 'src/app/helpers/validators';
import { CategoryService } from 'src/app/services/category/category.service';
import { CountryService } from 'src/app/services/country/country.service';
import { ImageService } from 'src/app/services/image/image.service';
import { LocationService } from 'src/app/services/location/location.service';
import { TourService } from 'src/app/services/tour/tour.service';

@Component({
  selector: 'app-tour-form',
  templateUrl: './tour-form.component.html',
  styleUrls: ['../../common/css/form-errors.css', './tour-form.component.css']
})
export class TourFormComponent implements OnInit {
  tourForm: UntypedFormGroup;
  tour: Tour = new Tour();
  isUpdateMode: boolean = false;
  categories: Category[] = [];
  countries: Country[] = [];
  departureCities: City[] = [];
  destinationCities: City[] = [];
  image!: File;
  formSubmitted: boolean = false;
  imageUploadError: string = '';
  
  constructor(
    private fb: UntypedFormBuilder,
    private route: ActivatedRoute, 
    private tourService: TourService,
    private categoryService: CategoryService,
    private countryService: CountryService,
    private locationService: LocationService,
    private imageService: ImageService,
    private renderer: Renderer2,
    private elementRef: ElementRef,
    private router: Router
  ) {
    this.tourForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(128)]],
      categoryId: [null, [Validators.required]],
      image: [null],
      departureId: [null, [Validators.required]],
      destinationId: [null, [Validators.required]],
      price: [null, [Validators.required, Validators.min(0), Validators.max(2000000)]],
      discountPercentage: [null, [Validators.min(0), Validators.max(100)]],
      numOfPeople: [null, [Validators.required, Validators.min(1), Validators.max(8)]],
      availableOrderCount: [null, [Validators.required, Validators.min(0)]],
      status: [null, [Validators.required]],
      startDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      description: ['', [Validators.required]]
    }, { validators: [dateRangeValidator], updateOn: 'blur' });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = !!id;
    this.categoryService.getCategories().subscribe(data => this.categories = data);
    this.countryService.getCountries().subscribe(data => this.countries = data);
    
    const imageValidators = this.isUpdateMode ? [imageValidator] : [imageValidator, Validators.required];
    this.tourForm.get('image')?.setValidators(imageValidators);
    
    if (this.isUpdateMode) {
      this.tourService.getById(id!).subscribe(data => {
        this.tour = data;
        this.updateLocationsSelects();
        this.initializeFormWithTourData();
      });
    }
  }

  private updateLocationsSelects(): void {
    this.locationService.getCitiesByCountryId(this.tour.departure.country.id).subscribe(cities => this.departureCities = cities);
    const departureCountriesEl = this.elementRef.nativeElement.querySelector('#departure-countries')
    this.renderer.setProperty(departureCountriesEl, 'value', this.tour.departure.country.id);
   
    this.locationService.getCitiesByCountryId(this.tour.destination.country.id).subscribe(cities => this.destinationCities = cities);
    const destinationCountriesEl = this.elementRef.nativeElement.querySelector('#destination-countries')
    this.renderer.setProperty(destinationCountriesEl, 'value', this.tour.destination.country.id);
  }

  private initializeFormWithTourData(): void {
    this.tourForm.patchValue({
      name: this.tour.name,
      categoryId: this.tour.category.id,
      departureId: this.tour.departure.id,
      destinationId: this.tour.destination.id,
      price: this.tour.initialPrice,
      discountPercentage: this.tour.discountPercentage,
      numOfPeople: this.tour.numOfPeople,
      availableOrderCount: this.tour.availableOrderCount,
      status: this.tour.status,
      startDate: this.tour.startDate,
      endDate: this.tour.endDate,
      description: this.tour.description 
    });
  }

  onCountryChange(e: Event) {
    const countryId = ((e.target) as HTMLSelectElement).value;
    const selectId = ((e.target) as HTMLSelectElement).id;

    this.locationService.getCitiesByCountryId(countryId).subscribe(data => {
      if (selectId === "departure-countries") {
        this.departureCities = data;
      }
      else {
        this.destinationCities = data;
      }
    });
  }

  setPreviewImage(e: Event) {
    this.imageUploadError = "";
    const fileReader = new FileReader();
    const file = (e.target as HTMLInputElement).files![0];
    this.image = file;
    fileReader.readAsDataURL(file);
    fileReader.onload = (fileReaderEvent) => {
      const previewImgElement = this.elementRef.nativeElement.querySelector('#preview-img');
      this.renderer.setAttribute(previewImgElement, 'src', fileReaderEvent.target!.result as string);
    }
  }

  cancel() {
    if (!this.isUpdateMode) {
      this.router.navigate(['/admin-tours']);
    }
    else {
      this.router.navigate([`/tours/${this.tour.id}`]);
    }
  }

  async onSave() {
    this.formSubmitted = true;

    if (this.tourForm.valid) {
      if (!this.isUpdateMode) {
        try {
          const path = await this.imageService.uploadImage(this.image, 'tour');
          this.tourService.create(this.getTourToSave(path)).subscribe((res: any) => {
            window.alert("Успішно створено тур з id " + res.id);
            this.router.navigate(['/tours/' + res.id]);
          });
        } catch (error: any) {
          this.onImageSaveError(error);
        }
      } 
      else {
        if (this.image) {
          try {
            await this.imageService.updateImage(this.image, this.tour.imagePath);
          } catch (error: any) {
            this.onImageSaveError(error);
          }
        }
        this.tourService.update(this.tour.id, this.getTourToSave(this.tour.imagePath)).subscribe(() => this.router.navigate(['/tours/' + this.tour.id]));
      }
    }
  }

  private onImageSaveError(error: any) {
    console.error("Error uploading image", error);
    this.imageUploadError = "Виникла проблема з цим зображенням. Спробуйте інше";
  }

  private getTourToSave(imagePath: string) {
    const tourToSave: TourSaveRequest = {
      categoryId: this.tourForm.get('categoryId')?.value,
      name: this.tourForm.get('name')?.value,
      description: this.tourForm.get('description')?.value,
      imagePath: imagePath,
      departureId: this.tourForm.get('departureId')?.value,
      destinationId: this.tourForm.get('destinationId')?.value,
      price: this.tourForm.get('price')?.value,
      discountPercentage: this.tourForm.get('discountPercentage')?.value,
      numOfPeople: this.tourForm.get('numOfPeople')?.value,
      availableOrderCount: this.tourForm.get('availableOrderCount')?.value,
      status: this.tourForm.get('status')?.value,
      startDate: this.tourForm.get('startDate')?.value,
      endDate: this.tourForm.get('endDate')?.value
    };

    return tourToSave;
  }
}
