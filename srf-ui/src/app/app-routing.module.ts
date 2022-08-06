import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OnlyLoddedUserGuard } from './auth/access.guard';
import HomeComponent from './pages/home/home.component';
import LoginComponent from './pages/login/login.component';

const routes: Routes = [
  { path: 'datasources', component: HomeComponent, canActivate: [OnlyLoddedUserGuard] },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
