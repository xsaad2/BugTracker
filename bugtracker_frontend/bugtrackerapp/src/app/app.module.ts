import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDividerModule } from '@angular/material/divider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';

import { TimeAgoPipe } from './pipes/TimeAgoPipe';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { UserTableComponent } from './components/user-table/user-table.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';

import { RegistrationComponent } from './components/registration/registration.component';
import { ProjectsTableComponent } from './components/projects-table/projects-table.component';
import { ProjectCreationFormComponent } from './components/project-creation-form/project-creation-form.component';
import { TicketsPageComponent } from './pages/tickets-page/tickets-page.component';
import { TicketsTableComponent } from './components/tickets-table/tickets-table.component';
import { DevAssignmentComponent } from './components/dev-assignment/dev-assignment.component';
import { TicketCreationFormComponent } from './components/ticket-creation-form/ticket-creation-form.component';
import { ProjectCardComponent } from './components/project-card/project-card.component';
import { TicketCardComponent } from './components/ticket-card/ticket-card.component';
import { AddDevToTicketComponent } from './components/add-dev-to-ticket/add-dev-to-ticket.component';
import { CommentsComponent } from './components/comments/comments.component';
import { UserUpdateFormComponent } from './components/user-update-form/user-update-form.component';
import { RequestInterceptor } from './Interceptors/request.interceptor';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MyTicketsComponent } from './components/my-tickets/my-tickets.component';
import { MyTicketsPageComponent } from './pages/my-tickets-page/my-tickets-page.component';
import { TicketsStatusChartComponent } from './components/tickets-status-chart/tickets-status-chart.component';
import { DateTimeFormatPipe } from './pipes/date-time-format.pipe';
import { DemoLoginComponent } from './components/demo-login/demo-login.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    UserTableComponent,
    LoginComponent,
    RegistrationComponent,
    ProjectsTableComponent,
    ProjectCreationFormComponent,
    TicketsPageComponent,
    TicketsTableComponent,
    DevAssignmentComponent,
    TicketCreationFormComponent,
    ProjectCardComponent,
    TicketCardComponent,
    AddDevToTicketComponent,
    CommentsComponent,
    UserUpdateFormComponent,
    TimeAgoPipe,
    DashboardComponent,
    MyTicketsComponent,
    MyTicketsPageComponent,
    TicketsStatusChartComponent,
    DateTimeFormatPipe,
    DemoLoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    HttpClientModule,
    FormsModule,
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    MatDividerModule,
    MatSidenavModule,
    MatPaginatorModule,
    MatCardModule,
    MatSelectModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatChipsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
