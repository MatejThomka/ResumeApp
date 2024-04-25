import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DetailsService {

  private detailsApiUrl = 'http://localhost:6868/api/personal-details'
  constructor(private http: HttpClient) { }

  personalDetails(token: string,
                  username: string): Observable<any> {
    return this.http.get<any>(`${this.detailsApiUrl}/${username}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  updateDetails(token: string,
                username: string,
                dateOfBirth: string,
                placeOfBirth: string,
                city: string,
                streetAndNumber: string,
                postalCode: string,
                country: string,
                gender: string,
                drivingLicence: boolean,
                drivingGroups: string[])
  {
    return this.http.put<any>(
      `${this.detailsApiUrl}/${username}/create-update`,
      {
        dateOfBirth,
        placeOfBirth,
        city,
        streetAndNumber,
        postalCode,
        country,
        gender,
        drivingLicence,
        drivingGroups
      },
      {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      }
    )
  }
}
