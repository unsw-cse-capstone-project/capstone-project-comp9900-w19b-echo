import { Component, OnInit } from '@angular/core';
import { NbAuthJWTToken, NbAuthService } from '@nebular/auth';
import {environment} from "../../../environments/environment";
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {NbToastrService} from "@nebular/theme";
import {UserService} from "../../service/user.service";
import {PropertyAuction} from "../../model/property-auction.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  isLoading: boolean = false;
  properties: PropertyAuction[] = [];

  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService, private userService: UserService) {

  }

  ngOnInit(): void {
    this.getRecommendProperties();
  }

  getRecommendProperties() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/listAllProperty', {})
      .subscribe( (p : PropertyAuction[])=> {
          this.properties = p;
          this.isLoading = false;
        }
      );
  }

  searchProperty($event: any) {
    this.isLoading = true;
    let uri = $event ? '/search-property-like' : '/listAllProperty';
    this.http.post(environment.baseEndpoint + '/search-property-like', {keyword: $event})
      .subscribe( (p : PropertyAuction[])=> {
          this.properties = p;
          this.isLoading = false;
        }
      );
  }
}
