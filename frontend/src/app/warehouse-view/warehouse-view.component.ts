import { Component, OnInit } from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import * as XLSX from 'xlsx'; // Import the xlsx library
import { Router } from '@angular/router';
import { Warehouse, WarehouseService } from "../service/warehouse-service.service";

@Component({
  selector: 'app-warehouse-view',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './warehouse-view.component.html',
  styleUrls: ['./warehouse-view.component.css']
})
export class WarehouseViewComponent implements OnInit {

  warehouses: Warehouse[] = [];

  ngOnInit(): void {
    this.loadWarehouses();
  }

  constructor(
    private warehouseService: WarehouseService,
    private router: Router
  ) {}

  loadWarehouses(): void {
    this.warehouseService.getWarehouses().subscribe(
      data => { this.warehouses = data; },
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

  docs() {
    this.warehouseService.warehouses = this.warehouses;
    this.router.navigate(['supplyDocument/manager']);
  }

  viewItems(warehouseName: string): void {
    this.router.navigate(['warehouse', warehouseName, 'items']);
  }

  exportToExcel(): void {
    const data: any[] = [];

    this.warehouses.forEach(warehouse => {
      data.push({
        'Warehouse Name': warehouse.name,
        'Description': warehouse.description,
        'Created Date': warehouse.createdDateTime,
        'Item Name': '',  // Blank row for warehouse-level data
        'Item Description': '',
        'Quantity': ''
      });

      warehouse.items.forEach(item => {
        data.push({
          'Warehouse Name': '',
          'Description': '',
          'Created Date': '',
          'Item Name': item.name,
          'Item Description': item.description,
          'Quantity': item.quantity
        });
      });
    });

    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = { Sheets: { 'Warehouses': worksheet }, SheetNames: ['Warehouses'] };

    XLSX.writeFile(workbook, 'Warehouses.xlsx');
  }
}
