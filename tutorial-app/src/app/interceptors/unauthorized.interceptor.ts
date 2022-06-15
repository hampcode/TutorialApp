import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserStorageService } from '../services/user-storage.service';
import { Router } from '@angular/router';
@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {
  constructor(
    private userStorageService: UserStorageService,
    private router: Router
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.userStorageService.destroy();
          this.router.navigate(['/login']);
        }

        return throwError(error);
      })
    );
  }
}
