import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Comment } from '../models/comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';
  commentsListeners: (() => void)[] = [];

  constructor(private http: HttpClient) {}

  subscribeToCommentsChange(callback: () => void) {
    this.commentsListeners.push(callback);
  }

  emitCommentsChange(): void {
    this.commentsListeners.forEach((callback): void => {
      callback();
    });
  }

  public createComment(comment: Comment): Observable<Comment> {
    return this.http
      .post<Comment>(`${this.apiServerUrl}/comment`, comment)
      .pipe(
        tap(() => {
          this.emitCommentsChange();
        })
      );
  }

  public getComments(): Observable<Comment[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<Comment[]>(`${this.apiServerUrl}/comments`);
  }

  public getCommentById(commentId: string): Observable<Comment> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<Comment>(`${this.apiServerUrl}/comment/${commentId}`, {
      headers,
    });
  }

  public getCommentsByTicket(ticketId: string): Observable<Comment[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.get<Comment[]>(
      `${this.apiServerUrl}/comments/by/ticket/${ticketId}`
    );
  }

  public updateProject(project: Comment): Observable<Comment> {
    return this.http.put<Comment>(`${this.apiServerUrl}/comment`, project).pipe(
      tap(() => {
        this.emitCommentsChange();
      })
    );
  }

  public deleteCommentById(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/comment/${id}`).pipe(
      tap(() => {
        this.emitCommentsChange();
      })
    );
  }
}
