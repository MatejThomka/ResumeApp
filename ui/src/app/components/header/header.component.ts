import {Component} from '@angular/core';
import {LoginDialogComponent} from "../login-dialog/login-dialog.component";
import {RegisterDialogComponent} from "../register-dialog/register-dialog.component";
import {GeneralService} from "../../services/general.service";
import {AsyncPipe, NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    LoginDialogComponent,
    RegisterDialogComponent,
    NgIf,
    AsyncPipe
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss',
    '../button.styles.scss']
})
export class HeaderComponent {

  session$: Observable<boolean>;

  constructor(
    public router: Router,
    public generalService: GeneralService,
    public authService: AuthService
  ) {
    this.session$ = authService.isLoggedIn$;
  }

  logout() {
    this.authService.updateLoggedInStatus(false);
    this.authService.logout(this.authService.getToken() as string);
    this.authService.removeToken();
    this.router.navigateByUrl('').then(() => {
      window.location.reload()
    });
  }
}
