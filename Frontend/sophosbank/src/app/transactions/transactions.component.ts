import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbDatepickerModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Params } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Transaction } from '../models/transaction';
import { TransactionsService } from '../transactions.service';
import { Product } from '../models/product';
import { ProductsService } from '../products.service';
import { __values } from 'tslib';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  product_id!: number;
  customer_id!: number;
  transactionType! : number;
  transactions: Transaction[] = [];
  products: Product[] = [];  
  productData!: Product;  
  form: FormGroup = this.fb.group({
    transaction_type_id: [Validators.required],
    description: [Validators.required],
    transaction_value: [Validators.required],
    destination_product_id: [],
    origin_product_id: [Validators.required]
  });
  ngSelect = "";

  constructor(
    private transactionService: TransactionsService,
    private productsService: ProductsService,
    private modal: NgbModal, 
    private fb: FormBuilder,
    private _activateRoute: ActivatedRoute
  ){}

  ngOnInit(): void {    
    this._activateRoute.params.subscribe((params: Params) => {
      this.product_id = params['num'];      
    });    
    this.customer_id = Number(localStorage.getItem('customerId'));
    this.getTransactions(this.product_id);    
    this.getProductById();    
  }
  
  getProductById(){
    this.productsService.getProductById(this.product_id).subscribe(data => {
      this.productData = data; 
      console.log(data)
    })
  }

  getProducts(){
    this.productsService.getAllProductsByCustomerId(this.customer_id)
    .subscribe(data => {
      this.products = data.filter(x => x.product_id != this.product_id) ; 
    })
  }

  openModal(content: any){
    this.form.reset();
    this.modal.open(content,{size:'s', centered: true})
    this.getProducts();
    this.form.controls['destination_product_id'].disable();
  }

  enabledListOfAccount(){
    this.transactionType = this.form.value.transaction_type_id; 
    this.form.controls['destination_product_id'].reset()
    if(this.transactionType == 1 || this.transactionType == 2){       
      this.form.controls['destination_product_id'].removeValidators;     
      this.form.controls['destination_product_id'].disable();
    } else {
      this.form.controls['destination_product_id'].addValidators;     
      this.form.controls['destination_product_id'].enable();
    }
  }

  saveTransaction(){
    const values = this.form.value;   
    values.product_id = this.product_id     

    this.transactionService.createTransaction(values)
      .subscribe(data => {
        if(data.success){
          this.getTransactions(this.product_id);
          this.modal.dismissAll();
          this.getProductById();    
          Swal.fire('Transacción realizada exitosamente!', '', 'success') ;

        } else{
          Swal.fire('No se pudo realizar la transacción', data.message, 'error')
        }
      })
  }

  getTransactions(id: number){
    this.transactionService.getAllTransactionsByProductId(id)
    .subscribe(data => {
      this.transactions = data;  
    })
  }

}
