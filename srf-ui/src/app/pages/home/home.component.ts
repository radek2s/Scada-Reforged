import { Component, OnInit } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataSource } from "./virtual";

@Component({
    selector: 'home-component',
    templateUrl: './home.component.html'
})
export default class HomeComponent implements OnInit {

    username: string = ''
    password: string = ''

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
        this.dsService.getAllDataSources().then(() => {
            console.log("Requested")
        })
    }

    createDs(): void {
        const ds = new VirtualDataSource("Example")
        this.dsService.createDataSource(ds).then((r) => {
            console.log(r)
        })
    }
}