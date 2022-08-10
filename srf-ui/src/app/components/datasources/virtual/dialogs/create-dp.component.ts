import { Component } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { VirtualDataPoint, VirtualDataSource } from "src/app/pages/datasources/virtual";

@Component({
    selector: 'dialog-create-dp-virtual',
    templateUrl: './create-dp.dialog.html',
})
export default class CreateDataPointVirtualDialog {

    datapoint: VirtualDataPoint

    constructor(
        public dialogRef: MatDialogRef<CreateDataPointVirtualDialog>,
    ) {
        this.datapoint = new VirtualDataPoint("DP_01", 0 , 20)
    }

    public save() {
        this.dialogRef.close(this.datapoint)
    }
    
    public onNoClick() {
        this.dialogRef.close();
    }
}