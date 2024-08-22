import { Component } from '@angular/core';
import { LoginRequest } from '../models/login-request.model';
import { AuthService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [AuthService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  request: LoginRequest = new LoginRequest('', '');
  loginSuccess = false;

  constructor(private authService: AuthService) { }

  onLogin() {
    this.loginSuccess = this.authService.doLogin(this.request);
  }
}
