import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

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
  NbListModule,
  NbUserModule,
  NbContextMenuModule,
  NbMenuModule,
  NbTabsetModule,
  NbToastrModule,
  NbSpinnerModule,
  NbDialogModule,
  NbTimepickerModule
} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NbPasswordAuthStrategy, NbAuthModule } from '@nebular/auth';
import {environment} from "../environments/environment";
import { AboutUsComponent } from './component/about-us/about-us.component';
import { HomeComponent } from './component/home/home.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { HeaderComponent } from './component/header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NbAuthJWTToken } from '@nebular/auth';
import { UserAccountComponent } from './component/user-account/user-account.component';
import { ProfileComponent } from './component/profile/profile.component';
import { AddressComponent } from './component/address/address.component';
import { PaymentComponent } from './component/payment/payment.component';
import {FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { MyPropertiesComponent } from './component/my-properties/my-properties.component';
import { ActiveAuctionsComponent } from './component/active-auctions/active-auctions.component';
import { CompletedAuctionsComponent } from './component/completed-auctions/completed-auctions.component';
import { InterestedPropertiesComponent } from './component/interested-properties/interested-properties.component';
import { MessagesComponent } from './component/messages/messages.component';
import {AgGridModule} from "ag-grid-angular";
import { MyPropertyCardComponent } from './component/my-property-card/my-property-card.component';
import { CardItemComponent } from './component/card-item/card-item.component';
import { AuctionCardComponent } from './component/auction-card/auction-card.component';
import { InterestedPropertyCardComponent } from './component/interested-property-card/interested-property-card.component';
import { MessageCardComponent } from './component/message-card/message-card.component';
import { SearchBarComponent } from './component/search-bar/search-bar.component';
import { NewPropertyComponent } from './component/new-property/new-property.component';
import {AuthInterceptor} from "./service/auth-interceptor";
import {MatTableModule} from "@angular/material/table";
import { PropertyListComponent } from './component/property-list/property-list.component';
import { AllPropertyListComponent} from './component/all-property-list/all-property-list.component';
import { ConfirmationDialogComponent } from './component/confirmation-dialog/confirmation-dialog.component';
import { SellPropertyComponent } from './component/sell-property/sell-property.component';
import { JoinBidComponent } from './component/join-bid/join-bid.component';
import { PlaceBidComponent } from './component/place-bid/place-bid.component';
import { PropertyDetailComponent } from './component/property-detail/property-detail.component';
import { UploadComponent } from './component/upload/upload.component';
import { PropertyCardComponent } from './component/property-card/property-card.component';

import { AgmCoreModule } from '@agm/core';
import { EmailFormComponent } from './component/email-form/email-form.component';
import { BidHistoryComponent } from './component/bid-history/bid-history.component';
import { DatePipe } from '@angular/common';
import { MessagesCardComponent } from './component/messages-card/messages-card.component';
import { FooterComponent } from './component/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    AboutUsComponent,
    HomeComponent,
    ContactUsComponent,
    HeaderComponent,
    UserAccountComponent,
    ProfileComponent,
    AddressComponent,
    PaymentComponent,
    DashboardComponent,
    MyPropertiesComponent,
    ActiveAuctionsComponent,
    CompletedAuctionsComponent,
    InterestedPropertiesComponent,
    MessagesComponent,
    MyPropertyCardComponent,
    CardItemComponent,
    AuctionCardComponent,
    InterestedPropertyCardComponent,
    MessageCardComponent,
    SearchBarComponent,
    NewPropertyComponent,
    PropertyListComponent,
    AllPropertyListComponent,
    ConfirmationDialogComponent,
    SellPropertyComponent,
    JoinBidComponent,
    PlaceBidComponent,
    PropertyDetailComponent,
    UploadComponent,
    PropertyCardComponent,
    EmailFormComponent,
    BidHistoryComponent,
    MessagesCardComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NbThemeModule.forRoot({name: 'default'}),
    NbDatepickerModule.forRoot(),
    NbTimepickerModule.forRoot(),
    NbLayoutModule,
    NbInputModule,
    NbEvaIconsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyACpbsx-Xm4re7zghBNqaZ1zoTfLCeD1i8'
    }),
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
            redirect: {
              success: '/dashboard',
              failure: null,
            },
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
    ReactiveFormsModule,
    NbSpinnerModule,
    AgGridModule.withComponents([]),
    MatTableModule,
    NbListModule,
    NbDialogModule.forRoot(),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    DatePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
