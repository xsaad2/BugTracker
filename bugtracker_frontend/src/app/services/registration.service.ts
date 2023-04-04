import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  constructor(private http: HttpClient) {}
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';

  register(model: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.apiServerUrl}/registration`, {
      firstName: model.firstName,
      lastName: model.lastName,
      email: model.email,
      password: model.password,
    });
  }
  checkEmail(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiServerUrl}/emailcheck/${email}`);
  }
}
