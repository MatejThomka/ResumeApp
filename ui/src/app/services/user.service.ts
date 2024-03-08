import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userApiUrl = 'http://localhost:6868/api/user';

  constructor(private http: HttpClient) { }

  userDetails(token: string, username: string): Observable<any> {
    return this.http.get<any>(`${this.userApiUrl}/${username}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  updateCredentials(token: string, usernameAuth: string, username: string, name: string, lastname: string, phoneNumber: string): Observable<any> {
    return this.http.patch<any>(
      `${this.userApiUrl}/${usernameAuth}/update-credentials`,
      {
        username,
        name,
        lastname,
        phoneNumber
      },
      {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
  }

  emailChange(token: string, usernameAuth: string, email: string, confirmEmail: string): Observable<any> {
    return this.http.patch<any>(
      `${this.userApiUrl}/${usernameAuth}/email-change`,
      {email, confirmEmail},
      {headers: {'Authorization': `Bearer ${token}`}})
  }
}
