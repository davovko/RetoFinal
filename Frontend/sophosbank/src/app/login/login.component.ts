import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { Credentials } from '../models/credentials';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  creds: Credentials = {
    email: '',
    password: ''
  };  

  constructor(
    private loginService: LoginService,
    private router: Router,
    ){    
  }

  ngOnInit(): void {
    if(this.loginService.getToken()){
      this.router.navigate(['/customers']);
      this.ngOnInit
    }
  }

  logIn(form: NgForm){
    this.loginService.login(this.creds)
      .subscribe(response => {
        this.router.navigate(['/customers'])   
      })
  }

}
