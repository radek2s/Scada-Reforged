import { Component } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import AuthenticationService from "src/app/auth/auth.service";
import CreateDataPointVirtualDialog from "src/app/components/datasources/virtual/dialogs/create-dp.component";
import CreateDataSourceVirtualDialog from "src/app/components/datasources/virtual/dialogs/create-ds.component";
import DataSourceService from "src/app/services/datasource.service";
import { VirtualDataSource } from "../datasources/virtual";

@Component({
    selector: 'ds-component',
    templateUrl: './datasource.component.html'
})
export default class DataSourceComponent {

    dsList: VirtualDataSource[] = []

    constructor(
        private dsService: DataSourceService,
        private authService: AuthenticationService,
        private dialog: MatDialog
    ) { 
        this.loadDS()
    }

    loadDS() {
        this.dsService.getAllDataSources().then(ds => {
            this.dsList = ds || []
        })
    }

    openCreateDataSourceDialog() {
        const dialogRef = this.dialog.open(CreateDataSourceVirtualDialog);
        dialogRef.afterClosed().subscribe(result => {
            if(result) {
                this.dsService.createDataSource(result).then(() => {
                    this.loadDS()
                })
            }
        })
    }

    openCreateDataPointDialog(ds: VirtualDataSource) {
        const dialogRef = this.dialog.open(CreateDataPointVirtualDialog);
        dialogRef.afterClosed().subscribe(result => {
            if(result) {
                this.dsService.addDP(ds.id, "VIRTUAL", result).then(() => {
                    this.loadDS()
                })
            }
        })
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