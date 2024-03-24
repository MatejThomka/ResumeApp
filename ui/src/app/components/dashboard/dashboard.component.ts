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

  profile() {
    this.router.navigateByUrl(`user/${this.authService.getUsername()}`)
      .then(() => {
      window.location.reload()
    });
  }

}
