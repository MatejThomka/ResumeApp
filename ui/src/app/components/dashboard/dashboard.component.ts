import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss',
    '../button.styles.scss']
})
export class DashboardComponent {

  constructor(public router: Router,
              public authService: AuthService) {  }

  profilePage() {
    this.router.navigateByUrl(`user/${this.authService.getUsername()}`)
      .then(() => {
      window.location.reload()
    });
  }

  detailsPage() {
    this.router.navigateByUrl(`details/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }

  educationPage() {
    this.router.navigateByUrl(`education/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }

  skillsPage() {
    this.router.navigateByUrl(`skills/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }

  experiencesPage() {
    this.router.navigateByUrl(`experiences/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }

  additionalInfoPage() {
    this.router.navigateByUrl(`additional-info/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }

  resumePage() {
    this.router.navigateByUrl(`resume/${this.authService.getUsername()}`)
      .then(() => {
        window.location.reload()
      });
  }
}
