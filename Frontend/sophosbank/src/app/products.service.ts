import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(
    private http: HttpClient
  ) { }

  getAllProductsByCustomerId(customer_id: number) : Observable<Product[]>{
    return this.http.get<Product[]>('/api/products/listProductsByUserId/' + customer_id)    
  }

  getProductById(product_id: number) : Observable<Product>{
    return this.http.get<Product>('/api/products/' + product_id)    
  }

  createProduct(product: Product){
    return this.http.post<any>('/api/products/createProduct', product)
  }

  updateGmfExempt(product_id: number){
    return this.http.patch<any>('/api/products/updateGmfExempt/' + product_id, null)
  }

  updateStatusAccount(product_id: number, status_account: number){
    return this.http.patch<any>('/api/products/updateStatusAccount/' + product_id.toString() + "/" + status_account.toString(), null)
  }




  
}
