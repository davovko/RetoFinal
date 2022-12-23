import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs';
import { Credentials } from './models/credentials';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }

  login(creds: Credentials){
    return this.http.post('/api/login', creds,{
      observe: 'response'
    }).pipe(map((response: HttpResponse<any>) =>{
      const body = response.body;
      const headers = response.headers;

      const bearerToken = headers.get('authorization')!;
      const token = bearerToken.replace('Bearer ',"");

      localStorage.setItem('token', token)

      return body;
    }))
  }

  getToken(){
    return localStorage.getItem('token');
  }
}
