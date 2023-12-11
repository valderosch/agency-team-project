import {Component, OnInit, ViewChild} from '@angular/core';
import {Tour} from "../../common/tour.model/tour";
import {TourService} from "../../services/tour/tour.service";
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {Pages} from "../../common/pages.model/pages";
import {LocationService} from "../../services/location/location.service";
import {CountryService} from "../../services/country/country.service";
import {UntypedFormBuilder} from "@angular/forms";
import {City} from "../../common/location.model/city";
import {Country} from "../../common/country.model/country";
import {Sort} from "../../common/sort.model/sort";
import {tourENUM} from "../../common/tour.model/tour-enum";
import {MatSelect, MatSelectChange} from "@angular/material/select";
import {THREE} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-tour-main-page',
  templateUrl: './tour-main-page.component.html',
  styleUrls: ['./tour-main-page.component.css']
})
export class TourMainPageComponent implements OnInit {


  tours: Tour[] = [];
  cities: City[] = [];
  countries: Country[] = [];

  currentPage: number = 0;
  pageSize: number = 5;
  pageIndex: number = 0;
  pageSizeOptions: number[] = [5, 10, 15];

  tour! : Tour;
  page! : Pages;
  sort! : Sort;

  status: tourENUM = tourENUM.AVAILABLE;
  startPrice: number = 0;
  endPrice: number = 9999999;
  departureId!: number ;
  destinationId!: number;
  startDate!: Date;
  endDate!: Date;

  hidePagination: boolean = false;

  showSecondSelect = false;
  showFirstSelect = true;

  showSecondSelectDestination = false;
  showFirstSelectDestination = true;



  @ViewChild(MatPaginator) paginator!: MatPaginator;



  constructor(private tourService: TourService,
              private locationService: LocationService,
              private countryService: CountryService,
              private formBuilder: UntypedFormBuilder) { }

  documentForm = this.formBuilder.group({
    filterPriceStart: [0],
    filterPriceEnd: [99999],
    filterStatus: [''],
    sorting: ['1']
  });

  mainForm = this.formBuilder.group({
    countryDepartureId: [null],
    locationDepartureId: [null],
    countryDestinationId: [null],
    locationDestinationId: [null],
    startDate: [],
    endDate: []
  })

  ngOnInit(): void {



    this.countryService.getCountries().subscribe(data => {
      this.countries = data
    });

    this.sort = {
      sortBy: 'id',
      sortDir: 'asc'
    }

    this.loadSortedAndPaginatedTours(this.currentPage,this.pageSize,this.sort.sortBy, this.sort.sortDir,
      this.status,this.startPrice,this.endPrice);
  }


  private getTours() {
    this.tourService.getAllTours().subscribe(data=>{
      this.tours = data;
      console.log("tours" + this.tours);
    });
  }






  loadSortedAndPaginatedTours(currentPage: number,
                              pageSize: number,
                              sortBy: String,
                              sortDir: String,
                              status: tourENUM,
                              startPrice: number,
                              endPrice: number,
                              departureId?: number,
                              destinationId?: number,
                              startDate?: Date,
                              endDate?: Date) {





    if(this.tours.length !=0) {
      for (let i = 0; i<this.page.pageSize;i++) {
        this.tours.shift();
      }
    }

    console.log("sortBY " + sortBy);
    console.log("sortDir " + sortDir);

    console.log(departureId);

    this.tourService.getSortedAndPaginatedToursMainPage(currentPage,pageSize,sortBy,sortDir,status,startPrice,endPrice,departureId,destinationId,startDate,endDate).subscribe(data => {



      data.forEach((item: { content: any }) => {
        this.tours.push(item.content[0]);
      });
      console.log(this.tours);

      if(this.tours.length === 0 ) {
        this.hidePagination = true;
        console.log("pagination" + this.hidePagination);
      }




      data.forEach((item: any) => {

        this.page = {
          pageNo: item.pageNo,
          pageSize: item.pageSize,
          totalElements: item.totalElements,
          totalPages: item.totalPages,
          last: item.last
        }
      });

      console.log(this.page);




    });
  }



  onPageChange(event: PageEvent) : void {
    console.log(event);
    const  startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;
    if(endIndex > this.page.totalElements){
      endIndex = this.page.totalElements;
    }

    // this.loadPaginatedTours(event.pageIndex,event.pageSize);
    this.loadSortedAndPaginatedTours(event.pageIndex,event.pageSize,this.sort.sortBy, this.sort.sortDir,this.status,this.startPrice,this.endPrice);
  }

  onCountryChangeDeparture(e : MatSelectChange) {

    const countryId = (e.source as MatSelect).value;

    this.locationService.getCitiesByCountryId(countryId).subscribe(data => {
      this.cities = data;
      this.showSecondSelect=  true;
      this.showFirstSelect = false;
    });
  }

  onCountryChangeDestination(e : MatSelectChange) {

    const countryId = (e.source as MatSelect).value;

    this.locationService.getCitiesByCountryId(countryId).subscribe(data => {
      this.cities = data;
      this.showSecondSelectDestination=  true;
      this.showFirstSelectDestination = false;
    });
  }



  onSubmit() {
    console.log(this.documentForm.value.sorting);
    if(this.documentForm.value.sorting === '1' ){
      this.sort.sortBy  = "price";
      this.sort.sortDir = "asc";
    }

    if(this.documentForm.value.sorting === '2') {
      this.sort.sortBy = "price";
      this.sort.sortDir = "desc";
    }

    if(this.documentForm.value.sorting === '3') {
      this.sort.sortBy = "discount";
      this.sort.sortDir = "desc";
    }

    if(this.documentForm.value.sorting === '4') {
      this.sort.sortBy = "discount";
      this.sort.sortDir = "asc";
    }

    if(this.documentForm.value.sorting === '5') {
      this.sort.sortBy = "createdAt";
      this.sort.sortDir = "desc"
    }



    if(this.documentForm.value.filterStatus === "1"){
      this.status = tourENUM.HOT;
    }
    if(this.documentForm.value.filterStatus === "2") {
      this.status = tourENUM.AVAILABLE;
    }
    if(this.documentForm.value.filterStatus === "3") {
      this.status = tourENUM.NOT_AVAILABLE;
    }




    this.startPrice = this.documentForm.value.filterPriceStart;
    this.endPrice = this.documentForm.value.filterPriceEnd;


    this.loadSortedAndPaginatedTours(this.currentPage,this.pageSize,this.sort.sortBy,this.sort.sortDir,
      this.status,this.startPrice,this.endPrice,this.departureId,this.destinationId,this.startDate,this.endDate);


  }




  onSubmitMainForm() {
    this.departureId = this.mainForm.value.locationDepartureId;
    this.destinationId = this.mainForm.value.locationDestinationId;
    this.startDate = this.mainForm.value.startDate;
    this.endDate = this.mainForm.value.endDate;



      this.loadSortedAndPaginatedTours(
        this.currentPage,
        this.pageSize,
        this.sort.sortBy,
        this.sort.sortDir,
        this.status,
        this.startPrice,
        this.endPrice,
        this.mainForm.value.locationDepartureId,
        this.mainForm.value.locationDestinationId,
        this.mainForm.value.startDate,
        this.mainForm.value.endDate
      );



  }



  hideSecondSelect() {
    this.showSecondSelect = false;
    this.showFirstSelect = true;
  }

  hideSecondSelectDestination() {
    this.showSecondSelectDestination = false;
    this.showFirstSelectDestination = true;
  }

}
