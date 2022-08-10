import { Component } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { VirtualDataSource } from "src/app/pages/datasources/virtual";

@Component({
    selector: 'dialog-create-ds-virtual',
    templateUrl: './create-ds.dialog.html',
})
export default class CreateDataSourceVirtualDialog {

    datasource: VirtualDataSource

    constructor(
        public dialogRef: MatDialogRef<CreateDataSourceVirtualDialog>,
    ) {
        this.datasource = new VirtualDataSource("")
    }

    public save() {
        this.dialogRef.close(this.datasource)
    }
    
    public onNoClick() {
        this.dialogRef.close();
    }
}