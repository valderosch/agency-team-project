import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  @Input() currentPage! : number;
  @Input() totalPage!: number;

  constructor() { }

  ngOnInit(): void {
    console.log(this.currentPage);
    console.log(this.totalPage);
  }

  onPageChange(pageNo: number) : void {
    console.log(`Page changed to ${pageNo}`);
  }

}
