import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import AuthenticationService from "./auth.service";

export interface ComponentCanDeactivate {
    canDeactivate: () => Promise<boolean> | any
}

@Injectable({
    providedIn: "root"
})
export class OnlyLoddedUserGuard implements CanActivate {

    constructor(
        private authService: AuthenticationService,
        private router: Router
    ) { }
    
    canActivate() {
        if(!this.authService.isLogged()) {
            this.router.navigateByUrl("/login")
            return false
        } else {
            return true
        }
    }
    
}