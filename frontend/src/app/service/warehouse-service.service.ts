import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

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

    return this.http.get<Warehouse[]>(`${this.baseUrl}`, {headers}).pipe(
      map((response) => {
        this.warehouses = response;
        console.log("Service response:", this.warehouses);
        return response;
      })
    );
  }

  getAllWarehousesWithItems(): Observable<Warehouse[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<Warehouse[]>(`${this.baseUrl}/all`, {headers}).pipe(
      map((response) => {
        this.warehouses = response;
        console.log("Service response:", this.warehouses);
        return response;
      })
    );
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
    ).pipe(
      map((response) => response)
    );
  }

  deleteWarehouse(name: string): Observable<any> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.delete<any>(`${this.baseUrl}/${name}`, {headers}).pipe(
      map((response) => response)
    );
  }

  getWarehouseItems(warehouseName: string): Observable<Item[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<Item[]>(`${this.baseUrl}/items`, { warehouseName }, { headers }).pipe(
      map((response) => response)
    );
  }


}
