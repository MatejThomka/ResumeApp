import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss',
    '../button.styles.scss']
})
export class NavBarComponent {

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
    this.router.navigateByUrl(`skill/${this.authService.getUsername()}`)
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
