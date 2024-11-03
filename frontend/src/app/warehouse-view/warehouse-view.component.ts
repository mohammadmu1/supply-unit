import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Router } from '@angular/router';
import {Warehouse, WarehouseService} from "../service/warehouse-service.service";

@Component({
  selector: 'app-warehouse-view',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './warehouse-view.component.html',
  styleUrls: ['./warehouse-view.component.css']
})
export class WarehouseViewComponent implements OnInit {

  warehouses: Warehouse[] = [];
  ngOnInit(): void {
    this.loadWarehouses();
  }

  constructor(
    private warehouseService:WarehouseService,
    private router:Router
  ) {
  }

  loadWarehouses(): void {
    this.warehouseService.getWarehouses().subscribe(
      data => {this.warehouses = data;

      },
      error => console.error('Error fetching warehouses', error)
    );
  }



  deleteWarehouse(name: string): void {
    this.warehouseService.deleteWarehouse(name).subscribe(
      () => {
        console.log(`Warehouse '${name}' deleted successfully.`);
        this.warehouses = this.warehouses.filter(warehouse => warehouse.name !== name);
      },
      error => console.error(`Error deleting warehouse '${name}':`, error)
    );
  }


  addWarehouse() {
    this.warehouseService.warehouses = this.warehouses;
    this.router.navigate(['warehouse/add']);
  }

  viewItems(warehouseName: string): void {
    this.router.navigate(['warehouse', warehouseName, 'items']);
  }

}
