import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from './models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  constructor(
    private http: HttpClient
  ) { }

  getAllTransactionsByProductId(product_id: number){
    return this.http.get<Transaction[]>('api/transactions/' + product_id)

  }

  createTransaction(transaction: Transaction){
    return this.http.post<any>('api/transactions/createTransaction', transaction)
  }
}
