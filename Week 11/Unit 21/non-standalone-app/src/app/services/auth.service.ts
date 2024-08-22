import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/login-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private mockUser = new LoginRequest('username', 'password');

  constructor() { }

  doLogin(request: LoginRequest): boolean {
    return request.username == this.mockUser.username && request.password == this.mockUser.password;
  }
}
