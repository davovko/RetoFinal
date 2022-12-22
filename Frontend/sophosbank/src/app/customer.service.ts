import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from './models/customer';



@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(
    private http: HttpClient
  ) { }

    getAll() : Observable<Customer[]>{
      return this.http.get<Customer[]>('/api/customers')
    }

    create(customer: any){
      return this.http.post<any>('/api/customers/createCustomer', customer)
    }

    update(id: number, customer: any){
      return this.http.put<any>('/api/customers/updateCustomer/' + id, customer)
    }

    delete(id: number){
      return this.http.delete('/api/customers/' + id)
    }

    /*
    getHeaders(){
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer rqerqwreqwe`
      });
      return {headers};
    }*/


}
