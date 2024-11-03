import { Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {WarehouseViewComponent} from "./warehouse-view/warehouse-view.component";
import {AddWarehouseComponent} from "./add-warehouse/add-warehouse.component";
import {AuthGuard} from "./service/auth-guard.service";
import {WarehouseItemsComponent} from "./warehouse-items/warehouse-items.component";
import {SupplyDocumentsViewComponent} from "./supply-documents-view/supply-documents-view.component";






export const routes: Routes = [
  // http://localhost:4200/login
  {path: 'login', component: LoginComponent},
  // http://localhost:4200
  {path: '', component: LoginComponent},
  // http://localhost:4200/addUser
  { path: 'addUser', component: SignupComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  // http://localhost:4200/warehouse
  {path: 'warehouse', component: WarehouseViewComponent, canActivate: [AuthGuard], data: { role: 'MANAGER' }},
  // http://localhost:4200/warehouse/add
  {path: 'warehouse/add', component: AddWarehouseComponent, canActivate: [AuthGuard], data: { role: 'MANAGER' }},
// http://localhost:4200/warehouse/:warehouseName/items
  { path: 'warehouse/:warehouseName/items', component: WarehouseItemsComponent , canActivate: [AuthGuard], data: { role: 'MANAGER' } },
  // http://localhost:4200/supplyDocument
  {path: 'supplyDocument', component: SupplyDocumentsViewComponent, canActivate: [AuthGuard], data: { role: 'EMPLOYEE' }},

];
