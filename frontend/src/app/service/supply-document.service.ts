import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface SupplyDocument {
  id: number;
  name: string;
  subject: string;
  createdBy: string;
  createdDateTime: string;
  selected: boolean;
  warehouseId: number,
  itemId: number
  status: string
  employeeName: string;
  warehouseName: string;
  itemName: string;

}

@Injectable({
  providedIn: 'root'
})
export class SupplyDocumentService {
  private baseUrl = 'http://localhost:8080/api/v1/supplyDocument';

  constructor(private http: HttpClient) {
  }

  getSupplyDocuments(): Observable<SupplyDocument[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<SupplyDocument[]>(`${this.baseUrl}`, {headers})
  }

  addSupplyDocument(name: string, subject: string, warehouseId: number, itemId: number): Observable<SupplyDocument> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<SupplyDocument>(
      `${this.baseUrl}`,
      {name, subject, warehouseId, itemId},
      {headers}
    );
  }


  deleteSelectedSupplyDocuments(documentIds: number[]): Observable<void> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });


    return this.http.post<void>(`${this.baseUrl}/deleteSelected`, documentIds, {headers});
  }

  getManagerSupplyDocuments(): Observable<SupplyDocument[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<SupplyDocument[]>(`${this.baseUrl}/manager`, {headers})
  }

  updateDocumentStatus(updatePayload: { id: number, status: string }): Observable<void> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.put<void>(`${this.baseUrl}/updateStatus`, updatePayload, {headers});
  }

}
