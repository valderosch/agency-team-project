import {Component, OnInit, ViewChild} from '@angular/core';
import {TourService} from "../../../services/tour/tour.service";
import {Tour} from "../../../common/tour.model/tour";
import { Router } from '@angular/router';
import {FormBuilder} from "@angular/forms";
import {City} from "../../../common/location.model/city";
import {Country} from "../../../common/country.model/country";
import {Pages} from "../../../common/pages.model/pages";
import {Sort} from "../../../common/sort.model/sort";
import {MatPaginator, PageEvent} from "@angular/material/paginator";


@Component({
  selector: 'app-admin-tours',
  templateUrl: './admin-tours.component.html',
  styleUrls: ['./admin-tours.component.css']
})
export class AdminToursComponent implements OnInit {

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



  @ViewChild(MatPaginator) paginator!: MatPaginator;







  constructor(private tourService: TourService,
              private router: Router) { }




  ngOnInit(): void {
    this.sort = {
      sortBy: 'id',
      sortDir: 'asc'
    }


    // this.getTours();
    this.loadSortedAndPaginatedTours(this.currentPage,this.pageSize,this.sort.sortBy, this.sort.sortDir);
  }
  private getTours() {
    this.tourService.getAllTours().subscribe(data=>{
      this.tours = data;
      console.log("tours" + this.tours);
    });
  }

  delete(id: string) {
    this.tourService.delete(id).subscribe({
      next: () => this.router.navigate(['/admin-tours']),
      error: (error) => {
        console.error("Error deleting tour", error);
        window.alert("У вас немає прав на видалення цього туру")
      }
    });
  }


  loadSortedAndPaginatedTours(currentPage: number, pageSize: number, sortBy: String, sortDir: String) {



    if(this.tours.length !=0) {
      for (let i = 0; i<this.page.pageSize;i++) {
        this.tours.shift();
      }
    }


    this.tourService.getSortedAndPaginatedTours(currentPage,pageSize,sortBy,sortDir).subscribe(data => {


      data.forEach((item: { content: any }) => {
        this.tours.push(item.content[0]);
      });

      console.log(this.tours);


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
    this.loadSortedAndPaginatedTours(event.pageIndex,event.pageSize,this.sort.sortBy, this.sort.sortDir)
  }




  onSelectChange(selectedValue: string) {

    if(selectedValue=== '1' ){
      this.sort.sortBy  = "price";
      this.sort.sortDir = "asc";
    }

    if(selectedValue === '2') {
      this.sort.sortBy = "price";
      this.sort.sortDir = "desc";
    }

    if(selectedValue === '3') {
      this.sort.sortBy = "startDate";
      this.sort.sortDir = "asc";
    }


    this.loadSortedAndPaginatedTours(this.currentPage,this.pageSize,this.sort.sortBy,this.sort.sortDir);
  }

}
