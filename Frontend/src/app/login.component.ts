import { Component, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  template: `
  <div class="auth-page">
    <div class="card">
      <h2 class="title">Sign in</h2>
      <p class="subtitle">Don't have an account? <a routerLink="/register" class="muted-link">Sign up</a></p>

      <form [formGroup]="form" (ngSubmit)="submit()" novalidate>
        <div class="field">
          <label>Mail</label>
          <input class="input" type="email" placeholder="Email@onecta.com" formControlName="email" />
          <div class="error" *ngIf="submitted && form.controls.email.invalid">Valid email is required</div>
        </div>

        <div class="field pw">
          <label>Password</label>
          <input class="input" [type]="showPassword() ? 'text' : 'password'" placeholder="********" formControlName="password" />
          <button type="button" class="pw-toggle" (click)="togglePassword()" aria-label="Toggle password">
            {{ showPassword() ? '🙈' : '👁️' }}
          </button>
          <div class="error" *ngIf="submitted && form.controls.password.invalid">Password is required</div>
        </div>

        <button class="btn" type="submit">Sign in</button>
      </form>
    </div>
  </div>
  `
})
export class LoginComponent {
  submitted = false;
  private show = signal(false);

  constructor(private fb: FormBuilder, private router: Router) {}

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  showPassword = this.show.asReadonly();
  togglePassword() { this.show.update(v => !v); }

  submit() {
    this.submitted = true;
    if (this.form.invalid) return;
    // Demo-only: pretend login succeeded and navigate
    this.router.navigateByUrl('/');
  }
}


