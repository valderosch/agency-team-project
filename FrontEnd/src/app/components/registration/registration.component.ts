import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Country } from 'src/app/common/country.model/country';
import { City } from 'src/app/common/location.model/city';
import { CountryService } from 'src/app/services/country/country.service';
import { LocationService } from 'src/app/services/location/location.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../../common/css/form-errors.css', '../../common/css/auth.css', './registration.component.css']
})
export class RegistrationComponent implements OnInit {
  registrationForm: UntypedFormGroup;
  countries: Country[] = [];
  cities: City[] = [];
  statusMessage: string = '';
  formSubmitted: boolean = false;

  constructor(
    private fb: UntypedFormBuilder,
    private userService: UserService,
    private countryService: CountryService,
    private locationService: LocationService,
    private renderer: Renderer2,
    private el: ElementRef
  ) {
      this.registrationForm = this.fb.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        phoneNumber: ['', [Validators.required, Validators.pattern(/^(\+?\d{1,2}[\s-.]?)?\(?\d{3}\)?[\s-.]?\d{3}[\s-.]?\d{2}[\s-.]?\d{2}$/)]],
        locationId: [null],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.pattern(/^[A-Za-z\d]{5,}$/)]]
      });
  }

  ngOnInit(): void {
    this.countryService.getCountries().subscribe(data => {
      this.countries = data
    });

  }

  onCountryChange(e: Event) {
    const countryId = ((e.target) as HTMLSelectElement).value;
    this.locationService.getCitiesByCountryId(countryId).subscribe(data => this.cities = data);
  }

  onRegistration() {
    this.formSubmitted = true;
    if (this.registrationForm.valid) {
      const statusElement = this.el.nativeElement.querySelector('#status');

      this.userService.create(this.registrationForm.value).subscribe({
        next: () => {
          this.renderer.removeClass(statusElement, 'text-danger');
          this.renderer.addClass(statusElement, 'text-success');
          this.statusMessage = "Реєстрація пройшла успішно";
        },
        error: err => {
          this.renderer.removeClass(statusElement, 'text-success');
          this.renderer.addClass(statusElement, 'text-danger');
          this.statusMessage = "Не вдалося зареєструватись: " + err.error.message;
        }
      });
    }
  }
}
