import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import {PropertyAuction} from "../../model/property-auction.model";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-auction-card',
  templateUrl: './auction-card.component.html',
  styleUrls: ['./auction-card.component.scss']
})
export class AuctionCardComponent implements OnInit {
  isLoading = false;
  activeProperties: PropertyAuction[] = [];
  completedProperties: PropertyAuction[] = [];

  constructor(private router: Router, private http: HttpClient, private userService: UserService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-active-auction', {uid: this.userService.user?.uid})
      .subscribe( (data : PropertyAuction[])=> {
          this.activeProperties = data;
          this.http.post(environment.baseEndpoint + '/view-complete-auction', {uid: this.userService.user?.uid})
            .subscribe( (data : PropertyAuction[])=> {
                this.completedProperties = data;
                this.isLoading = false;
              }
            );
        }
      );
  }

  getCount(properties :PropertyAuction[], status : number[]) {
    return properties.filter(p => status.includes(p.auction.status)).length;
  }
}
