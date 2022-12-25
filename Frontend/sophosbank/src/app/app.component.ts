import { Component, OnInit } from '@angular/core';
import { LoginService } from './login.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'sophosbank';
  userName: string = "";

  constructor(    
    private loginService: LoginService,
    private router: Router
  ){}

  ngOnInit(): void {
    var token =  this.loginService.getToken();
    if(token){
      var base64Url = token.split('.')[1];
      var base64 = base64Url.replace('-', '+').replace('_', '/');      
      var obj = JSON.parse(atob(base64));

      localStorage.setItem('userName', obj.name)

      this.userName = localStorage.getItem('userName') || "";      

    }      
  }

  logOut(){
    Swal.fire({
      title: 'Desea cerrar su sesión?',
      text: "",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si!'
    }).then((result) => {
      if (result.isConfirmed) {
        localStorage.removeItem('token');
        Swal.fire({
          icon: 'success',
          title: 'Sesión eliminada!',
          showConfirmButton: false,
          timer: 1800
        })
        this.router.navigate(['/'])
      }
    })
    localStorage.removeItem('token');
    this.userName = '';
  }
}
