import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css'],
})
export class NavigationBarComponent implements OnInit {
  logOutBtn: boolean = false;
  userIsAdmin: boolean = false;
  currentUser!: User | null;

  constructor(
    private authService: AuthenticationService,
    public loaderService: LoaderService
  ) {
    this.logOutBtn = sessionStorage.getItem('token') != null;
  }
  ngOnInit(): void {
    this.authService.subscribeToisAdmin((newState: boolean): void => {
      this.userIsAdmin = newState;
    });
    this.authService.isAdmin().subscribe();
    this.authService
      .getCurrentUser()
      .subscribe((res) => (this.currentUser = res.body));
  }

  logout() {
    this.authService.logout().subscribe();
  }
}
