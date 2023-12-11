import { Injectable } from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tour} from "../../common/tour.model/tour";
import { GlobalConstants } from 'src/app/global-constants';
import { TourSaveRequest } from 'src/app/common/tour.model/tour-save-request';
import {tourENUM} from "../../common/tour.model/tour-enum";
import {Pages} from "../../common/pages.model/pages";

@Injectable({
  providedIn: 'root'
})
export class TourService {
  // private API_TOURS = GlobalConstants.apiURL + '/tours/';
  private baseUrl = "http://localhost:8080/api/tours"

  constructor(private httpClient: HttpClient) { }

  getAllTours(): Observable<Tour[]> {
    return this.httpClient.get<Tour[]>(`${this.baseUrl}/`);
  }

  getById(id: string): Observable<Tour> {
    return this.httpClient.get<Tour>(`${this.baseUrl}/${id}`);
  }

  create(request: TourSaveRequest) {
    return this.httpClient.post(`${this.baseUrl}/`, request);
  }

  update(id: string, request: TourSaveRequest) {
    return this.httpClient.put(`${this.baseUrl}/${id}`, request);
  }

  delete(id: string) {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }

  pagination(pageNo: number, pageSize: number) {
    return this.httpClient.get<any>(`${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}`);
  }

  getSortedAndPaginatedTours(pageNo: number, pageSize: number,sortBy: String, sortDir: String) {
    return this.httpClient.get<any>(`${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}`);
  }

  getSortedAndPaginatedToursMainPage(pageNo: number,
                                     pageSize: number,
                                     sortBy: String,
                                     sortDir: String,
                                     status: tourENUM,
                                     startPrice: number,
                                     endPrice: number,
                                     departureId?: number,
                                     destinationId?: number,
                                     startDate?: Date,
                                     endDate?: Date){





    let url = `${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}&status=${status}&startPrice=${startPrice}&endPrice=${endPrice}`;

    // const startDateString = startDate ? startDate.toISOString() : null;
    // const endDateString = endDate ? endDate.toISOString() : null;

    if(departureId != undefined) {
      url += `&departure=${departureId}`;
    }
    if(destinationId != undefined) {
      url += `&destination=${destinationId}`;
    }

    if(startDate != undefined) {
      url += `&startDate=${startDate}`;
    }
    if(endDate != undefined) {
      url += `&endDate=${endDate}`;
    }

    // return this.httpClient.get<any>(`${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}&status=${status}&startPrice=${startPrice}&endPrice=${endPrice}&departure=${departureId}&destination=${destinationId}`);
    return this.httpClient.get<any>(url);
  }

  // getSidebarFilter(status: tourENUM, startPrice: number, endPrice: number) {
  //   return this.httpClient.get<any>(`${this.baseUrl}/filter/?status=${status}&startPrice=${startPrice}&endPrice=${endPrice}`);
  // }

  getMainFilter(pageNo: number, pageSize: number,departureId: number, destinationId: number,startDate: Date, endDate: Date) {
    return this.httpClient.get<any>(`${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}&departure=${departureId}&destination=${destinationId}&startDate=${startDate}&endDate=${endDate}`);
  }


}
