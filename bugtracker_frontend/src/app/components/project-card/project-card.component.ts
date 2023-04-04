import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/models/project';
import { User } from 'src/app/models/user';
import { ProjectService } from 'src/app/services/project.service';
import { DevAssignmentComponent } from '../dev-assignment/dev-assignment.component';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.css'],
})
export class ProjectCardComponent {
  @Input('project') project!: Project;
  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private dialog: MatDialog
  ) {}

  onAddMembersToProject() {
    const dialogRef = this.dialog.open(DevAssignmentComponent, {
      width: '400px',
      data: this.project,
      panelClass: 'dialog',
    });
  }

  removeDev(member: User) {
    console.log('member name: ' + member.firstName);
    this.projectService
      .removeMemberFromProject(this.project.id, member.id)
      .subscribe();
  }
}
