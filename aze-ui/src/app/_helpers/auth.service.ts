import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable()
export class AuthService {
    constructor(private router: Router, private http: HttpClient) {
    }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        const isLoggedIn = localStorage.getItem('loggedIn');
        const valid = token === null || isLoggedIn === null;

        return !valid;
    }

    login(username: string, password: string): Observable<any> {
        const user = {username, password};
        return this.http.post<any>(environment.api + '/login', user, {observe: 'response'});
    }

    logout(): void {
        localStorage.removeItem('token');
        localStorage.removeItem('loggedIn');
        this.router.navigate(['/login']);
    }
}
