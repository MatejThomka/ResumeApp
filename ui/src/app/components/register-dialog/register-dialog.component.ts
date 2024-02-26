import {Component, OnInit} from '@angular/core';
import {GeneralService} from "../../services/general.service";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
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
    public authService: AuthService
  ) {  }

  ngOnInit() {
  }

  register() {
    // this.checkCredential();
    this.authService.register(this.email, this.password, this.username).subscribe(
      (response) => {
        this.authService.saveToken(response.token);
        this.authService.saveUsername(response.username);
        this.message = 'Register successfully!';
        this.messageType = 'success';
        setTimeout(() => {
          this.message = '';
          this.generalService.showRegisterDialog = false;
        }, 2000);
      },
      (error) => {
        console.error('Login error', error);
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
