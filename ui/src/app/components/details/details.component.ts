import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {DetailsService} from "../../services/details.service";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './details.component.html',
  styleUrls: ['../settings-pages.styles.scss',
              '../button.styles.scss'
  ]
})
export class DetailsComponent implements OnInit {

  dateOfBirth = '';
  placeOfBirth = '';
  city = '';
  streetAndNumber = '';
  postalCode = '';
  country = '';
  gender = '';
  drivingLicence = false;
  drivingGroups = [''];

  isEditableBirth = false;

  constructor(
    public detailsService: DetailsService,
    public router: Router,
    public authService: AuthService
  ) {  }

  ngOnInit() {
    this.detailsService.personalDetails(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
    ).subscribe(
      response => {
        this.dateOfBirth = response.dateOfBirth;
        this.placeOfBirth = response.placeOfBirth;
        this.city = response.city;
        this.streetAndNumber = response.streetAndNumber;
        this.postalCode = response.postalCode;
        this.country = response.country;
        this.gender = response.gender;
        this.drivingLicence = response.drivingLicence;
        this.drivingGroups = response.drivingGroups;
      },
      error => {
        if (error.status === 403) {
          this.router.navigateByUrl('home').then(() => window.location.reload());
        }
      }
    )
  }

  updateDetails() {
    this.detailsService.updateDetails(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.dateOfBirth,
      this.placeOfBirth,
      this.city,
      this.streetAndNumber,
      this.postalCode,
      this.country,
      this.gender,
      this.drivingLicence,
      this.drivingGroups
    ).subscribe( () => {
      window.location.reload();
    })
  }

  isBirthEditable() {
    this.isEditableBirth = !this.isEditableBirth
  }
}
