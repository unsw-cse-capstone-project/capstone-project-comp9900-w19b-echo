import { Component, OnInit } from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {User} from "../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbComponentStatus, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {AuctionRegister} from "../../model/auction-register.model";

@Component({
  selector: 'app-join-bid',
  templateUrl: './join-bid.component.html',
  styleUrls: ['./join-bid.component.scss']
})
export class JoinBidComponent implements OnInit {
  property: Property;
  isLoading: boolean = false;
  auction: Auction;
  user: User;
  auctionRegister: AuctionRegister;

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService) { }

  ngOnInit(): void {
    this.property = this.userService.currentProperty;
    this.auction = this.userService.currentAuction ? this.userService.currentAuction : new Auction();
    this.auctionRegister = new AuctionRegister();
    this.auctionRegister.pid = this.auction.pid;
    this.auctionRegister.uid = this.auction.uid;
    this.auctionRegister.aid = this.auction.aid;
    this.auctionRegister.userType = 1;
    const email = this.userService.user?.email;
    if(email) {
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
          }
        );
    }
  }

  join() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/register-auction', {auctionRegister: this.auctionRegister})
      .subscribe((p: Property) => {
          this.property = p;
          setTimeout(() => {
            this.showToast('success');
          }, 1000);
          this.isLoading = false;
          this.router.navigate(['/interested-properties', {}]);
        }
      );
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `Auction - Initial bid has been placed successfully.`, { status });
  }

  cancel() {
    this.router.navigate(['/interested-properties', {}]);
  }

  address (p: Property) {
    if(p) {
      return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
    }
    return '';
  }

  onSelect($event: any) {
    this.auction.beginTime = new Date($event.year,$event.month-1, $event.day, $event.hour, $event.minute, $event.second);
    console.log(this.auction.beginTime);
  }
}
