import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import HomeComponent from './pages/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import LoginComponent from './pages/login/login.component';
import { MaterialModule } from './material.module';
import AmChartComponent from './components/chart/amchart.component';
import DataSourceComponent from './pages/datasources/datasource.component';
import CreateDataSourceVirtualDialog from './components/datasources/virtual/dialogs/create-ds.component';
import CreateDataPointVirtualDialog from './components/datasources/virtual/dialogs/create-dp.component';
import MainNavbarComponent from './layout/main-navbar/main-navbar.component';
import SrfMainComponent from './srf-main.component';
import ScadaPageComponent from './layout/scada-page/scada-page.component';

@NgModule({
  declarations: [
    SrfMainComponent,
    ScadaPageComponent,
    HomeComponent,
    LoginComponent,
    AmChartComponent,
    DataSourceComponent,
    CreateDataSourceVirtualDialog,
    CreateDataPointVirtualDialog,
    MainNavbarComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [SrfMainComponent]
})
export class AppModule { }
