import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OnlyLoddedUserGuard } from './auth/access.guard';
import DataSourceComponent from './pages/datasources/datasource.component';
import HomeComponent from './pages/home/home.component';
import LoginComponent from './pages/login/login.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [OnlyLoddedUserGuard] },
  { path: 'datasources', component: DataSourceComponent, canActivate: [OnlyLoddedUserGuard] },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
