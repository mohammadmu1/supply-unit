import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import { RouterLink } from "@angular/router";
import { AddUserService } from "../service/add-user.service";
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    FormsModule,
    NgClass,
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signUpRequest = {
    name: '',
    username: '',
    password: '',
    role: ''
  };
  errorMessage: string = '';
  successMessage: string = '';  // Add successMessage to display success feedback

  roles = ['MANAGER', 'EMPLOYEE'];

  constructor(
    private addUserService: AddUserService,
    private authService:AuthenticationService
  ) {}

  signUp() {
    const token = localStorage.getItem('jwtToken');

    this.addUserService.addUser(
      this.signUpRequest.username,
      this.signUpRequest.password,
      this.signUpRequest.name,
      this.signUpRequest.role
    ).subscribe(
       (response) => {
        console.log('User added successfully:', response);
        this.successMessage = 'User added successfully';
        this.errorMessage = '';
      },
       (error) => {
        console.log('Error adding user:', error);
        this.errorMessage = 'Unable to add user due to insufficient permissions';
        this.successMessage = '';
      }
    );
  }


}
