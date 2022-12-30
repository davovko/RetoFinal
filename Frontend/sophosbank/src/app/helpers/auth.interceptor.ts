import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
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
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        "X-Requested-With": "XMLHttpRequest"

      });
      const cloned = request.clone({
        headers: headers
      })

      return next.handle(cloned).pipe(catchError((error: HttpErrorResponse) =>{
        this.loginService.logout();
        this.router.navigate(['']);
        return throwError("")
      }));
    }    
    return next.handle(request).pipe(catchError((error: HttpErrorResponse) =>{
      
      return throwError("")
    }));
  }
}
