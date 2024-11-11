import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SupplyDocumentService } from '../service/supply-document.service';
import { SupplyDocument } from '../service/supply-document.service';
import { WarehouseService, Warehouse, Item } from '../service/warehouse-service.service';
import { FormsModule } from "@angular/forms";
import {NgClass, NgForOf} from "@angular/common";

@Component({
  selector: 'app-add-supply-document',
  templateUrl: './add-supply-document.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgClass
  ],
  styleUrls: ['./add-supply-document.component.css']
})
export class AddSupplyDocumentComponent implements OnInit {
  warehouses: Warehouse[] = [];
  items: Item[] = [];
  selectedWarehouse?: Warehouse;
  selectedItemId?: number;

  newDocument: Partial<SupplyDocument> = {
    name: '',
    subject: '',
    warehouseId: undefined,
    itemId: undefined
  };

  constructor(
    private supplyDocumentService: SupplyDocumentService,
    private warehouseService: WarehouseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadWarehouses();
  }

  loadWarehouses(): void {  //client side filters ( filter warehouses without items )
    this.warehouseService.getAllWarehousesWithItems().subscribe(
      (warehouses) => {
        this.warehouses = warehouses.filter(warehouse => warehouse.items && warehouse.items.length > 0);
      },
       (error) => {
        console.error('Error loading warehouses:', error);
      }
    );
  }

  onWarehouseChange(): void {
    if (this.selectedWarehouse) {
      this.items = this.selectedWarehouse.items || []; // items if there is items with in warehouse or empty list
      this.selectedItemId = undefined;
      this.newDocument.warehouseId = this.selectedWarehouse.id;
    }
  }

  onItemChange(): void {
    this.newDocument.itemId = this.selectedItemId;
  }

  addDoc(): void {
    console.log(this.newDocument);

    if (this.newDocument.name && this.newDocument.subject && this.newDocument.warehouseId && this.newDocument.itemId) {
      this.supplyDocumentService
        .addSupplyDocument(
          this.newDocument.name,
          this.newDocument.subject,
          this.newDocument.warehouseId,
          this.newDocument.itemId
        )
        .subscribe(
           (response) => {
            console.log('Document added successfully', response);
             window.location.reload();
           },
           (error) => {
            console.error('Error adding document', error);
          }
        );
    } else {
      console.error('Please fill out all required fields');
    }
  }

  goBack() {
    this.router.navigate(['/supplyDocument']);
  }



}
