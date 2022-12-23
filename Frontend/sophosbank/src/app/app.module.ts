import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CustomersComponent } from './customers/customers.component';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductsComponent } from './products/products.component';
import { ReactiveFormsModule } from '@angular/forms';
//import { AppRoutingModule } from './app-routing.module';
import { TransactionsComponent } from './transactions/transactions.component';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AboutComponent } from './about/about.component';
import { FormsModule }   from '@angular/forms';
import { AuthInterceptor } from './helpers/auth.interceptor';
import { AuthGuard } from './helpers/auth.guard';


const appRoutes: Routes = [  
  {path: '', component: LoginComponent},
  {path: 'customers', component: CustomersComponent, canActivate: [AuthGuard]},  
  {path: 'about', component: AboutComponent, canActivate: [AuthGuard]},
  {path: 'products/:num', component: ProductsComponent, canActivate: [AuthGuard]},
  {path: 'transactions/:num', component: TransactionsComponent, canActivate: [AuthGuard]}
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
    FormsModule ,
    //AppRoutingModule,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
