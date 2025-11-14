import { Component, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  template: `
  <div class="auth-page">
    <div class="card">
      <h2 class="title">Registration</h2>
      <p class="subtitle">Already have an account? <a routerLink="/login" class="muted-link">Log in</a></p>

      <form [formGroup]="form" (ngSubmit)="submit()" novalidate>
        <div class="field">
          <label>Full Name</label>
          <input class="input" type="text" placeholder="Full Name..." formControlName="fullName" />
          <div class="error" *ngIf="submitted && form.controls.fullName.invalid">Name is required</div>
        </div>

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
          <div class="error" *ngIf="submitted && form.controls.password.invalid">Min 6 characters</div>
        </div>

        <div class="field pw">
          <label>Repeat password</label>
          <input class="input" [type]="showConfirm() ? 'text' : 'password'" placeholder="********" formControlName="confirm" />
          <button type="button" class="pw-toggle" (click)="toggleConfirm()" aria-label="Toggle password">
            {{ showConfirm() ? '🙈' : '👁️' }}
          </button>
          <div class="error" *ngIf="submitted && passwordsMismatch">Passwords do not match</div>
        </div>

        <label class="row">
          <input type="checkbox" formControlName="agree" />
          <span>I agree to the Terms and Privacy Policy</span>
        </label>
        <div class="error" *ngIf="submitted && form.controls.agree.invalid">You must agree to continue</div>

        <button class="btn" type="submit">Register</button>
      </form>
    </div>
  </div>
  `
})
export class RegisterComponent {
  submitted = false;
  private showPw = signal(false);
  private showC = signal(false);

  constructor(private fb: FormBuilder, private router: Router) {}

  form = this.fb.group({
    fullName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirm: ['', [Validators.required, Validators.minLength(6)]],
    agree: [false, Validators.requiredTrue]
  });

  showPassword = this.showPw.asReadonly();
  showConfirm = this.showC.asReadonly();
  togglePassword() { this.showPw.update(v => !v); }
  toggleConfirm() { this.showC.update(v => !v); }

  get passwordsMismatch(): boolean {
    const p = this.form.controls.password.value;
    const c = this.form.controls.confirm.value;
    return !!p && !!c && p !== c;
  }

  submit() {
    this.submitted = true;
    if (this.form.invalid || this.passwordsMismatch) return;
    // Demo-only: navigate to login after a successful client-side validation
    this.router.navigateByUrl('/login');
  }
}


