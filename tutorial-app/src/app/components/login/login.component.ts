import { Component, OnInit } from '@angular/core';
import { Login } from '../../models/login.model';
import { IdentityService } from '../../services/identity.service';
import { UserStorageService } from '../../services/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public model: Login = new Login();
  public invalid?: boolean;

  constructor(
    private identityService: IdentityService,
    private userStorageService: UserStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onSubmit(): void {
    let self = this;

    this.identityService.signIn(this.model).subscribe({
      next(data) {
        //console.log(data);
        self.userStorageService.set(data);
        self.router.navigate(['/tutorials']);
      },
      error() {
        self.invalid = true;
      },
    });
  }
}
