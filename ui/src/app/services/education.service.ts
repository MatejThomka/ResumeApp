import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Education} from "../interfaces/education";

@Injectable({
  providedIn: 'root'
})
export class EducationService {

  private educationUrl = 'http://localhost:6868/api/education'
  constructor(private http: HttpClient) { }

  getEducation(token: string,
               username:string): Observable<Education[]> {
    return this.http.get<Education[]>(`${this.educationUrl}/${username}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  putEducation(token: string,
               username: string,
               EducationDTO: Education
                  ): Observable<Education> {
    if (EducationDTO.id) {
      return this.http.put<Education>(
        `${this.educationUrl}/${username}/create-update?id=${EducationDTO.id}`,
        EducationDTO,
        {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      )
    } else {
      return this.http.put<Education>(
        `${this.educationUrl}/${username}/create-update`,
        EducationDTO,
        {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      )
    }
  }

  deleteEducation(token: string,
                  username: string,
                  id?: number): Observable<any> {
    return this.http.delete(`${this.educationUrl}/${username}/delete?id=${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }
}
