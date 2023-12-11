import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import { GlobalConstants } from 'src/app/global-constants';
import { UserSaveRequest } from 'src/app/common/user.model/user-save-request';
import {User} from "../../common/user.model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_USERS = GlobalConstants.apiURL + '/users/';
  private baseUrl = "http://localhost:8080/api/users/"

  constructor(private httpClient: HttpClient) { }

  getUserList(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.API_USERS);
  }

  getUserById(id : number | undefined): Observable<User> {
    return this.httpClient.get<User>(`${this.API_USERS}${id}`)
  }

  create(request: UserSaveRequest) {
    return this.httpClient.post(this.API_USERS, request);
  }

  update(id:number | undefined, user: User){
    return this.httpClient.put(`${this.baseUrl}${id}`,user);
  }
}

