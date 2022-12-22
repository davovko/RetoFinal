import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbDatepickerModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { CustomerService } from '../customer.service';
import { Customer } from '../models/customer';


@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit{
  customers: Customer[] = [];
  form: FormGroup = this.fb.group({
    customer_id: [0],
    identification_type_Id: [Validators.required],
    identification_number: [Validators.required],
    first_name: [Validators.required, Validators.minLength(2)],
    middle_name: [],
    last_name: [Validators.required, Validators.minLength(2)],
    second_last_name: [],
    email: [Validators.required, Validators.email],
    date_of_birth: [Validators.required]
  });
  customerInEditon: any;

  constructor(
    private customerService: CustomerService,
    private modal: NgbModal, 
    private fb: FormBuilder

  ){ }

  ngOnInit(): void {
    this.getCustomers();
  }  
  
  openModal(content: any, formEnable: boolean){
    this.form.reset();
    formEnable ? this.form.enable() : this.form.disable();  
    this.modal.open(content,{size:'xl'})
  }
  saveCustomer(){
    const values = this.form.value;

    if(this.customerInEditon){
      this.customerService.update(values.customer_id, values)
    .subscribe(data => {
      if(data.success){
        this.getCustomers();
        this.modal.dismissAll();
        Swal.fire('Cliente actualizado!', '', 'success') ;
        this.customerInEditon = false;
      } else{
        Swal.fire('El cliente no se puede actualizar', data.message, 'error')
      }
    })

    }else{
      this.customerService.create(values)
      .subscribe(data => {
        if(data.success){
          this.getCustomers();
          this.modal.dismissAll();
          Swal.fire('Cliente creado!', '', 'success') ;
          this.customerInEditon = true;
        } else{
          Swal.fire('El cliente no se puede crear', data.message, 'error')
        }
      })

    }
    

    

  }

  updateCustomer(customer: Customer){
    this.customerInEditon = true;
    this.form.setValue({
      customer_id: customer.customer_id,
      identification_type_Id: customer.identification_type_Id,
      identification_number: customer.identification_number,
      first_name: customer.first_name,
      middle_name: customer.middle_name,
      last_name: customer.last_name,
      second_last_name: customer.second_last_name,
      email: customer.email,
      date_of_birth: customer.date_of_birth
    })   
  }
 
  getCustomers(){
    this.customerService.getAll()
    .subscribe(data => {
      this.customers = data.filter(x => x.status == true) ; 
    })
  }

  deleteCustomer(id: number){    
    Swal.fire({
      title: `Desea eliminar al cliente ID: ${id} ?`,
      text: "No podra recuperar sus datos!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      denyButtonText: `Cancelar`,
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        this.customerService.delete(id)
        .subscribe(data => {
          this.getCustomers();
          Swal.fire('Cliente eliminado!', '', 'success') ;},
          error => {
            Swal.fire('El cliente no se puede eliminar', 'El cliente aun no tiene todos sus productos cancelados', 'info')
          })
        
      }
    })
  }
}


