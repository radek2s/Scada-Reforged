import {Stomp} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
// import {WebSocketSubjet} from 'rxjs/webSocket'
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: "root"
})
export default class WebSocketService {

    private ws: any;
    private connected: boolean;

    constructor() {
        this.connected = false
        if(!this.ws || !this.connected) {
            this.connect();
        }
    }

    private connect() {
        let wsAddress = 'http://localhost:8080/srf-ws';
        
        let socket = new SockJS(wsAddress);
        this.ws = Stomp.over(socket);
        this.ws.connect({}, 
            () => { this.connected = true; },
            () => { this.connected = false; }
        );
    }

    public disconnect() {
        if(!!this.ws) {
            this.ws.disconnect();
            this.ws = null;
            this.connected = false;
        }
    }


    public subscribe(topic: string, callback: any):any {
        if(this.connected) {
            return this.ws.subscribe(topic, callback);
        }
    }

    public send(topic: string, message: any) {
        if(this.connected) {
            this.ws.send(topic, {}, JSON.stringify(message));
        }
    }

    public isConnected(): boolean {
        return this.connected;
    }

}