// add-warehouse.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { NgForOf, NgIf } from "@angular/common";
import { Router } from "@angular/router";
import { Warehouse, WarehouseService } from "../service/warehouse-service.service";

@Component({
  selector: 'app-add-warehouse',
  templateUrl: './add-warehouse.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  styleUrls: ['./add-warehouse.component.css']
})
export class AddWarehouseComponent implements OnInit {
  warehouseForm: FormGroup;
  errorMessage: string = '';
  warehouses: Warehouse[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private warehouseService: WarehouseService
  ) {
    this.warehouseForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      items: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.warehouseService.getWarehouses().subscribe(
      (response) => {
        this.warehouses = response;
      },
      (error) => {
        console.error("Error loading warehouses:", error);
      }
    );
  }

  get items(): FormArray {
    return this.warehouseForm.get('items') as FormArray;
  }

  addItem(): void {
    const itemGroup = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      quantity: [1, [Validators.required, Validators.min(1)]]
    });
    this.items.push(itemGroup);
  }

  removeItem(index: number): void {
    this.items.removeAt(index);
  }

  onSubmit(): void {
    if (this.warehouseForm.valid) {
      const warehouseData = this.warehouseForm.value;
      console.log('Payload to be sent:', warehouseData);

      const duplicate = this.warehouses.some((wh) => wh.name === warehouseData.name);
      if (duplicate) {
        this.showErrorMessage('This warehouse name already exists. Please choose another name.');
      } else {
        this.warehouseService.addWarehouse(warehouseData.name, warehouseData.description, warehouseData.items).subscribe(
          (response) => {
            console.log('Warehouse added successfully:', response);
            console.log(warehouseData.items);
            this.resetForm();
          },
          (error) => {
            this.showErrorMessage('This warehouse name already exists with another Manager. Please choose another name.');
            console.error('Error adding warehouse:', error);
          }
        );
        this.errorMessage = '';
      }
    } else {
      console.log('Form is invalid');
    }
  }

  private resetForm(): void {
    this.warehouseForm.reset();
    this.items.clear();
  }

  private showErrorMessage(message: string): void {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = '';
      this.resetForm();
    }, 2000);
  }



  back(): void {
    this.router.navigate(['/warehouse']);
  }
}
