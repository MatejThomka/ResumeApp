import { Component } from '@angular/core';
import {GeneralService} from "../../services/general.service";

@Component({
  selector: 'app-register-dialog',
  standalone: true,
  imports: [],
  templateUrl: './register-dialog.component.html',
  styleUrl: '../dialog.component.scss'
})
export class RegisterDialogComponent {
  constructor(public generalService: GeneralService) {  }

}
