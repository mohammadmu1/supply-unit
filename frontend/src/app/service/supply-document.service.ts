import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface SupplyDocument {
  id: number;
  name: string;
  subject: string;
  createdBy: string;
  createdDateTime: string;
  selected: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SupplyDocumentService {
  private baseUrl = 'http://localhost:8080/api/v1/supplyDocument';

  constructor(private http: HttpClient) {}

  getSupplyDocuments(): Observable<SupplyDocument[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<SupplyDocument[]>(`${this.baseUrl}`, { headers }).pipe(
      map((response) => response)
    );
  }

  // addSupplyDocument(name: string, subject: string, createdBy: string): Observable<SupplyDocument> {
  //   const token = localStorage.getItem('jwtToken');
  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${token}`,
  //     'Content-Type': 'application/json'
  //   });
  //
  //   return this.http.post<SupplyDocument>(
  //     `${this.baseUrl}`,
  //     { name, subject, createdBy },
  //     { headers }
  //   );
  // }

  deleteSelectedSupplyDocuments(documentIds: number[]): Observable<void> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<void>(`${this.baseUrl}/deleteSelected`,
      { documentIds },
      { headers }).pipe(
      map((response) => response)
    );
  }

}
