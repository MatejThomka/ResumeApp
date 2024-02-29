import {Component, OnInit} from '@angular/core';
import {GeneralService} from "../../services/general.service";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {NgIf} from "@angular/common";
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    NgIf,
    RouterLink,
    RouterLinkActive,
    RouterOutlet
  ],
  styleUrl: './register-dialog.component.scss'
})
export class RegisterDialogComponent implements OnInit {

  email: string = '';
  password: string = '';
  username: string = '';
  message: string = '';
  messageType: string = '';

  constructor(
    public generalService: GeneralService,
    public authService: AuthService,
    public router: Router
  ) {  }

  ngOnInit() {
  }

  /**
   * Method for registering a new user.
   * Upon calling this method, the authService service is invoked to register the user with provided credentials.
   * Upon successful registration, the token and username are saved in the local storage, and a success message is displayed.
   * If an error occurs, an error message is displayed; if the error status code is 409, it indicates a conflict.
   */
  register() {
    this.authService.register(this.email, this.password, this.username).subscribe(
      (response) => {
        this.authService.saveToken(response.token);
        this.authService.saveUsername(response.username);
        this.message = 'Register successfully!';
        this.messageType = 'success';
        setTimeout(() => {
          this.message = '';
          this.generalService.showRegisterDialog = false;
          this.router.navigateByUrl(`user/${localStorage.getItem('username')}`).then(() => {
            window.location.reload();
          })
        }, 2000);
      },
      (error) => {
        if (error.status === 409) {
          this.message = error.error;
          this.messageType = 'error';
          setTimeout(() => {
            this.message = '';
          }, 5000);
        }
      }
    )
  }
}
