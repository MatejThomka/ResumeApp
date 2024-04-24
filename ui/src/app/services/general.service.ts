import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {
  showLoginDialog = false;
  showRegisterDialog = false;
  showDeleteConfirmationDialog = false;
  constructor() { }
}
