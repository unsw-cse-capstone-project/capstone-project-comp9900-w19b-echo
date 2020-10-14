import { Component, OnInit } from '@angular/core';
import {NbAuthJWTToken, NbAuthService} from "@nebular/auth";
import { NbSearchService } from '@nebular/theme';
import {User} from "../../Models/user.model";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: User = new User();
  items = [{ title: 'Profile' }, { title: 'Log out' }];

  constructor(private authService: NbAuthService) {
    this.authService.onTokenChange()
      .subscribe((token: NbAuthJWTToken) => {
        if (token.isValid()) {
          this.user = token.getPayload(); // here we receive a payload from the token and assigns it to our `user` variable
        }
      });
  }

  ngOnInit(): void {
  }
}
export class SearchEventComponent {

  value = '';
  constructor(private searchService: NbSearchService) {

    this.searchService.onSearchSubmit()
      .subscribe((data: any) => {
        this.value = data.term;
      });

  }
}
