import { Component, Input } from "@angular/core";

@Component({
    selector: 'scada-page',
    templateUrl: './scada-page.component.html',
    styleUrls: ['./scada-page.component.scss']
})
export default class ScadaPageComponent {
    
    @Input() title: String = ''
    
}