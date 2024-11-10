import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {map, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddUserService {
  private baseUrl = 'http://localhost:8080/api/v1/auth/';

  constructor(private http: HttpClient) {console.log("test")}

  addUser(userName: string, password: string, name: string, role: string): Observable<any> {
    console.log("test")
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(
      `${this.baseUrl}addUser`,
      { userName, password, name, role },
      { headers }
    )
  }
}
