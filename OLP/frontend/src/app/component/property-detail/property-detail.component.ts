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
  styleUrls: ['./property-detail.component.scss'],
  styles: [`
    agm-map {
      height: 300px;
    }
  `]
})
export class PropertyDetailComponent implements OnInit {
  propertyAuction: PropertyAuction;
  isLoading: boolean = false;
  isLiked: boolean = false;

  constructor(private router: Router, private commonService: CommonService, public userService: UserService, private http: HttpClient, private toastrService: NbToastrService) { }

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

  addToFav(propertyAuction: PropertyAuction, add: boolean) {
    if(!this.userService.authenticated) {
      this.router.navigate(['/auth/login', {}]);
    }else{
      this.isLoading = true;
      let uri = add ? '/add-favorite' : '/cancel-favorite';
      this.http.post(environment.baseEndpoint + uri, {uid: this.userService.user?.uid, pid: propertyAuction.property.pid})
        .subscribe( (data)=> {
            if(data) {
              let title = add ? 'Property - Added to favourite.' : 'Property - Removed from favourite.';
              this.showToast('success', title);
              this.isLiked = !this.isLiked;
              this.isLoading = false;
            }
          }
        );
    }
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, { status });
  }
}
