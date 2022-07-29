import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { DiveBookComponent } from './components/dive-book-components/dive-book/dive-book.component';
import { PassportComponent } from './components/passport-components/passport/passport.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DiveCardComponent } from './components/dive-book-components/dive-card/dive-card.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { RegisterComponent } from './components/initial-page-components/register/register.component';
import { LoginComponent } from './components/initial-page-components/login/login.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { HomeComponent } from './components/initial-page-components/home/home.component';
import { UserPageComponent } from './components/user-components/user-page/user-page.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDialogModule } from '@angular/material/dialog';
import { DiveInfoDialogComponent } from './components/dive-book-components/dive-info-dialog/dive-info-dialog.component';
import { TitulationFormDialogComponent } from './components/passport-components/titulation-form-dialog/titulation-form-dialog.component';
import { DiveFormDialogComponent } from './components/dive-book-components/dive-form-dialog/dive-form-dialog.component';
import { MatRadioModule } from '@angular/material/radio';
import { MatIconModule } from '@angular/material/icon';
import { TitulationCardComponent } from './components/passport-components/titulation-card/titulation-card.component';
import { ModifyUserDialogComponent } from './components/user-components/modify-user-dialog/modify-user-dialog.component';
import { DeleteUserDialogComponent } from './components/user-components/delete-user-dialog/delete-user-dialog.component';
import { ClubsPageComponent } from './components/club-components/clubs-page/clubs-page.component';
import { ClubCardComponent } from './components/club-components/club-card/club-card.component';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    DiveBookComponent,
    PassportComponent,
    DiveCardComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    TitulationCardComponent,
    UserPageComponent,
    DiveInfoDialogComponent,
    TitulationFormDialogComponent,
    ModifyUserDialogComponent,
    DeleteUserDialogComponent,
    DiveFormDialogComponent,
    ClubsPageComponent,
    ClubCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatRadioModule,
    MatIconModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
