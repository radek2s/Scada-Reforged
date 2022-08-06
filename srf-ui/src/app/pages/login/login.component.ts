import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import AuthenticationService from "src/app/auth/auth.service";

@Component({
    selector: 'login-component',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export default class LoginComponent {

    username: string = ''
    password: string = ''

    constructor(
        private authService: AuthenticationService,
        private router: Router
    ) { }

    login() {
        this.authService.login(this.username, this.password).then(() => {
            this.router.navigateByUrl("/home")
        })

    }
}