import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbDatepickerModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Params } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductsService } from '../products.service';
import { Product } from '../models/product';
import { __values } from 'tslib';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  customer_id!: number;
  products: Product[] = [];
  form: FormGroup = this.fb.group({
    product_type_id: [Validators.required],    
    gmf_exempt: [false]
  });
  constructor(
    private productService: ProductsService,
    private modal: NgbModal, 
    private fb: FormBuilder,
    private _activateRoute: ActivatedRoute
  ){}

  ngOnInit(): void {
    localStorage.removeItem('customerId');
    this._activateRoute.params.subscribe((params: Params) => {
      this.customer_id = params['num'];
    });    
    localStorage.setItem('customerId', this.customer_id.toString());
    this.getProducts(this.customer_id);
  }  

  saveProduct(){
    const values = this.form.value;   
    values.customer_id = this.customer_id     

    this.productService.createProduct(values)
      .subscribe(data => {
        if(data.success){
          this.getProducts(this.customer_id);
          this.modal.dismissAll();
          Swal.fire('Producto creado!', '', 'success') ;

        } else{
          Swal.fire('El producto no se puede crear', data.message, 'error')
        }
      })
  }

  openModal(content: any){
    this.form.reset();
    this.modal.open(content,{size:'s', centered: true})
  }

  updateStatus(product_id: number, status_account: number){

    this.productService.updateStatusAccount(product_id, status_account)
      .subscribe(data => {
        if(data.success){
          this.getProducts(this.customer_id);
          this.modal.dismissAll();
          switch(status_account){
            case 1: Swal.fire('Producto activado!', '', 'success');
                    break;
            case 2: Swal.fire('Producto inactivado!', '', 'success');
                    break;
            case 3: Swal.fire('Producto cancelado!', '', 'success');
                    break;            
          }
        } else{
          switch(status_account){
            case 1: Swal.fire('El Producto no pudo ser activado!', '', 'error');
                    break;
            case 2: Swal.fire('El Producto no pudo ser inactivado!', '', 'error');
                    break;
            case 3: Swal.fire('El Producto no pudo ser cancelado!', '', 'error');
                    break;            
          }
        }
      })
  }

  updateGmfExempt(product_id: number){

    this.productService.updateGmfExempt(product_id)
      .subscribe(data => {
        if(data.success){
          this.getProducts(this.customer_id);
          this.modal.dismissAll();
          if(data.data){
            Swal.fire('Producto marcado como exento GMF!', '', 'success');
          } else {
            Swal.fire('Producto desmarcado como exento GMF!', '', 'success');
          }
        } else{
          Swal.fire('No se pudo marcar el producto!', data.message, 'error');
        }
      })
  }

  getProducts(id: number){
    this.productService.getAllProductsByCustomerId(id)
    .subscribe(data => {
      this.products = data;   
    })

  }


}
