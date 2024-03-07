import {Component, OnInit} from '@angular/core';
import {GeneralService} from "../../services/general.service";
import {AuthService} from "../../services/auth.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgIf} from "@angular/common";
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    NgIf,
    RouterLink,
    RouterLinkActive,
    RouterOutlet
  ],
  styleUrl: 'login-dialog.component.scss'
})
export class LoginDialogComponent implements OnInit {

  email = '';
  password = '';
  message= '';
  messageType = '';

  constructor(
    public generalService: GeneralService,
    public authService: AuthService,
    public router: Router
  ) { }

  ngOnInit() {
  }

  /**
   * Method for user login.
   * Invokes the login method of the authService service with provided email and password.
   * If the login is successful, the token and username are saved in the local storage, and a success message is displayed.
   * If an error occurs, an error message is displayed; if the error status code is 403, it indicates incorrect credentials.
   */
  login() {
    this.authService.login(this.email, this.password).subscribe(
      response => {
        this.authService.saveToken(response.token);
        this.authService.saveUsername(response.username);
        this.authService.isLoggedIn = true;
        this.message = 'Login successfully!'
        this.messageType = 'success';
        setTimeout(() =>
          {
            this.message = '';
            this.generalService.showLoginDialog = false;
            this.router.navigateByUrl(`user/${localStorage.getItem('username')}`).then(() => {
              window.location.reload();
            });
          }, 2000);
      },
      error => {
        if (error.status === 403) {
          this.message = "Incorrect credentials!";
          this.messageType = 'error';
        }
        setTimeout(() => {
          this.message = '';
          }, 5000);
      }
    )
  }
}
