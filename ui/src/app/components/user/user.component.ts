import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    FormsModule,
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
    public userService: UserService,
    public router: Router,
    public authService: AuthService
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

  saveDetails() {
    this.userService.updateCredentials(
      localStorage.getItem('jwt_token') as string,
      localStorage.getItem('username') as string,
      this.username,
      this.name,
      this.lastname,
      this.phoneNumber,
    ).subscribe(response => {
      this.username = response.username;
      this.name = response.name;
      this.lastname = response.lastname;
      this.phoneNumber = response.phoneNumber;
      this.authService.saveUsername(response.username);
      this.router.navigateByUrl(`user/${localStorage.getItem('username')}`).then(() => {
        window.location.reload();
      })
    })

  }
}
