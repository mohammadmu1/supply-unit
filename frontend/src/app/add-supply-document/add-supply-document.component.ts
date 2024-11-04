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

  loadWarehouses(): void {
    this.warehouseService.getAllWarehousesWithItems().subscribe({
      next: (warehouses) => {
        this.warehouses = warehouses.filter(warehouse => warehouse.items && warehouse.items.length > 0);
      },
      error: (error) => {
        console.error('Error loading warehouses:', error);
      }
    });
  }

  onWarehouseChange(): void {
    if (this.selectedWarehouse) {
      this.items = this.selectedWarehouse.items || [];
      this.selectedItemId = undefined;
      this.newDocument.warehouseId = this.selectedWarehouse.id;
    }
  }

  onItemChange(): void {
    this.newDocument.itemId = this.selectedItemId;
  }

  addDoc(): void {
    // Log the document data for debugging purposes
    console.log(this.newDocument);

    // Destructure values from `newDocument` and pass them to `addSupplyDocument` method
    if (this.newDocument.name && this.newDocument.subject && this.newDocument.warehouseId && this.newDocument.itemId) {
      this.supplyDocumentService
        .addSupplyDocument(
          this.newDocument.name,
          this.newDocument.subject,
          this.newDocument.warehouseId,
          this.newDocument.itemId
        )
        .subscribe({
          next: (response) => {
            console.log('Document added successfully', response);
            // Reset the form or perform any other success action
            this.newDocument = {};
          },
          error: (error) => {
            console.error('Error adding document', error);
          }
        });
    } else {
      console.error('Please fill out all required fields');
    }
  }
}
