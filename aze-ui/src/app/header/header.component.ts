import {Component, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../_helpers/auth.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    encapsulation: ViewEncapsulation.Emulated
})
export class HeaderComponent {
    date:any;

    constructor(private authService: AuthService) {
        setInterval(() => {
            this.date = new Date()
        }, 1000)
    }

    token(): boolean {
        if (localStorage.getItem('token')) {
            return true;
        } else {
            return false;
        }
    }

    logout(): void {
        this.authService.logout();
    }
}
