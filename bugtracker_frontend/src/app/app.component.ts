import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'bugtrackerapp';
  isLoggedIn: boolean = false;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.authService.subscribeToLogin((newState: boolean): void => {
      this.isLoggedIn = newState;
    });
    this.authService.loggedIn().subscribe();
  }
}
