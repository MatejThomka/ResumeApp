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
}
