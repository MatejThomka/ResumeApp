import {Component, OnInit} from '@angular/core';
import {DeleteConfirmationComponent} from "../delete-confirmation/delete-confirmation.component";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Skill} from "../../interfaces/skill";
import {SkillsService} from "../../services/skills.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {GeneralService} from "../../services/general.service";

@Component({
  selector: 'app-skills',
  standalone: true,
  imports: [
    DeleteConfirmationComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './skills.component.html',
  styleUrl: './skills.component.scss'
})
export class SkillsComponent implements OnInit {

  skills: Skill[] = [];
  initialSkill: Skill = {} as Skill;
  editedSkill: Skill = {} as Skill;
  newSkill: Skill = {} as Skill;

  messageType = '';
  message = '';

  isEditable: boolean[] = [];
  isCreating = false;

  constructor(
    public skillService: SkillsService,
    public router: Router,
    public authService: AuthService,
    public generalService: GeneralService
  ) { }

  ngOnInit() {
    this.fetchSkills();
  }

  fetchSkills() {
    this.skillService.getSkills(
      this.authService.getToken() as string,
      this.authService.getUsername() as string
    ).subscribe(
      skills => {
        this.skills = skills
      },
      error => {
        if (error.status === 403) {
          this.router.navigateByUrl('home').then(() => window.location.reload());
        } else if (error.status === 404) {
          this.message = 'You do not have any skills yet!';
          this.messageType = 'error';
        }
      }
    )
  }

  create() {
    this.isCreating = !this.isCreating;
  }
}
