import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseUrl = 'http://localhost:8080/api/v1/';
  private readonly tokenKey = 'jwtToken';

  constructor(private http: HttpClient) {}

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const expiry = JSON.parse(atob(token.split('.')[1])).exp;
      return Math.floor(new Date().getTime() / 1000) < expiry;
    } catch (e) {
      this.clearToken();
      return false;
    }
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  executeAuthentication(userName: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}auth/login`, { userName, password }).pipe(
      map((response) => {
        if (response && response.token) {
          this.setToken(response.token);
        }
        return response;
      })
    );
  }

  getUserRole(): string {
    const token = this.getToken();
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.role || '';
      } catch (e) {
        this.clearToken();
        return '';
      }
    }
    return '';
  }

  isTokenExpired(): boolean {
    const token = this.getToken();

    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const expiration = payload.exp * 1000;
        if (Date.now() > expiration) {
          this.clearToken();
          return true;
        }
      } catch (e) {
        this.clearToken();
        return true;
      }
    }
    return false;
  }
}
