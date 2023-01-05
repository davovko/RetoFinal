import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { Credentials } from '../models/credentials';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

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
        if(response == null){
          Swal.fire({
            icon: 'success',
            title: 'Bienvenido',
            showConfirmButton: false,
            timer: 1800
          })
          this.router.navigate(['/customers'])
        }
      }, error => {
        Swal.fire({
          icon: 'error',
          title: 'Usuarios o contraseña incorrectos',
          showConfirmButton: false,
          timer: 1800
        })
      }
      )
  }

}
