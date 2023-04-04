import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { LoaderService } from '../services/loader.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
  constructor(private loaderService: LoaderService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let token = sessionStorage.getItem('token');

    if (token) {
      request = request.clone({
        headers: request.headers.set('Authorization', token),
      });
    }

    //here we set the loading state to true, to display the loading bar
    this.loaderService.setLoading(true);

    //after loading is finished
    return next.handle(request).pipe(
      finalize(() => {
        this.loaderService.setLoading(false);
      })
    );
  }
}
