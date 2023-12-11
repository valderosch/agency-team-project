import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest } from 'src/app/common/auth.model/auth-request';
import { GlobalConstants } from 'src/app/global-constants';
import { Observable} from 'rxjs';
import { AuthResponse } from 'src/app/common/auth.model/auth-response';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API_AUTH = GlobalConstants.apiURL + '/auth';


  constructor(private http: HttpClient) {}

  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_AUTH}/authenticate`, credentials);
  }

  logout() {
    return this.http.get(`${this.API_AUTH}/logout`);
  }
}
