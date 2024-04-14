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

  education(token: string,
            username:string): Observable<Education[]> {
    return this.http.get<Education[]>(`${this.educationUrl}/${username}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  updateEducation(token: string,
                  username: string,
                  id: number,
                  nameOfInstitution: string,
                  educationType: string,
                  fieldOfStudy: string,
                  yearFrom: string,
                  yearTill: string,
                  description: string): Observable<any> {
    return this.http.put<any>(
      `${this.educationUrl}/${username}/create-update`,
      {
        id,
        nameOfInstitution,
        educationType,
        fieldOfStudy,
        yearFrom,
        yearTill,
        description,
      },
      {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      }
    )
  }

  deleteEducation(token: string,
                  username: string,
                  id: number): Observable<any> {
    return this.http.delete(`${this.educationUrl}/${username}/delete?=${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }
}
