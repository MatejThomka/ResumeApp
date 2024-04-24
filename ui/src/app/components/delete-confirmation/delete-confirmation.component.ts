import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {GeneralService} from "../../services/general.service";
import {EducationService} from "../../services/education.service";
import {AuthService} from "../../services/auth.service";
import {EducationComponent} from "../education/education.component";

@Component({
  selector: 'app-delete-confirmation',
  standalone: true,
    imports: [
        FormsModule,
        NgIf
    ],
  templateUrl: './delete-confirmation.component.html',
  styleUrls: ['./delete-confirmation.component.scss',
              '../button.styles.scss']
})
export class DeleteConfirmationComponent implements OnInit{

  constructor(public generalService: GeneralService,
              public educationService: EducationService,
              public authService: AuthService,
              public education: EducationComponent
  ) {}

  ngOnInit() {
  }

  delete(id: number | undefined) {
    this.educationService.deleteEducation(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      id
    ).subscribe(() => {
      this.education.reload()
    })
  }
}
