import { Component, OnInit } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataSource } from "../datasources/virtual";

@Component({
    selector: 'home-component',
    templateUrl: './home.component.html'
})
export default class HomeComponent implements OnInit {

    username: string = ''
    password: string = ''

    dsId: number = 8
    dpId: number = 1

    pointValues?: any[]


    constructor(
        private dsService: DataSourceService,
        private authService: AuthenticationService
    ) { }

    ngOnInit(): void {
        this.loadDs()
    }

    login() {
        this.authService.login(this.username, this.password)
    }

    loadDs(): void {
        this.dsService.getAllDataSources().then((ds) => {
            console.log(ds)
        })
    }

    getValues() {
        this.dsService.getValues(this.dsId, this.dpId).then((r) => {
            if(r) {
                this.pointValues = (r as any[]).map(i => { return {time: new Date(i.time).getTime(), value: i.value }})
            }
            console.log(r)
        })

    }



    createDs(): void {
        const ds = new VirtualDataSource("Example")
        this.dsService.createDataSource(ds).then((r) => {
            console.log(r)
        })
    }
}