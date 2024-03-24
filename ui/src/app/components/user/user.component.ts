import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
  ],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss',
    '../button.styles.scss']
})
export class UserComponent implements OnInit {

  username = '';
  name = '';
  lastname = '';
  email = '';
  confirmationEmail = '';
  phoneNumber = '';
  isEditableCredentials = false;
  isEditableEmail = false;
  isEditablePassword = false;
  emailMessage = '';
  passwordMessage = '';
  messageType = '';
  currentPassword = '';
  newPassword = '';
  confirmNewPassword = '';

  constructor(
    public userService: UserService,
    public router: Router,
    public authService: AuthService
  ) {
  }

  ngOnInit() {
    this.userService.userDetails(
      localStorage.getItem('jwt_token') as string,
      localStorage.getItem('username') as string,
    ).subscribe(
      response => {
        this.username = response.username;
        this.name = response.name;
        this.lastname = response.lastname;
        this.email = response.email;
        this.phoneNumber = response.phoneNumber;
      },
      error => {
        if (error.status === 403) {
          this.router.navigateByUrl('home').then(() => window.location.reload());
        }
      }
    )
  }

  saveDetails() {
    this.userService.updateCredentials(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.username,
      this.name,
      this.lastname,
      this.phoneNumber,
    ).subscribe((response) => {
      this.username = response.username;
      this.name = response.name;
      this.lastname = response.lastname;
      this.phoneNumber = response.phoneNumber;
      this.authService.saveUsername(response.username);
      this.router.navigateByUrl(`user/${this.authService.getUsername()}`).then(() => {
        window.location.reload();
      })
    })
  }

  editCredential() {
    this.isEditableCredentials = !this.isEditableCredentials;
  }

  editEmail() {
    this.isEditableEmail = !this.isEditableEmail;
  }

  changeEmail() {
    this.userService.emailChange(
      this.authService.getToken() as string,
      this.authService.getUsername() as  string,
      this.email,
      this.confirmationEmail
    ).subscribe(
      response => {
        this.messageType = 'success';
        this.emailMessage = response.message;
        setTimeout(() => {
          this.emailMessage = '';
        }, 2000);
      },
      error => {
        this.messageType = 'error';
        this.emailMessage = error.error;
        setTimeout(() => {
          this.emailMessage = '';
        }, 2000);
      }
    )

  }

  changePassword() {
    this.userService.passwordChange(
      this.authService.getToken() as string,
      this.authService.getUsername() as string,
      this.currentPassword,
      this.newPassword,
      this.confirmNewPassword
    ).subscribe(
      response => {
        this.messageType = 'success';
        this.passwordMessage = response.message;
        setTimeout(() => {
          this.passwordMessage = '';
        }, 2000);
      },
      error => {
        this.messageType = 'error';
        this.passwordMessage = error.error;
        setTimeout(() => {
          this.passwordMessage = '';
        }, 2000);
      }
    )
  }

  editPassword() {
    this.isEditablePassword = !this.isEditablePassword;
  }

  goBack() {
    this.router.navigateByUrl(`home/${this.authService.getUsername()}`).then(() => {
      window.location.reload()
    });
  }
}
