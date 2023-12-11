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
    { image: '../../assets/images/tours/israel-tel-aviv-1-big.jpg',
      title: 'Сонячний Тель-Авів?'},

    { image: '../../assets/images/tours/7cef64ab-90d0-4523-b7b1-8981b3359ca1.jpg',
      title: 'Чи може вишуканість Австрії?'},

    { image: '../../assets/images/tours/9e39f0b4-3d84-4953-a58c-f3babddbf3b3.jpg',
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
