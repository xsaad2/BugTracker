import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';
  usersListeners: (() => void)[] = [];

  constructor(private http: HttpClient) {}

  subscribeToUsersChange(callback: () => void) {
    this.usersListeners.push(callback);
  }

  emitUsersChange(): void {
    this.usersListeners.forEach((callback): void => {
      callback();
    });
  }

  public getUsers(): Observable<User[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<User[]>(`${this.apiServerUrl}/users`, { headers });
  }
  public getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/${id}`);
  }
  public addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/user`, user);
  }
  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiServerUrl}/userdetails`, user).pipe(
      tap(() => {
        this.emitUsersChange();
      })
    );
  }
  public assignTeamToUser(userId: number, teamId: number): Observable<User> {
    return this.http.put<User>(
      `${this.apiServerUrl}/user/${userId}/team/${teamId}`,
      undefined
    );
  }
  public deleteUserById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/${id}`).pipe(
      tap(() => {
        this.emitUsersChange();
      })
    );
  }
}
