import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {NbThemeModule, NbLayoutModule, NbActionsModule, NbIconModule, NbCardModule,NbInputModule, NbTooltipModule,NbDatepickerModule,NbSelectModule} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NbPasswordAuthStrategy, NbAuthModule } from '@nebular/auth';
import {environment} from "../environments/environment";
import { AboutUsComponent } from './about-us/about-us.component';
import { HomeComponent } from './home/home.component';
import { BuyerInfoComponent } from './buyer-info/buyer-info.component';
import { SellerInfoComponent } from './seller-info/seller-info.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { HeaderComponent } from './header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    AboutUsComponent,
    HomeComponent,
    BuyerInfoComponent,
    SellerInfoComponent,
    ContactUsComponent,
    HeaderComponent
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
          baseEndpoint: environment.baseEndpoint,
          login: {
            endpoint: '/auth/sign-in',
            method: 'post',
          },
          register: {
            endpoint: '/auth/sign-up',
            method: 'post',
          },
          logout: {
            endpoint: '/auth/sign-out',
            method: 'post',
          },
          requestPass: {
            endpoint: '/auth/request-pass',
            method: 'post',
          },
          resetPass: {
            endpoint: '/auth/reset-pass',
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
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
