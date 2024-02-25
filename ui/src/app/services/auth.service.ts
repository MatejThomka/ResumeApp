import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:6868/api/auth';
  constructor(
    private http: HttpClient,
  ) { }

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, {email, password})
  }

  register(email: string, password: string, username: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, {email, password, username})
  }

  logout(token: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/logout`, null, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  saveToken(token: string) {
    localStorage.setItem('jwt_token', token);
  }

  saveUsername(username: string) {
    localStorage.setItem('username', username);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  removeToken() {
    localStorage.removeItem('jwt_token');
  }
}
