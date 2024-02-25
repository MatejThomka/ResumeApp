import {AfterViewInit, Component, ElementRef,} from '@angular/core';
import {GeneralService} from "./services/general.service";
import {RegisterDialogComponent} from "./components/register-dialog/register-dialog.component";
import {LoginDialogComponent} from "./components/login-dialog/login-dialog.component";
import {NgIf} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    RegisterDialogComponent,
    LoginDialogComponent,
    NgIf,
    HttpClientModule
  ],
  styleUrl: './app.component.scss'
})
export class AppComponent implements AfterViewInit{
  title = 'ResumeAppUI';

  constructor(
    public generalService: GeneralService,
    private elementRef: ElementRef
  ) {  }

  ngAfterViewInit() {
    this.elementRef.nativeElement.ownerDocument
      .body.style.background = 'rgba(85,231,214,0.29)'
  }
}
