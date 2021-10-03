import {Component} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthService} from '../_helpers/auth.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {

    public username = '';
    public password = '';
    public status = '';
    public loading = true;

    constructor(private router: Router, private http: HttpClient, private authService: AuthService) {
    }

    login(loginForm: NgForm): void {
        this.authService.login(this.username, this.password)
            .subscribe(data => {
                localStorage.setItem('token', data.headers.get('Authorization'));
                localStorage.setItem('loggedIn', 'true');
            }, error => console.log(error), () => {
                this.loading = false;
                this.router.navigate(['/']);
            });
    }

}
