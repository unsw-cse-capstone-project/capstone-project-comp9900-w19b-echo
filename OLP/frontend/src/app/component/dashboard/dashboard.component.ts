import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {NbToastrService} from "@nebular/theme";
import {UserService} from "../../service/user.service";
import {PropertyAuction} from "../../model/property-auction.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  isLoading: boolean = false;
  properties: PropertyAuction[] = [];

  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService, private userService: UserService) {
  }

  ngOnInit(): void {
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
