import { Injectable } from '@angular/core';
import {NbAuthJWTToken, NbAuthService} from "@nebular/auth";
import {User} from "../model/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  user: User = new User();
  authenticated: boolean = false;
  token: string = null;

  constructor(private authService: NbAuthService) {
    this.authService.onTokenChange()
      .subscribe((token: NbAuthJWTToken) => {
        if (token.isValid()) {
          let payload = token.getPayload();
          this.user.email = payload.email;
          this.user.fullName = payload.sub;
          this.token = token.getValue();
        }
      });

    this.authService.onAuthenticationChange()
      .subscribe((isAuthenticated: boolean) => {
          this.authenticated = isAuthenticated;
          if(!isAuthenticated){
            this.token = '';
          }
      });
  }

}
