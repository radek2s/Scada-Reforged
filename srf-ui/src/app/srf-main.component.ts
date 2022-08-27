import { Component } from "@angular/core";

@Component({
    selector: 'srf-main',
    templateUrl: './srf-main.component.html',
    styleUrls: ['./srf-main.component.scss']
    
})
export default class SrfMainComponent {

    isExpanded = false

    onMouseEnter() {
        this.isExpanded = true
    }

    onMouseLeave() {
        this.isExpanded = false
    }

    
}