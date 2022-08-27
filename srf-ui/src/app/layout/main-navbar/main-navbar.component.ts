import { Component, Input } from "@angular/core";
import AuthenticationService from "src/app/auth/auth.service";

@Component({
    selector: 'main-navbar',
    templateUrl: './main-navbar.component.html',
    styleUrls: ['./main-navbar.component.scss']
    
})
export default class MainNavbarComponent {

    constructor(
        private authService: AuthenticationService
    ) { }
    
    @Input() isExpanded: Boolean = false

    logout() {
        this.authService.logout()   
    }

    isLogged() {
        return this.authService.isLogged()
    }
    
}