import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { DiveBookComponent } from './components/dive-book-components/dive-book/dive-book.component';
import { HomeComponent } from './components/initial-page-components/home/home.component';
import { LoginComponent } from './components/initial-page-components/login/login.component';
import { PassportComponent } from './components/passport-components/passport/passport.component';
import { RegisterComponent } from './components/initial-page-components/register/register.component';
import { UserPageComponent } from './components/user-components/user-page/user-page.component';
import { AuthGuardService } from './services/auth-guard.service';
import { ClubsPageComponent } from './components/club-components/clubs-page/clubs-page.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuardService],
    component: HomeComponent
  },
  {
    path: 'user',
    canActivate: [AuthGuardService],
    component:UserPageComponent,
  },
  {
    path: 'dive-book',
    canActivate: [AuthGuardService],
    component:DiveBookComponent,
  },
  {
    path: 'passport',
    canActivate: [AuthGuardService],
    component: PassportComponent,
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'clubs',
    component: ClubsPageComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, FormsModule]
})
export class AppRoutingModule { }
