import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import {User} from "../../model/user.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  user: User = new User();

  constructor(private http: HttpClient, private userService: UserService) { }

  ngOnInit(): void {
    this.getProfile();
  }

  getProfile() {
    const email = this.userService.user?.email;
    if(email) {
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
            this.userService.currentUser = u;
          }
        );
    }
  }
}
