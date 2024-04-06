import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {DetailsService} from "../../services/details.service";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './details.component.html',
  styleUrls: ['../settings-pages.styles.scss',
              '../button.styles.scss',
              '../message.style.scss'
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
  drivingGroupsOptions = ['AM', 'A1', 'A2', 'A',
                                   'B1', 'B', 'C1', 'C',
                                   'D1', 'D', 'BE', 'C1E',
                                   'CE', 'D1E', 'DE', 'T'];

  isEditableBirth = false;
  isEditableAddress = false;
  isEditableDriving = false;

  message = '';
  messageType = '';

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
        this.dateOfBirth = response?.dateOfBirth;
        this.placeOfBirth = response?.placeOfBirth;
        this.city = response?.city;
        this.streetAndNumber = response?.streetAndNumber;
        this.postalCode = response?.postalCode;
        this.country = response?.country;
        this.gender = response?.gender;
        this.drivingLicence = response?.drivingLicence;
        this.drivingGroups = response?.drivingGroups;
        this.messageType = 'success';
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
      this.message = 'Update of your personal details was successfully!'
      setTimeout(() => {
        this.message = '';
        window.location.reload();
      }, 2000);
    })
  }

  onCheckboxChange(event: Event, option: string) {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      this.drivingGroups.push(option);
    } else {
      this.drivingGroups = this.drivingGroups.filter(item => item !== option);
    }
    // if (this.drivingGroups.includes(option)) {
    //   this.drivingGroups = this.drivingGroups.filter(item => item !== option);
    // } else {
    //   this.drivingGroups.push(option);
    // }
  }

  isBirthEditable() {
    this.isEditableBirth = !this.isEditableBirth;
  }

  isAddressEditable() {
    this.isEditableAddress = !this.isEditableAddress;
  }

  isDrivingEditable() {
    this.isEditableDriving = !this.isEditableDriving;
  }
}
