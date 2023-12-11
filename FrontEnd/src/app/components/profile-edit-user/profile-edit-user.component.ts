import { Component, OnInit } from '@angular/core';
import {User} from "../../common/user.model/user";
import {UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";
import {StorageService} from "../../services/storage/storage.service";
import {UntypedFormBuilder, UntypedFormControl} from "@angular/forms";
import {LocationService} from "../../services/location/location.service";
import {Country} from "../../common/country.model/country";
import {City} from "../../common/location.model/city";
import {CountryService} from "../../services/country/country.service";


@Component({
  selector: 'app-profile-edit-user',
  templateUrl: './profile-edit-user.component.html',
  styleUrls: ['./profile-edit-user.component.css']
})
export class ProfileEditUserComponent implements OnInit {

  user! : User;
  userId: number| undefined = this.storageService.getUser().id;
  // documentForm!: FormGroup;
  countries: Country[] = [];
  cities: City[] = [];



  constructor(private userService: UserService, private router: Router,
              private storageService : StorageService,
              private formBuilder : UntypedFormBuilder,
              private locationService : LocationService,
              private countryService: CountryService) { }

  documentForm = this.formBuilder.group({

    firstName: [''],
    lastName: [''],
    phoneNumber: [''],
    locationId: [null],
    countryId: [null],
  });

  ngOnInit(): void {
    this.getUser();



    this.countryService.getCountries().subscribe(data => {
      this.countries = data;
    });
    console.log(this.countries);


  }

  onSubmit() {

    console.log(this.user);

    this.userService.update(this.userId,this.documentForm.value).subscribe(data => {
      console.log(data);
    });
    this.router.navigate(['profile']);
  }

  getUser(){
    this.userService.getUserById(this.userId).subscribe((data : any) => {
        console.log(data);
        this.documentForm = this.formBuilder.group({
          firstName: new UntypedFormControl(data['firstName']),
          lastName: new UntypedFormControl(data['lastName']),
          phoneNumber: new UntypedFormControl(data['phoneNumber']),
          locationId: new UntypedFormControl(data['locationId'])
        });
    });
  }

  onCountryChange(e : Event) {
    const countryId = ((e.target) as HTMLSelectElement).value;
    this.locationService.getCitiesByCountryId(countryId).subscribe(data => this.cities = data);
  }

}
