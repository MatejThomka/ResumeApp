import {AfterViewInit, Component, ElementRef} from '@angular/core';
import {RegisterDialogComponent} from "./components/register-dialog/register-dialog.component";
import {LoginDialogComponent} from "./components/login-dialog/login-dialog.component";
import {NgIf} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {HeaderComponent} from "./components/header/header.component";
import {RouterOutlet} from "@angular/router";
import {UserComponent} from "./components/user/user.component";
import {FooterComponent} from "./components/footer/footer.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    RegisterDialogComponent,
    LoginDialogComponent,
    NgIf,
    HttpClientModule,
    HeaderComponent,
    RouterOutlet,
    UserComponent,
    FooterComponent,
    DashboardComponent,
  ],
  styleUrl: './app.component.scss'
})
export class AppComponent implements AfterViewInit {
  title = 'ResumeAppUI';


  constructor(private elementRef: ElementRef) {  }


  ngAfterViewInit() {
    this.elementRef.nativeElement.ownerDocument
      .body.style.background = 'rgba(85,231,214,0.29)'
  }
}
