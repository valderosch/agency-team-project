import {Component, OnInit, ViewChild} from '@angular/core';
import {Order} from "../../common/order.model/order";
import {User} from "../../common/user.model/user";
import {StorageService} from "../../services/storage/storage.service";
import {OrderService} from "../../services/order/order.service";
import {LocationService} from "../../services/location/location.service";
import {Pages} from "../../common/pages.model/pages";
import {Sort} from "../../common/sort.model/sort";
import {MatPaginator, PageEvent} from "@angular/material/paginator";


@Component({
  selector: 'app-profile-order-history',
  templateUrl: './profile-order-history.component.html',
  styleUrls: ['./profile-order-history.component.css']
})
export class ProfileOrderHistoryComponent implements OnInit {


  orders: Order[] = [];

  currentPage: number = 0;
  pageSize: number = 3;
  pageIndex: number = 0;
  pageSizeOptions: number[] = [3,6, 9];

  page! : Pages;
  sort! : Sort;
  order!: Order;
  user!: User;
  userId: number | undefined = this.storageService.getUser().id;

  @ViewChild(MatPaginator) paginator!: MatPaginator;


  constructor(private storageService: StorageService,private orderService: OrderService,private  locationService: LocationService) { }

  ngOnInit(): void {
    // this.orderService.getOrdersByUserId(this.userId).subscribe(data => {
    //   this.orders = data;
    //   console.log(this.userId);
    //  console.log(data);
    //
    // });

    this.sort = {
      sortBy: 'id',
      sortDir: 'asc'
    }

    // this.getOrders();
    this.loadSortedAndPaginatedOrdersByUserId(this.userId,this.currentPage,this.pageSize,this.sort.sortBy, this.sort.sortDir);


  }

  loadSortedAndPaginatedOrdersByUserId(userId: number| undefined,currentPage: number, pageSize: number, sortBy: String, sortDir: String) {

    if(this.orders.length !=0) {
      for (let i = 0; i<this.page.pageSize;i++) {
        this.orders.shift();
      }
    }



    this.orderService.getSortedAndPaginatedOrdersByUser(this.userId,currentPage,pageSize,sortBy,sortDir).subscribe(data => {
      console.log("data " + data);

      data.forEach((item: {content: any}) => {


        this.orders.push(item.content[0]);

      });





      console.log(this.orders);


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

    this.loadSortedAndPaginatedOrdersByUserId(this.userId,event.pageIndex,event.pageSize,this.sort.sortBy, this.sort.sortDir)
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
      this.sort.sortBy = "createdAt";
      this.sort.sortDir = "desc";
    }
    if(selectedValue === '4') {
      this.sort.sortBy = "createdAt";
      this.sort.sortDir = "asc";
    }


    this.loadSortedAndPaginatedOrdersByUserId(this.userId,this.currentPage,this.pageSize,this.sort.sortBy,this.sort.sortDir);
  }

}
