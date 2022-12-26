import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, map } from 'rxjs';
import { Credentials } from './models/credentials';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  userName: BehaviorSubject<string> = new BehaviorSubject("");
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
      var base64Url = token.split('.')[1];
      var base64 = base64Url.replace('-', '+').replace('_', '/');      
      var obj = JSON.parse(atob(base64));

      localStorage.setItem('userName', obj.name)

      this.userName.next(obj.name);

      return body;
    }))
  }

  getToken(){
    return localStorage.getItem('token');
  }
}
