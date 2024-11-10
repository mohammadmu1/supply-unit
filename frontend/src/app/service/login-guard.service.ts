import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      const userRole = this.authService.getUserRole();

      // Redirect based on the role
      if (userRole === 'ADMIN') {
        this.router.navigate(['/addUser']);
      } else if (userRole === 'MANAGER') {
        this.router.navigate(['/warehouse']);
      } else if (userRole === 'EMPLOYEE') {
        this.router.navigate(['/supplyDocument']);
      }
      return false;
    }
    return true;
  }
}
