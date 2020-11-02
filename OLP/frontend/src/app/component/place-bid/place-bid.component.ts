import { Component, OnInit } from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {User} from "../../model/user.model";
import {AuctionRegister} from "../../model/auction-register.model";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbComponentStatus, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {Bid} from "../../model/bid.model";
import {Timer} from "../../model/timer.model";

@Component({
  selector: 'app-place-bid',
  templateUrl: './place-bid.component.html',
  styleUrls: ['./place-bid.component.scss']
})
export class PlaceBidComponent implements OnInit {
  property: Property;
  isLoading: boolean = false;
  auction: Auction;
  user: User;
  bid: Bid;
  time: Timer = new Timer(0,0,0,0,0);

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService) { }

  ngOnInit(): void {
    this.property = this.userService.currentProperty;
    this.auction = this.userService.currentAuction ? this.userService.currentAuction : new Auction();
    this.bid = new Bid();
    this.bid.pid = this.auction.pid;
    this.bid.uid = this.auction.uid;
    this.bid.aid = this.auction.aid;
    const email = this.userService.user?.email;
    if(email) {
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
          }
        );
    }
    this.time = this.getTimeRemaining(this.auction.endTime);
    const timeinterval = setInterval(() => {
      this.time = this.getTimeRemaining(this.auction.endTime);
      if (this.time.total <= 0) {
        clearInterval(timeinterval);
      }
    },1000);
  }

  place() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/place-bid', this.bid)
      .subscribe((p: Property) => {
          this.property = p;
          this.showToast('success');
          this.isLoading = false;
        }
      );
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `Auction - New bid has been placed.`, { status });
  }

  cancel() {
    this.router.navigate(['/active-auctions', {}]);
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

  getTimeRemaining(endTime){
    let current = new Date()
    const total = Date.parse(endTime) - current.getTime();
    const seconds = Math.floor( (total/1000) % 60 );
    const minutes = Math.floor( (total/1000/60) % 60 );
    const hours = Math.floor( (total/(1000*60*60)) % 24 );
    const days = Math.floor( total/(1000*60*60*24) );
    return new Timer(total, days, hours, minutes, seconds);
  }

  format(num: number) {
    return num < 10 ? '0' + num : num;
  }
}
