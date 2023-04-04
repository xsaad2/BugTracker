import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-project-creation-form',
  templateUrl: './project-creation-form.component.html',
  styleUrls: ['./project-creation-form.component.css'],
})
export class ProjectCreationFormComponent implements OnInit {
  user: any;
  projectForm!: FormGroup;

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.projectForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  onSubmit(event: Event) {
    event.preventDefault();
    const projectDto: any = {
      title: this.projectForm.value.title,
      description: this.projectForm.value.description,
    };
    console.log('model: ' + projectDto.title + ' ' + projectDto.membersIds);
    this.projectService.createProject(projectDto).subscribe(
      (response) => {
        this._snackBar.open('Project created successfully', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar'],
        });
      },
      (error) => {
        this._snackBar.open('Project creation failed', 'OK', {
          duration: 5000,
          panelClass: ['failure-snackbar'],
        });
      }
    );
    this.dialog.closeAll();
    this.router.navigate(['projects']);
    console.log('from project creation component');
  }
}
