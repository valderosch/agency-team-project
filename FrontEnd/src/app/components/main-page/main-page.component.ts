import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { trigger, transition, style, animate } from '@angular/animations';


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
  animations: [
    trigger('slideAnimation', [
      transition(':enter', [
        style({ transform: 'translateX(-100%)' }),
        animate('500ms ease-out', style({ transform: 'translateX(0)' }))
      ]),
      transition(':leave', [
        animate('500ms ease-out', style({ transform: 'translateX(100%)' }))
      ])
    ]),

    trigger('carouselAnimation', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate('500ms', style({ opacity: 1 }))
      ]),
      transition('* => void', [
        animate('500ms', style({ opacity: 0 }))
      ])
    ])
  ]

})
export class MainPageComponent implements OnInit {

  animations: any[] = [];




  carouselItems = [
    { image: './assets/images/tour/2a8372ec-2779-4d11-960a-fcf840c10379.jpg',
      title: 'Feel free: Нью-Йорк + Ніагара '},

    { image: './assets/images/tour/4e617d27-c8f7-436d-af10-280305f126b0.jpg',
      title: 'Glimpse of Paris'},

    { image: './assets/images/tour/73d6b52e-e3f7-402b-8d17-c89195762cb5.jpg',
      title: 'Усміхнись, ми в Монтенеґро'
    }
  ];



  carouselConfig = {
    slidesToShow: 1,
    slidesToScroll: 1,
    dots: true,
    infinite: true,
    autoplay: true,
    autoplaySpeed: 2000
  };

  constructor(
    private router: Router,
  ) {

  }

  ngOnInit(): void {


  }
  routeTourPage() {
    this.router.navigate(['tour-main-page'])
  }




}
