import {Component, OnInit} from '@angular/core';
import {GeneralService} from "../../services/general.service";

@Component({
  selector: 'app-login-dialog',
  standalone: true,
  imports: [],
  templateUrl: './login-dialog.component.html',
  styleUrl: '../dialog.component.scss'
})
export class LoginDialogComponent implements OnInit {
  constructor(public generalService: GeneralService) { }

  ngOnInit() {
  }
}
