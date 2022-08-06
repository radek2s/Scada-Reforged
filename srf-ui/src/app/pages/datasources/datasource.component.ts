import { Component, OnInit } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataSource } from "../datasources/virtual";

@Component({
    selector: 'ds-component',
    templateUrl: './datasource.component.html'
})
export default class DataSourceComponent {

    ds: VirtualDataSource
    dsList: VirtualDataSource[] = []

    username: string = ''
    password: string = ''

    constructor(
        private dsService: DataSourceService,
        private authService: AuthenticationService
    ) { 
        this.ds = new VirtualDataSource("Example")
        this.loadDS()
    }

    generateNew() {
        this.ds = new VirtualDataSource("New")
    }

    loadDS() {
        this.dsService.getAllDataSources().then(ds => {
            this.dsList = ds || []
        })
    }

    

    createDs(): void {
        this.dsService.createDataSource(this.ds).then((r) => {
            console.log(r)
        })
    }

    createDp(ds: VirtualDataSource) {
        const dp = {
            sid: "DSP",
            name: "DP_0",
            enabled: true,
            settable: true,
            dataType: 0,
            changeType: 0,
            minValue: 0,
            maxValue: 20
        }
        this.dsService.addDP(ds.id, "VIRTUAL", dp)
    }

    enable(ds: VirtualDataSource) {
        this.dsService.initDS(ds.id, "VIRTUAL")
    }

    start(ds: VirtualDataSource) {
        this.dsService.initRT(ds.id)
    }
}