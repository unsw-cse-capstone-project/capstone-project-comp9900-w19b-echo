import { Component, OnInit } from '@angular/core';
import {User} from "../../Models/user.model";
import {NbAuthJWTToken, NbAuthService} from "@nebular/auth";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User = new User();

  constructor(private authService: NbAuthService, private http: HttpClient) {
    this.authService.onTokenChange()
      .subscribe((token: NbAuthJWTToken) => {
        if (token.isValid()) {
          let payload = token.getPayload();
          this.user.email = payload.sub;
          this.user.fullName = payload.fullName;
          this.user.phone = payload.phoneNumber;
        }
      });
  }

  ngOnInit(): void {
  }

  saveProfile() {
    this.http.post(environment.baseEndpoint + '/user', this.user).subscribe();
  }
}
