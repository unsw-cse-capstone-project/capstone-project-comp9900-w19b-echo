import {Component, Input, OnInit} from '@angular/core';
import {AuctionBid} from "../../model/auction-bid.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-bid-history',
  templateUrl: './bid-history.component.html',
  styleUrls: ['./bid-history.component.scss']
})
export class BidHistoryComponent implements OnInit {
  isLoading: boolean = false;
  @Input() auctionBids: AuctionBid[];
  @Input() updatedDate: Date;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }
}
