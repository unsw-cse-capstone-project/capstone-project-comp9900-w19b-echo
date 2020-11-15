import {Component, OnInit} from '@angular/core';
import {NbAuthJWTToken, NbAuthService} from '@nebular/auth';
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

  constructor(private router: Router, private http: HttpClient,
              private toastrService: NbToastrService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.getRecommendProperties();
  }

  getRecommendProperties() {
    this.isLoading = true;
    let uri = this.userService.authenticated ? '/get-recommendation' : '/listAllProperty';
    this.http.post(environment.baseEndpoint + uri, {})
      .subscribe((p: PropertyAuction[]) => {
          if (p) {
            this.properties = p;
          }
          this.isLoading = false;
        }
      );
  }

  searchProperty($event: any) {
    this.isLoading = true;
    if ($event.propertyType > -1 || $event.state || $event.suburb
      || $event.bedroom > 0 || $event.bathroom > 0 || $event.carport > 0) {
      this.http.post(environment.baseEndpoint + '/search-property', $event)
        .subscribe((p: PropertyAuction[]) => {
            if (p) {
              this.properties = p;
            }
            this.isLoading = false;
          }
        );
    } else {
      this.http.post(environment.baseEndpoint + '/search-property-like', {keyword: $event.text})
        .subscribe((p: PropertyAuction[]) => {
            if (p) {
              this.properties = p;
            }
            this.isLoading = false;
          }
        );
    }
  }
}
