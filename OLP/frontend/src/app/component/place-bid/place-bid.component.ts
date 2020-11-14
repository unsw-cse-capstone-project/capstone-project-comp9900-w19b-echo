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

    constructor(private route: ActivatedRoute, private router: Router,
                private http: HttpClient, private userService: UserService,
                private toastrService: NbToastrService) {
    }

    ngOnInit(): void {
        let pid = this.route.snapshot.paramMap.get("pid");
        if (pid) {
            this.isLoading = true;
            this.http.post(environment.baseEndpoint + '/view-property-pid', {pid: pid})
                .subscribe((data: PropertyAuction[]) => {
                        if (data && data.length > 0) {
                            this.loadPropertyAuction(data);
                            this.time = this.getTimeRemaining(this.auction.endTime);
                            this.setupAcutionTimer();
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
            this.updatedDate = new Date();
        }, 3000);
    }

    private setupAcutionTimer() {
        const timeinterval = setInterval(() => {
            this.time = this.getTimeRemaining(this.auction.endTime);
            if (this.time.total <= 0) {
                clearInterval(timeinterval);
            }
        }, 1000);
    }

    private loadPropertyAuction(data: PropertyAuction[]) {
        this.property = data[0].property;
        this.auction = data[0].auction ? data[0].auction : new Auction();
        this.currentHighestBid = this.auction.basePrice;
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
                            this.currentHighestBid = this.auction.basePrice
                        }
                    }
                );
        }
    }

    getBidHistory() {
        if(this.auction?.aid) {
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
            .subscribe((p: Property) => {
                    this.showToast('success');
                    this.isLoading = false;
                }
            );
    }

    showToast(status: NbComponentStatus) {
        this.toastrService.show(status, `Auction - New bid has been placed.`, {status});
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
        const seconds = Math.floor((total / 1000) % 60);
        const minutes = Math.floor((total / 1000 / 60) % 60);
        const hours = Math.floor((total / (1000 * 60 * 60)) % 24);
        const days = Math.floor(total / (1000 * 60 * 60 * 24));
        return new Timer(total, days, hours, minutes, seconds);
    }

    format(num: number) {
        return num < 10 ? '0' + num : num;
    }
}
