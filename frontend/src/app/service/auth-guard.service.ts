import {Injectable} from '@angular/core';
import {CanActivate, Router, ActivatedRouteSnapshot} from '@angular/router';
import {AuthenticationService} from './authentication.service';

interface RouteData {
  role?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (this.authService.isTokenExpired()) {
      this.router.navigate(['/login']);
      return false;
    }

    const expectedRole = (route.data as RouteData).role;
    const userRole = this.authService.getUserRole();

    if (this.authService.isAuthenticated() && (!expectedRole || userRole === expectedRole)) {
      return true;
    } else {
      if (userRole === 'ADMIN') {
        this.router.navigate(['/addUser']);
      } else if (userRole === 'MANAGER') {
        this.router.navigate(['/warehouse']);
      } else if (userRole === 'EMPLOYEE') {
        this.router.navigate(['/supplyDocument']);
      } else {
        this.router.navigate(['/login']);
      }

      return false;
    }
  }
}
