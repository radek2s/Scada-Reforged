import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import jwtDecode, { JwtPayload } from "jwt-decode";

@Injectable({
    providedIn: "root"
})
export default class AuthenticationService {

    private securityApi = "/api/v1"
    private accessToken?: string
    private loggedUser?: string
    private secretKey: string = '1234'

    constructor(
        private http: HttpClient,
    ) { }

    public isLogged(): boolean {
        return !!this.loggedUser
    }

    public async login(username: string, password: string) {        
        this.http.post(`${this.securityApi}/login?username=${username}&password=${password}`, null, { observe: 'response' }).subscribe(resp => {
            this.saveToken(resp.headers.get("authorization") || '')
        })
    }

    public saveToken(token: string) {
        if(token == '') { console.warn("Not logged!")}
        this.accessToken = token

        try {
            this.loggedUser = jwtDecode<JwtPayload>(token).sub
        } catch (e) {
            console.error(e)
        }
    }

    public getHeaders(): HttpHeaders {
        if(this.accessToken) {
            console.log("Logged as: ", this.loggedUser)
            return new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer: ${this.accessToken}`
            })
        }
        return new HttpHeaders({
            'Content-Type': 'application/json'
        }) 
    }
}