import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from 'src/app/common/location.model/city';
import { GlobalConstants } from 'src/app/global-constants';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private API_LOCATIONS = GlobalConstants.apiURL + '/locations';

  constructor(private http: HttpClient) { }

  getCitiesByCountryId(countryId: string): Observable<City[]> {
    return this.http.get<City[]>(`${this.API_LOCATIONS}/country/${countryId}`);
  }
}
