import {AfterViewInit, Component, ElementRef,} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {GeneralService} from "./services/general.service";
import {NgIf} from "@angular/common";
import {LoginDialogComponent} from "./components/login-dialog/login-dialog.component";
import {RegisterDialogComponent} from "./components/register-dialog/register-dialog.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NgIf, LoginDialogComponent, RegisterDialogComponent],
  templateUrl: './app.component.html',
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
