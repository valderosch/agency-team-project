import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthUser } from 'src/app/common/auth.model/auth-user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../common/css/form-errors.css', '../../common/css/auth.css', './login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: UntypedFormGroup;
  loginFailed: boolean = false;
  formSubmitted: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: UntypedFormBuilder,
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/^[A-Za-z\d]{5,}$/)]]
    });
  }

  ngOnInit(): void {
  }

  onLogin() {
    this.formSubmitted = true;
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: response => {
          this.storageService.saveToken(response.token);
          this.storageService.saveUser(new AuthUser(response.id, response.role));

          if(response.role === "CUSTOMER") {
            this.router.navigate(['/profile']);
          }
          if(response.role === "ADMIN") {
            this.router.navigate(['/admin-orders']);
          }

        },
        error: err => {
          this.errorMessage = err.error.message;
          this.loginFailed = true;
        }
      });
    }
  }
}
