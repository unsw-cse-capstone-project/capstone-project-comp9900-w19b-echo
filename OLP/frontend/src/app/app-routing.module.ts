import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  NbAuthComponent,
  NbLoginComponent,
  NbRegisterComponent,
  NbLogoutComponent,
  NbRequestPasswordComponent,
  NbResetPasswordComponent,
} from '@nebular/auth';
import {AboutUsComponent} from "./component/about-us/about-us.component";
import {HomeComponent} from "./component/home/home.component";
import {BuyerInfoComponent} from "./component/buyer-info/buyer-info.component";
import {SellerInfoComponent} from "./component/seller-info/seller-info.component";
import {ContactUsComponent} from "./component/contact-us/contact-us.component";
import {UserAccountComponent} from "./component/user-account/user-account.component";
import {DASH} from "@angular/cdk/keycodes";
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {MyPropertiesComponent} from "./component/my-properties/my-properties.component";
import {ActiveAuctionsComponent} from "./component/active-auctions/active-auctions.component";
import {CompletedAuctionsComponent} from "./component/completed-auctions/completed-auctions.component";
import {InterestedPropertiesComponent} from "./component/interested-properties/interested-properties.component";
import {MessagesComponent} from "./component/messages/messages.component";
import {NewPropertyComponent} from "./component/new-property/new-property.component";
import {SellPropertyComponent} from "./component/sell-property/sell-property.component";

const routes: Routes = [
  {
    path: 'auth',
    component: NbAuthComponent,
    children: [
      {
        path: '',
        component: NbLoginComponent,
      },
      {
        path: 'login',
        component: NbLoginComponent,
      },
      {
        path: 'register',
        component: NbRegisterComponent,
      },
      {
        path: 'logout',
        component: NbLogoutComponent,
      },
      {
        path: 'request-password',
        component: NbRequestPasswordComponent,
      },
      {
        path: 'reset-password',
        component: NbResetPasswordComponent,
      },
    ],
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'about-us',
    component: AboutUsComponent,
  },
  {
    path: 'buyer-info',
    component: BuyerInfoComponent,
  },
  {
    path: 'seller-info',
    component: SellerInfoComponent,
  },
  {
    path: 'contact-us',
    component: ContactUsComponent,
  },
  {
    path: 'user-account',
    component: UserAccountComponent,
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'my-properties',
    component: MyPropertiesComponent,
  },
  {
    path: 'active-auctions',
    component: ActiveAuctionsComponent,
  },
  {
    path: 'completed-auctions',
    component: CompletedAuctionsComponent,
  },
  {
    path: 'interested-properties',
    component: InterestedPropertiesComponent,
  },
  {
    path: 'messages',
    component: MessagesComponent,
  },
  {
    path: 'new-property',
    component: NewPropertyComponent,
  },
  {
    path: 'sell-property',
    component: SellPropertyComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
