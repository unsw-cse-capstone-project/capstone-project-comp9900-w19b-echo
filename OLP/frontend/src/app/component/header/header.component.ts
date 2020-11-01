import { Component, OnInit } from '@angular/core';
import {NbAuthJWTToken, NbAuthService} from "@nebular/auth";
import { NbSearchService } from '@nebular/theme';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: User = new User();
  items = [{ title: 'Profile' }, { title: 'Log out' }];

  constructor(public userService: UserService, private authService: NbAuthService, private router: Router) {
    this.user = userService.user;
  }

  ngOnInit(): void {
  }

  isPage(url: string) {
    return this.router.url === url;
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
