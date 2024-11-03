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
    const expectedRole = (route.data as RouteData).role;
    const userRole = this.authService.getUserRole();

    if (this.authService.isAuthenticated() && (!expectedRole || userRole === expectedRole)) {
      return true;
    } else {
      console.log(userRole);

      if (userRole == 'ADMIN') {
        console.log('userRole: ADMIN');
        this.router.navigate(['/addUser']);
      } else if (userRole == 'MANAGER') {
        console.log('userRole: MANAGER');
        this.router.navigate(['/warehouse']);
      }else if (userRole == 'EMPLOYEE') {
        console.log('userRole: EMPLOYEE');
        this.router.navigate(['/supplyDocument']);
      } else {
        this.router.navigate(['/login']);
      }

      return false;
    }
  }
}
