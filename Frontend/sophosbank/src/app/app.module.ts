import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CustomersComponent } from './customers/customers.component';

import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductsComponent } from './products/products.component';
import { ReactiveFormsModule } from '@angular/forms';
//import { AppRoutingModule } from './app-routing.module';
import { TransactionsComponent } from './transactions/transactions.component';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AboutComponent } from './about/about.component';

const appRoutes: Routes = [  
  {path: '', component: LoginComponent},
  {path: 'customers', component: CustomersComponent},  
  {path: 'about', component: AboutComponent},
  {path: 'products/:num', component: ProductsComponent},
  {path: 'transactions/:num', component: TransactionsComponent}
  ]

@NgModule({
  declarations: [
    AppComponent,
    CustomersComponent,
    ProductsComponent,
    TransactionsComponent,
    LoginComponent,
    AboutComponent    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    //AppRoutingModule,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
