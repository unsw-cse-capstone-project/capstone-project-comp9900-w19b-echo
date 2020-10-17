import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  NbThemeModule,
  NbLayoutModule,
  NbActionsModule,
  NbIconModule,
  NbCardModule,
  NbInputModule,
  NbTooltipModule,
  NbDatepickerModule,
  NbSelectModule,
  NbButtonModule,
  NbSearchModule,
  NbUserModule, NbContextMenuModule, NbMenuModule, NbTabsetModule, NbToastrModule, NbSpinnerModule
} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NbPasswordAuthStrategy, NbAuthModule } from '@nebular/auth';
import {environment} from "../environments/environment";
import { AboutUsComponent } from './component/about-us/about-us.component';
import { HomeComponent } from './component/home/home.component';
import { BuyerInfoComponent } from './component/buyer-info/buyer-info.component';
import { SellerInfoComponent } from './component/seller-info/seller-info.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { HeaderComponent } from './component/header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NbAuthJWTToken } from '@nebular/auth';
import { UserAccountComponent } from './component/user-account/user-account.component';
import { ProfileComponent } from './component/profile/profile.component';
import { AddressComponent } from './component/address/address.component';
import { PaymentComponent } from './component/payment/payment.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    AboutUsComponent,
    HomeComponent,
    BuyerInfoComponent,
    SellerInfoComponent,
    ContactUsComponent,
    HeaderComponent,
    UserAccountComponent,
    ProfileComponent,
    AddressComponent,
    PaymentComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        NbThemeModule.forRoot({name: 'default'}),
        NbDatepickerModule.forRoot(),
        NbLayoutModule,
        NbInputModule,
        NbEvaIconsModule,
        NbAuthModule.forRoot({
            strategies: [
                NbPasswordAuthStrategy.setup({
                    name: 'email',
                    token: {
                        class: NbAuthJWTToken,
                        key: 'token',
                    },
                    baseEndpoint: environment.baseEndpoint,
                    login: {
                        endpoint: '/sign-in',
                        method: 'post',
                    },
                    register: {
                        endpoint: '/sign-up',
                        method: 'post',
                        defaultErrors: ['Something went wrong, please try again.'],
                    },
                    logout: {
                        endpoint: '/sign-out',
                        method: 'post',
                    },
                    requestPass: {
                        endpoint: '/request-pass',
                        method: 'post',
                    },
                    resetPass: {
                        endpoint: '/reset-pass',
                        method: 'post',
                    },
                }),
            ],
            forms: {},
        }),
        NbActionsModule,
        NbIconModule,
        NbTooltipModule,
        NbInputModule,
        NbCardModule,
        NgbModule,
        NbSelectModule,
        NbButtonModule,
        NbSearchModule,
        NbUserModule,
        NbContextMenuModule,
        NbMenuModule.forRoot(),
        NbToastrModule.forRoot(),
        NbTabsetModule,
        FormsModule,
        NbSpinnerModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
