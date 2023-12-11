import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Tour} from 'src/app/common/tour.model/tour';
import {StorageService} from 'src/app/services/storage/storage.service';
import {TourService} from 'src/app/services/tour/tour.service';
import {OrderService} from "../../services/order/order.service";
import {OrderSaveRequest} from "../../common/order.model/order-save-request";
import {toNumbers} from "@angular/compiler-cli/src/version_helpers";


@Component({
  selector: 'app-tour',
  templateUrl: './tour.component.html',
  styleUrls: ['./tour.component.css']
})
export class TourComponent implements OnInit {
  tour!: Tour;
  order!: OrderSaveRequest;
  userId: number| undefined;

  constructor(
      private route: ActivatedRoute,
      private tourService: TourService,
      private storageService: StorageService,
      private orderService: OrderService,
      private router: Router
    ) { }

  ngOnInit(): void {
    this.getTour();

    if(this.tour.availableOrderCount == 0) {
      this.delete();
    }
  }

  private getTour(): void {
    const id: string = this.route.snapshot.paramMap.get('id') ?? '';
    this.tourService.getById(id).subscribe(data => this.tour = data);
  }

  getTourDurationInDays() {
    const timeDiff = new Date(this.tour.endDate).getTime() - new Date(this.tour.startDate).getTime();
    return (timeDiff / 1000 / 60 / 60 / 24) + 1;
  }

  isAdmin() {
    return this.storageService.getUser()?.role === "ADMIN";
  }

  getFinalPrice() {
    if (!this.tour.discountPercentage || this.tour.discountPercentage === 0) {
      return this.tour.initialPrice;
    }
    return this.tour.initialPrice - this.tour.initialPrice * this.tour.discountPercentage / 100;
  }

  getTimestamp() {
    return new Date().getTime();
  }

  delete() {
    this.tourService.delete(this.tour.id).subscribe({
      next: () => this.router.navigate(['/admin-tours']),
      error: (error) => {
        console.error("Error deleting tour", error);
        window.alert("У вас немає прав на видалення цього туру")
      }
    });
  }

  createOrder() {

    if (!this.storageService.isLoggedIn()) {
      window.alert("Увійдіть у профіль користувача");
    }
    else {
      this.userId = this.storageService.getUser().id;
      this.order = {
        userId: this.userId,
        tourId: this.tour.id
      }


      this.orderService.createOrder(this.order).subscribe({
        next: () => {
          this.router.navigate(['/'])
          this.tour.availableOrderCount = this.tour.availableOrderCount - 1;

        },
        error: (error) => {
          console.log("Error creating order", error);
        }
      })
    }
  }

  protected readonly onsubmit = onsubmit;
}
