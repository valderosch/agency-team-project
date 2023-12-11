import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../../common/order.model/order";
import {OrderSaveRequest} from "../../common/order.model/order-save-request";
@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUrl = "http://localhost:8080/api/orders"
  constructor(private httpClient: HttpClient) { }

  getOrderList() : Observable<Order[]>{
    return this.httpClient.get<Order[]>(`${this.baseUrl}/`);
  }

  updateOrderStatus(id:number,order: Order): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/${id}`, order);
  }



  createOrder(request: OrderSaveRequest) {
    return this.httpClient.post(`${this.baseUrl}/`,request);
  }



  getOrdersByUserId(userId: number | undefined) : Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.baseUrl}/user/${userId}`);
  }

  getSortedAndPaginatedOrders(pageNo: number, pageSize: number,sortBy: String, sortDir: String) {
    return this.httpClient.get<any>(`${this.baseUrl}?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}`);
  }

  getSortedAndPaginatedOrdersByUser(userId: number | undefined, pageNo: number, pageSize: number,sortBy: String, sortDir: String)  {
    return this.httpClient.get<any>(`${this.baseUrl}/user/${userId}?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}`);
  }





}
