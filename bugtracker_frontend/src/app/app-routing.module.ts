import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './Interceptors/authentication.guard';
import { LoginComponent } from './components/login/login.component';
import { ProjectCreationFormComponent } from './components/project-creation-form/project-creation-form.component';
import { ProjectsTableComponent } from './components/projects-table/projects-table.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { UserTableComponent } from './components/user-table/user-table.component';
import { TicketsPageComponent } from './pages/tickets-page/tickets-page.component';
import { AdminGuard } from './Interceptors/authorisation.guard';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LoginGuard } from './Interceptors/login.guard';
import { MyTicketsPageComponent } from './pages/my-tickets-page/my-tickets-page.component';
import { DemoLoginComponent } from './components/demo-login/demo-login.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'demo', component: DemoLoginComponent, canActivate: [LoginGuard] },

  {
    path: 'registration',
    component: RegistrationComponent,
    canActivate: [LoginGuard],
  },
  {
    path: 'users',
    component: UserTableComponent,
    canActivate: [AdminGuard],
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'mytickets',
    component: MyTicketsPageComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'project/:id',
    component: TicketsPageComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'form',
    component: ProjectCreationFormComponent,
    canActivate: [AuthenticationGuard],
  },
  { path: '**', redirectTo: 'dashboard' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
