import { Component, OnInit } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";
import DataSourceService from "src/app/services/datasource.service";
import WebSocketService from "src/app/services/websocket.service";
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
    dataType: string = "VIRTUAL"

    pointValue?: number
    pointValues?: any[]


    constructor(
        private dsService: DataSourceService,
        private authService: AuthenticationService,
        private wsService: WebSocketService
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
        this.wsService.subscribe(`/topic/values/${this.dataType}/${this.dsId}/${this.dpId}`, (data: any) => {
            this.pointValue = data.body
        })
        this.dsService.getValues(this.dsId, this.dpId, this.dataType).then((r) => {
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