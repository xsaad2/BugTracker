import {
  Component,
  EventEmitter,
  Injectable,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Project } from 'src/app/models/project';
import { ProjectService } from 'src/app/services/project.service';
import { ProjectCreationFormComponent } from '../project-creation-form/project-creation-form.component';

@Component({
  selector: 'app-projects-table',
  templateUrl: './projects-table.component.html',
  styleUrls: ['./projects-table.component.css'],
})
@Injectable({
  providedIn: 'root',
})
export class ProjectsTableComponent implements OnInit {
  @Output() employeeIdSelected = new EventEmitter<String>();

  datasource: any;
  displayedColumns = ['id', 'title', 'creator', 'Teamsize', 'nbroftickets'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  constructor(
    private projectService: ProjectService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchProjects();
    this.projectService.subscribeToProjectsChange((): void => {
      this.fetchProjects();
    });
  }
  public fetchProjects(): void {
    this.projectService.getProjects().subscribe((response): void => {
      this.datasource = new MatTableDataSource<Project>(response);
      setTimeout(() => (this.datasource.paginator = this.paginator));
    });
  }

  OnCodeClick(id: String) {
    this.router.navigate(['project', id]);
  }

  onNewProjectClick() {
    const dialogRef = this.dialog.open(ProjectCreationFormComponent, {
      width: '400px',

      data: null,
      panelClass: 'dialog',
    });
  }
}
