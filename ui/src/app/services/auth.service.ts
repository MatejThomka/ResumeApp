import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {delay, Observable, tap} from "rxjs";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn = false;

  private apiUrl = 'http://localhost:6868/api/auth';
  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Logs in the user by sending a POST request to the login endpoint with provided email and password.
   * @param email The email of the user.
   * @param password The password of the user.
   * @returns An Observable that emits the HTTP response from the server.
   */
  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, {email, password})
  }

  /**
   * Registers a new user by sending a POST request to the register endpoint with provided email, password, and username.
   * @param email The email of the user.
   * @param password The password of the user.
   * @param username The username of the user.
   * @returns An Observable that emits the HTTP response from the server.
   */
  register(email: string, password: string, username: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, {email, password, username})
  }

  /**
   * Logs out the user by sending a POST request to the logout endpoint with the user's token.
   * @param token The token of the logged-in user.
   * @returns An Observable that emits the HTTP response from the server.
   */
  logout(token: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/logout`, null, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  /**
   * Saves the JWT token in the local storage.
   * @param token The JWT token to be saved.
   */
  saveToken(token: string) {
    localStorage.setItem('jwt_token', token);
  }

  /**
   * Saves the username in the local storage.
   * @param username The username to be saved.
   */
  saveUsername(username: string) {
    localStorage.setItem('username', username);
  }

  /**
   * Retrieves the JWT token from the local storage.
   * @returns The JWT token if it exists in the local storage, otherwise null.
   */
  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  /**
   * Retrieves the username from the local storage.
   * @returns The username if it exists in the local storage, otherwise null.
   */
  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  /**
   * Removes the JWT token from the local storage.
   */
  removeToken() {
    localStorage.removeItem('jwt_token');
  }
}
