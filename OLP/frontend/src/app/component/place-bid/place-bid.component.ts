import {Component, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbComponentStatus, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {Bid} from "../../model/bid.model";
import {Timer} from "../../model/timer.model";
import {AuctionBid} from "../../model/auction-bid.model";
import {PropertyAuction} from "../../model/property-auction.model";
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-place-bid',
  templateUrl: './place-bid.component.html',
  styleUrls: ['./place-bid.component.scss']
})
export class PlaceBidComponent implements OnInit {
  property: Property;
  propertyAuction: PropertyAuction;
  isLoading: boolean = false;
  auction: Auction;
  bid: Bid;
  time: Timer = new Timer(0, 0, 0, 0, 0);
  auctionBids: AuctionBid[] = [];
  updatedDate: Date = new Date();
  currentHighestBid: number;
  currentEndTime: Date;
  isAuctionCompleted: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService, public datePipe: DatePipe) {
  }

  ngOnInit(): void {
    let pid = this.route.snapshot.paramMap.get("pid");
    if (pid) {
      this.isLoading = true;
      this.http.post(environment.baseEndpoint + '/view-property-pid', {pid: pid})
        .subscribe((data: PropertyAuction[]) => {
            if (data && data.length > 0) {
              this.loadPropertyAuction(data);
              this.currentEndTime = this.auction.endTime;
              this.time = this.getTimeRemaining(this.auction.endTime);
              this.setupAuctionTimer();
              this.getBidHistory();
              this.setLoadBidHistoryTimer();
              this.isLoading = false;
            }
          }
        );
    }
  }

  private setLoadBidHistoryTimer() {
    const bidHistInterval = setInterval(() => {
      this.getBidHistory();
      this.getHighestBid();
      this.updatedDate = new Date();
      if (this.isAuctionCompleted || this.time.total < 0) {
        clearInterval(bidHistInterval);
      }
    }, 3000);
  }

  private setupAuctionTimer() {
    const timeInterval = setInterval(() => {
      this.time = this.getTimeRemaining(this.currentEndTime);
      if (this.isAuctionCompleted || this.time.total < 0) {
        clearInterval(timeInterval);
      }
    }, 1000);
  }

  private loadPropertyAuction(data: PropertyAuction[]) {
    this.property = data[0].property;
    this.auction = data[0].auction ? data[0].auction : new Auction();
    this.currentHighestBid = this.auction.currentPrice;
    this.bid = new Bid();
    this.bid.pid = this.auction.pid;
    this.bid.uid = this.auction.uid;
    this.bid.aid = this.auction.aid;
  }

  getHighestBid() {
    if (this.property?.pid) {
      this.http.post(environment.baseEndpoint + '/view-property-pid', {pid: this.property?.pid})
        .subscribe((data: PropertyAuction[]) => {
            if (data && data.length > 0) {
              this.property = data[0].property;
              this.auction = data[0].auction ? data[0].auction : new Auction();
              if (this.auction.currentPrice > this.currentHighestBid) {
                this.currentHighestBid = this.auction.currentPrice
                this.showToast('success', 'Current highest bid is increased to $' + this.formatCurrency(this.currentHighestBid) + '.');
              }
              if (this.currentEndTime != this.auction.endTime) {
                this.currentEndTime = this.auction.endTime;
                this.showToast('success', 'Current bid end time has been extended to ' + this.formatDate(this.currentEndTime) + '.');
              }
              this.checkIfAuctionCompleted();
            }
          }
        );
    }
  }

  private checkIfAuctionCompleted() {
    if (this.auction.status == 3 || this.auction.status == 4) {
      this.isAuctionCompleted = true;
      if (this.auction.status == 3) {
        if (this.userService.user.uid === this.auction.winner) {
          this.showToast('success', 'Congratulations, you are the winner of this auction!');
        } else {
          this.showToast('info', 'Sorry, the action is won by other buyer.');
        }
      }
      if (this.auction.status == 4) {
        this.showToast('info', 'Sorry, this property passed in.');
      }
    }
  }

  formatDate(t) {
    return this.datePipe.transform(t, 'dd/MM/yyyy hh:mm:ss a');
  }

  getBidHistory() {
    if (this.auction?.aid) {
      this.http.post(environment.baseEndpoint + '/view-bid', {aid: this.auction?.aid})
        .subscribe((data: AuctionBid[]) => {
            this.auctionBids = data;
          }
        );
    }
  }

  placeBid() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/place-bid', this.bid)
      .subscribe((data: any) => {
          if(!data.success) {
            this.showToast('success', `New bid has been placed.`);
          }else{
            this.showToast('danger', `Bid is not placed successfully because of other higher bid price.`);
          }
          this.isLoading = false;
        }
      );
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, {status});
  }

  cancel() {
    this.router.navigate(['/active-auctions', {}]);
  }

  address(p: Property) {
    if (p) {
      return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
    }
    return '';
  }

  onSelect($event: any) {
    this.auction.beginTime = new Date($event.year, $event.month - 1, $event.day, $event.hour, $event.minute, $event.second);
    console.log(this.auction.beginTime);
  }

  getTimeRemaining(endTime) {
    let current = new Date()
    const total = Date.parse(endTime) - current.getTime();
    const seconds = total > 0 ? Math.floor((total / 1000) % 60) : 0;
    const minutes = total > 0 ? Math.floor((total / 1000 / 60) % 60) : 0;
    const hours = total > 0 ? Math.floor((total / (1000 * 60 * 60)) % 24) : 0;
    const days = total > 0 ? Math.floor(total / (1000 * 60 * 60 * 24)) : 0;
    return new Timer(total, days, hours, minutes, seconds);
  }

  format(num: number) {
    return num < 10 ? '0' + num : num;
  }

  invalidate() {
    return this.bid.newPrice <= this.currentHighestBid || this.isAuctionCompleted || this.time.total <= 0;
  }

  formatCurrency(num: number) {
    return num.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
  }
}
