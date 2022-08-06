import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from "rxjs";
import { VirtualDataSource } from "../pages/home/virtual";
import AuthenticationService from "../auth/auth.service";

@Injectable({
    providedIn: "root"
})
export default class DataSourceService {

    private dataSourceApi = "/api/v1/ds"

    constructor(
        private http: HttpClient, 
        private authService: AuthenticationService
    ) { }

    public async createDataSource(ds: VirtualDataSource) {
        return await firstValueFrom(this.http.post<any>(`${this.dataSourceApi}`, ds, { headers: this.authService.getHeaders() }))
    }

    public async getAllDataSources() {
        return await this.http.get<any>(`${this.dataSourceApi}`, { headers: this.authService.getHeaders() }).toPromise()
    }
}