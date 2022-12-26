import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.loginService.getToken();
    
    if(token){
      const cloned = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${token}`)
      })

      return next.handle(cloned).pipe(retry(1),catchError((error: HttpErrorResponse) =>{
      
        this.router.navigate(['/']);
        return throwError("")
      }));
    }    
    return next.handle(request).pipe(catchError((error: HttpErrorResponse) =>{
      
      this.router.navigate(['/customers']);
      return throwError("")
    }));
  }
}
