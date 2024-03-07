import { Component } from '@angular/core';
import {LoginDialogComponent} from "../login-dialog/login-dialog.component";
import {RegisterDialogComponent} from "../register-dialog/register-dialog.component";
import {GeneralService} from "../../services/general.service";
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    LoginDialogComponent,
    RegisterDialogComponent,
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  constructor(
    public generalService: GeneralService,
    public authService: AuthService
  ) {
  }

  logout() {
    this.authService.logout(this.authService.getToken() as string);
    this.authService.removeToken();
  }
}
