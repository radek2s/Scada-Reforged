import { Component, OnInit } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataPoint, VirtualDataSource } from "../datasources/virtual";

@Component({
    selector: 'ds-component',
    templateUrl: './datasource.component.html'
})
export default class DataSourceComponent {

    ds: VirtualDataSource
    dsList: VirtualDataSource[] = []
    dp: VirtualDataPoint

    username: string = ''
    password: string = ''

    constructor(
        private dsService: DataSourceService,
        private authService: AuthenticationService
    ) { 
        this.ds = new VirtualDataSource("Example")
        this.dp = new VirtualDataPoint("DP_01", 0 , 20)
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
        this.dsService.addDP(ds.id, "VIRTUAL", this.dp)
    }

    enable(ds: VirtualDataSource) {
        const data = this.dsList.find(datasource => datasource.id === ds.id)
        if(data) { data.enabled = true }
        this.dsService.enable(ds.id, "VIRTUAL")
    }

    disable(ds: VirtualDataSource) {
        const data = this.dsList.find(datasource => datasource.id === ds.id)
        if(data) { data.enabled = false }
        this.dsService.disable(ds.id, "VIRTUAL")
    }

    toggleDPState(ds: VirtualDataSource, dp: any) {
        const res = ds.datapoints.find(d => d.id == dp.id)
        if(res) {
            res.enabled = !res.enabled
            this.dsService.setDataPointState(ds.id, "VIRTUAL", dp.id, res.enabled)
        }
    }
}