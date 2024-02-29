import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {FormsModule} from "@angular/forms";
import {NonNullAssert} from "@angular/compiler";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit{

  username = '';
  name = '';
  lastname = '';
  email = '';
  phoneNumber = '';

  constructor(
    public userService: UserService
    ) { }

  ngOnInit() {
    this.userService.userDetails(
      localStorage.getItem('jwt_token') as string,
      localStorage.getItem('username') as string,
    ).subscribe(
      (response) => {
        this.username = response.username;
        this.name = response.name;
        this.lastname = response.lastname;
        this.email = response.email;
        this.phoneNumber = response.phoneNumber;
      },
    (error) => {
      console.error(error);
    }
    )
  }
}
