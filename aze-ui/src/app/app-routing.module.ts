import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ShiftViewComponent} from './shift-view/shift-view.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './_helpers/auth-guard.service';

export const routes: Routes = [
    {path: '', component: ShiftViewComponent, canActivate: [AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: '**', redirectTo: ''}
];


@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
