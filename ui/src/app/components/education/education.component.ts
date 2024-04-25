import {Component, OnInit} from '@angular/core';
import {EducationService} from "../../services/education.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {Education} from "../../interfaces/education";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {GeneralService} from "../../services/general.service";
import {DeleteConfirmationComponent} from "../delete-confirmation/delete-confirmation.component";

@Component({
  selector: 'app-education',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgIf,
    DeleteConfirmationComponent
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

  isEditable: boolean[] = [];
  isCreatable = false;

  index: number = -1;

  constructor(
    public educationService: EducationService,
    public router: Router,
    public authService: AuthService,
    public generalService: GeneralService
  ) { }

  ngOnInit() {
    this.fetchEducation();
  }

  reload() {
    window.location.reload()
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

  edit(id?: number) {
    const index = this.educations.findIndex(education => education.id === id);

    if (index !== -1) {
      this.isEditable[index] = !this.isEditable[index];

      if (this.isEditable[index]) {
        this.initialEducation = { ...this.educations[index]};
        this.editedEducation = { ...this.educations[index]};
      }
    }
  }

  cancelEdit(id?: number) {
    this.index = this.educations.findIndex(education => education.id === id);

    if (this.index !== -1) {
      this.isEditable[this.index] = !this.isEditable[this.index];

      if (!this.isEditable[this.index]) {
        this.editedEducation = { ...this.initialEducation};
      }
    }
  }

  create() {
    this.isCreatable = !this.isCreatable;
  }

  saveEdited() {
    this.educationService.putEducation(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.editedEducation
  ).subscribe(() => {
        this.reload()
      }, error => {
        if (error.status === 400) {
          this.message = error.error;
          this.messageType = 'error';
          setTimeout(() => {
            this.message = '';
          }, 2000);
        }
      }
    )
  }

  saveCreated() {
    this.educationService.putEducation(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.newEducation,
    ).subscribe(() => {
        this.reload()
      }
    )
  }

  delete(id: number | undefined) {
    this.editedEducation.id = id;
    this.generalService.showDeleteConfirmationDialog = true;
  }

  yearTillDelete() {
    this.editedEducation.yearTill = null;
  }
}
