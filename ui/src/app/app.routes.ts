import { Routes } from '@angular/router';
import {UserComponent} from "./components/user/user.component";
import {HomeComponent} from "./components/home/home.component";

export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'user/:username', component: UserComponent},
  {path: 'home/:username', component: HomeComponent},
  {path: 'home', component: HomeComponent}
];
