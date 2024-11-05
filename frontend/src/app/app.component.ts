import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import { AuthenticationService } from './service/authentication.service';
import {HeaderComponent} from "./header/header.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterOutlet
  ],
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Supply Unit';

  constructor(private authService: AuthenticationService, private router: Router) {}

  get isLoginPage(): boolean {
    return this.router.url === '/login';
  }

  logout() {
    this.authService.clearToken();
    window.location.reload();
  }
}
