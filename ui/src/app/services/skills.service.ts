import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Skill} from "../interfaces/skill";

@Injectable({
  providedIn: 'root'
})
export class SkillsService {

  private skillsApiUrl = 'http://localhost:6868/api/skill'
  constructor(private http: HttpClient) { }

  getSkills(token: string,
            username: string): Observable<Skill[]> {
    return this.http.get<(Skill[])>(`${this.skillsApiUrl}/${username}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }

  putSkill(token: string,
           username: string,
           skill: Skill): Observable<Skill> {
    if (skill.id) {
      return this.http.put<Skill>(
        `${this.skillsApiUrl}/${username}/create-update?id=${skill.id}`,
        skill, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      )
    } else {
      return this.http.put<Skill>(
        `${this.skillsApiUrl}/${username}/create-update`,
        skill, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      )
    }
  }

  deleteSkill(token: string,
              username: string,
              id: number): Observable<Skill> {
    return this.http.delete<Skill>(`${this.skillsApiUrl}/${username}/delete?id=${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
  }
}
