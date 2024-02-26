import {Component, OnInit} from '@angular/core';
import {GeneralService} from "../../services/general.service";
import {AuthService} from "../../services/auth.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    NgIf
  ],
  styleUrl: 'login-dialog.component.scss'
})
export class LoginDialogComponent implements OnInit {

  email: string = '';
  password: string = '';
  message: string = '';
  messageType: string = '';

  constructor(
    public generalService: GeneralService,
    public authService: AuthService
  ) { }

  ngOnInit() {
  }

  login() {
    this.authService.login(this.email, this.password).subscribe(
      (response) => {
        this.authService.saveToken(response.token);
        this.authService.saveUsername(response.username);
        this.message = 'Login successfully!'
        this.messageType = 'success';
        setTimeout(() =>
          {
            this.message = '';
            this.generalService.showLoginDialog = false;
          }, 2000);
      },
      (error) => {
        console.error('Register error', error);
        if (error.status === 403) {
          this.message = 'Incorrect credentials!';
          this.messageType = 'error';
          setTimeout(() => {
            this.message = '';
          }, 5000);
        }
      }
    )
  }
}
