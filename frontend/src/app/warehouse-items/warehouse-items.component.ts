import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {Item, WarehouseService} from '../service/warehouse-service.service';
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-warehouse-items',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './warehouse-items.component.html',
  styleUrls: ['./warehouse-items.component.css']
})
export class WarehouseItemsComponent implements OnInit {

  warehouseName: string = '';
  items: Item[] = [];

  constructor(
    private route: ActivatedRoute,
    private warehouseService: WarehouseService,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {

    this.warehouseName = this.route.snapshot.paramMap.get('warehouseName') || '';
    this.loadItems();
  }

  loadItems(): void {
    this.warehouseService.getWarehouseItems(this.warehouseName).subscribe(
      (items) => {
        this.items = items;
      },
      (error) => {
        console.error(`Error fetching items for warehouse '${this.warehouseName}':`, error);
      }
    );
  }
}
