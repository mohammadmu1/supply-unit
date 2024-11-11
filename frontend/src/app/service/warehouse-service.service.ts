import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Item {
  id: number;
  name: string;
  description: string;
  quantity: number;
}

export interface Warehouse {
  id: number;
  name: string;
  description: string;
  createdBy: string;
  createdDateTime: string;
  items: Item[];
}

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private baseUrl = 'http://localhost:8080/api/v1/warehouses';

  constructor(private http: HttpClient) {
  }

  warehouses: Warehouse[] = [];

  getWarehouses(): Observable<Warehouse[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<Warehouse[]>(`${this.baseUrl}`, {headers})
  }

  getAllWarehousesWithItems(): Observable<Warehouse[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<Warehouse[]>(`${this.baseUrl}/all`, {headers})

  }

  addWarehouse(name: string, description: string, items: Item[]): Observable<Warehouse> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<Warehouse>(
      `${this.baseUrl}`,
      {name, description, items},
      {headers}
    )
  }

  deleteWarehouse(name: string): Observable<any> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.delete<any>(`${this.baseUrl}/${name}`, {headers})
  }

  getWarehouseItems(warehouseName: string): Observable<Item[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<Item[]>(`${this.baseUrl}/items`, {warehouseName}, {headers})
  }


}
