import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {User} from "../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbComponentStatus, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {AuctionRegister} from "../../model/auction-register.model";
import {PropertyAuction} from "../../model/property-auction.model";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss']
})
export class PropertyDetailComponent implements OnInit {
  propertyAuction: PropertyAuction;
  isLoading: boolean = false;

  constructor(private router: Router, private commonService: CommonService, public userService: UserService) { }

  ngOnInit(): void {
    this.propertyAuction = this.userService.currentPropertyAuction;
  }

  getPicUrl(p: Property) {
    if(p && p.picUrl && p.picUrl.length > 0){
      return environment.baseResourceEndpoint + p.picUrl[0];
    }
  }

  getAddress (p: Property) {
    if(p) {
      return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb;
    }
    return '';
  }

  getPropertyType(propertyType: number) {
    return this.commonService.getPropertyType(propertyType);
  }

  joinBid(propertyAuction: PropertyAuction) {
    this.userService.currentProperty = propertyAuction.property;
    this.userService.currentAuction = propertyAuction.auction;
    this.userService.currentPropertyAuction = propertyAuction;
    this.router.navigate(['/join-bid']);
  }

  login(propertyAuction: PropertyAuction) {
    this.router.navigate(['/auth/login', {}]);
  }
}
