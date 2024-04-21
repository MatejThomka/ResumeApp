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
  initialEducation: Education = {} as Education;
  editedEducation: Education = {} as Education;
  newEducation: Education = {educationType: 'HIGH_SCHOOL'} as Education;

  messageType = '';
  message = '';

  isEditable = false;
  isCreatable = false;

  constructor(
    public educationService: EducationService,
    public router: Router,
    public authService: AuthService
  ) { }

  ngOnInit() {
    this.fetchEducation();
  }

  fetchEducation() {
    this.educationService.getEducation(
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

  edit(education?: Education) {
    this.isEditable = !this.isEditable;

    this.initialEducation = <Education>{...education};
    this.editedEducation = <Education>{...education};
  }

  cancelEdit() {
    this.isEditable = !this.isEditable;

    this.editedEducation = {...this.initialEducation};
  }

  create() {
    this.isCreatable = !this.isCreatable;
  }

  saveEdited() {
    this.educationService.putEducation(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.editedEducation
  ).subscribe(
      this.fetchEducation
    )
  }

  saveCreated() {
    this.educationService.putEducation(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.newEducation
    ).subscribe(
      this.fetchEducation
    )
  }

}
