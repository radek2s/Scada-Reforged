import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from "rxjs";
import { VirtualDataSource } from "../pages/datasources/virtual";
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
        return await this.http.get<VirtualDataSource[]>(`${this.dataSourceApi}`, { headers: this.authService.getHeaders() }).toPromise()
    }

    public async addDP(dsId: number, type: string, datapoint: any) {
        return await this.http.post<any>(`${this.dataSourceApi}/${dsId}?t=${type}`, datapoint, { headers: this.authService.getHeaders() }).toPromise()
    }

    public async initDS(dsId: number, type: string) {
        return await this.http.get(`${this.dataSourceApi}/${dsId}/init?t=${type}`, { headers: this.authService.getHeaders() }).toPromise()
    }

    public async getValues(dsId: number, dpId: number) {
        return await this.http.get(`/api/v1/pv?dsId=${dsId}&dpId=${dpId}`, { headers: this.authService.getHeaders() }).toPromise()
    }

    public async initRT(dsId: number) {
        return await this.http.get(`${this.dataSourceApi}/${dsId}/inite`, { headers: this.authService.getHeaders() }).toPromise()
    }
}