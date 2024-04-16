import {Component, OnInit} from '@angular/core';
import {EducationService} from "../../services/education.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {Education} from "../../interfaces/education";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-education',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgIf
  ],
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss',
              '../button.styles.scss',
              '../message.style.scss',
              '../checkbox.styles.scss'
  ]
})
export class EducationComponent implements OnInit {

  educations: Education[] = [];

  messageType = '';
  message = '';

  isEditable = false;

  constructor(
    public educationService: EducationService,
    public router: Router,
    public authService: AuthService
  ) { }

  ngOnInit() {
    this.fetchEducation();
  }

  fetchEducation() {
    this.educationService.education(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
    ).subscribe(
      educations => {
        this.educations = educations;
      },
      error => {
        if (error.status === 403) {
          this.router.navigateByUrl('home').then(() => window.location.reload());
        } else if (error.status === 404) {
          this.message = 'You do not have any details yet!';
          this.messageType = 'error';
        }
      }
    )
  }

  edit() {
    this.isEditable = !this.isEditable;
  }

  save() {

  }
}
