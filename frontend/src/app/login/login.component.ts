import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router, RouterModule, RouterLink} from '@angular/router';
import {NgClass, NgIf} from '@angular/common';
import {AuthenticationService} from '../service/authentication.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgClass,
    RouterLink,
    RouterModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  login() {
    // client-side validation.
    const usernamePattern = /^(?=.*[A-Z]).{4,}$/;
    const passwordPattern = /^(?=.*[A-Z]).{4,}$/;
    if (!usernamePattern.test(this.username) || !passwordPattern.test(this.password)) {
      this.errorMessage = 'Username and password must be at least 4 characters long and contain at least one uppercase letter';

      setTimeout(() => {
        this.errorMessage = '';
      }, 1750);

      this.username = '';
      this.password = '';
      return;
    }


    this.authService.executeAuthentication(this.username, this.password)
      .subscribe(
         (response) => {
          const token = response.token;
          const role = response.role;

          if (token) {
            localStorage.setItem('jwtToken', token);
            console.log(token)
            localStorage.setItem('role', role);
            localStorage.setItem('userId', response.id);

            if (role === 'ADMIN') {
              this.router.navigate(['/addUser']);
            }
            if (role === 'MANAGER') {
              this.router.navigate(['/warehouse']);
            }
            if (role === 'EMPLOYEE') {
              this.router.navigate(['/supplyDocument']);
            }
          }
        },
         (err) => {
          this.errorMessage = 'Wrong username or password';
          this.username = '';
          this.password = '';

          setTimeout(() => {
            this.errorMessage = '';
          }, 1750);
        },
         () => {
          console.log('Authentication process completed');
        }
      );
  }


}
