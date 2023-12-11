import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { RegistrationComponent } from './components/registration/registration.component'

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { MainPageComponent } from './components/main-page/main-page.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { AdminToursComponent } from './components/admin/admin-tours/admin-tours.component';
import { AdminOrdersComponent } from './components/admin/admin-orders/admin-orders.component';

import { AuthInterceptor } from './helpers/auth.interceptor';

import { ProfileUserComponent } from './components/profile-user/profile-user.component';
import { ProfileOrderHistoryComponent } from './components/profile-order-history/profile-order-history.component';
import {NavBarProfileComponent} from "./components/nav-bar-profile/nav-bar-profile.component";
import { NotfoundComponent } from './components/notfound/notfound.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { TourComponent } from './components/tour/tour.component';
import { TourFormComponent } from './components/tour-form/tour-form.component';
import {ProfileEditUserComponent} from "./components/profile-edit-user/profile-edit-user.component";
import { TourMainPageComponent } from './components/tour-main-page/tour-main-page.component';
import { ProfileQuestionaryComponent } from './components/profile-questionary/profile-questionary.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainPageComponent,
    AboutUsComponent,
    HeaderComponent,
    FooterComponent,
    RegistrationComponent,
    AdminDashboardComponent,
    AdminToursComponent,
    AdminOrdersComponent,
    ProfileUserComponent,
    ProfileOrderHistoryComponent,
    NavBarProfileComponent,
    NotfoundComponent,
    TourComponent,
    TourFormComponent,
    ProfileEditUserComponent,
    TourMainPageComponent,
    ProfileQuestionaryComponent,
    PaginationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatSelectModule,
    NgbModule,
    MatInputModule,
    MatButtonModule,

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
