import { Component, OnInit } from "@angular/core";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataSource } from "./virtual";

@Component({
    selector: 'home-component',
    templateUrl: './home.component.html'
})
export default class HomeComponent implements OnInit {

    constructor(
        private dsService: DataSourceService
    ) { }

    ngOnInit(): void {
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