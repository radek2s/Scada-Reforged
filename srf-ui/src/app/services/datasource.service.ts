import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from "rxjs";
import { VirtualDataSource } from "../pages/home/virtual";

@Injectable({
    providedIn: "root"
})
export default class DataSourceService {

    private dataSourceApi = "/api/v1/ds"
    private headers: HttpHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImV4YW1wbGUuaW8ifQ.6Bj0gaYdXcEaauCD8tQ-7ObdzzSiJa8emD4P4H84sCU`
    })

    constructor(private http: HttpClient) { }

    public async createDataSource(ds: VirtualDataSource) {
        return await firstValueFrom(this.http.post<any>(`${this.dataSourceApi}`, ds, { headers: this.headers }))
    }

    public async getAllDataSources() {
        return await this.http.get<any>(`${this.dataSourceApi}`, { headers: this.headers }).toPromise()
    }
}