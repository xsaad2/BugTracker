import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Project } from '../models/project';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';
  projectsListeners: (() => void)[] = [];
  projectsDevsListeners: (() => void)[] = [];

  constructor(private http: HttpClient) {}

  subscribeToProjectsChange(callback: () => void) {
    this.projectsListeners.push(callback);
  }

  emitProjectsChange(): void {
    this.projectsListeners.forEach((callback): void => {
      callback();
    });
  }
  subscribeToProjectDevsChanges(callback: () => void) {
    this.projectsDevsListeners.push(callback);
  }

  emitProjectDevsChanges(): void {
    this.projectsDevsListeners.forEach((callback): void => {
      callback();
    });
  }

  public createProject(project: Project): Observable<Project> {
    return this.http
      .post<Project>(`${this.apiServerUrl}/project`, project)
      .pipe(
        tap(() => {
          this.emitProjectsChange();
        })
      );
  }

  public getProjectMembers(projectId: string): Observable<User[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<User[]>(
      `${this.apiServerUrl}/project/members/${projectId}`
    );
  }
  public getProjectById(id: string): Observable<Project> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http
      .get<Project>(`${this.apiServerUrl}/project/${id}`, {
        headers,
      })
      .pipe(
        tap(() => {
          this.emitProjectsChange();
        })
      );
  }

  public getProjects(): Observable<Project[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<Project[]>(`${this.apiServerUrl}/projects`);
  }

  public updateProject(project: Project): Observable<Project> {
    return this.http.put<Project>(`${this.apiServerUrl}/project`, project);
  }

  public addMemberToProject(
    projectId: string,
    memberId: number
  ): Observable<Project> {
    return this.http.put<Project>(
      `${this.apiServerUrl}/project/${projectId}/${memberId}`,
      {}
    );
  }
  public addMembersToProject(
    projectId: string,
    membersIds: number[]
  ): Observable<Project> {
    return this.http
      .put<Project>(`${this.apiServerUrl}/project/${projectId}`, membersIds)
      .pipe(
        tap(() => {
          this.emitProjectDevsChanges();
        })
      );
  }

  public removeMemberFromProject(
    projectId: string,
    devId: number
  ): Observable<void> {
    return this.http
      .delete<void>(`${this.apiServerUrl}/project/${projectId}/${devId}`)
      .pipe(
        tap(() => {
          this.emitProjectDevsChanges();
        })
      );
  }
  public deleteProjectById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/project/${id}`);
  }
}
