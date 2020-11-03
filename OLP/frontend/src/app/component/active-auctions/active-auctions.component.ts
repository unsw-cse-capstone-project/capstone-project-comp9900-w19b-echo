import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {Router} from "@angular/router";
import {PropertyAuction} from "../../model/property-auction.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbDialogService, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {Property} from "../../model/property.model";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {Auction} from "../../model/auction.model";

@Component({
  selector: 'app-active-auctions',
  templateUrl: './active-auctions.component.html',
  styleUrls: ['./active-auctions.component.scss']
})
export class ActiveAuctionsComponent implements OnInit {
  properties: PropertyAuction[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private toastrService: NbToastrService, private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-property', {})
      .subscribe( (data : PropertyAuction[])=> {
          this.properties = data.filter(p => p.auction != null && (p.auction.status == 1 || p.auction.status == 2));
          this.isLoading = false;
        }
      );
  }

  addProperty() {
    this.router.navigate(['/new-property', {}]);
  }


  address (p: Property) {
    return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
  }


  status(status: number) {
    if(status == 0){
      return 'Not Verified';
    }
    if(status == 1){
      return 'Verified';
    }
    if(status == 2){
      return 'On Auction';
    }
    if(status == 3) {
      return 'Sold';
    }
    if(status == 4) {
      return 'Passed In';
    }
    return '';
  }

  statusOfAuction(status: number) {
    if(status == 1){
      return 'Not started';
    }
    if(status == 2){
      return 'Started';
    }
    if(status == 3) {
      return 'Failed';
    }
    if(status == 4) {
      return 'Success';
    }
    return '';
  }

  edit(p: Property) {
    this.userService.currentProperty = p;
    this.router.navigate(['/new-property', {pid: p.pid}]);
  }

  delete(p: Property){
    this.dialogService.open(ConfirmationDialogComponent,{
      context: {
        title: 'Are you sure to quite the bid?',
      },
    })
      .onClose.subscribe(data => {
      if(data == true) {
        this.properties = this.properties.filter(p1 => p1.property.pid != p.pid);
      }
    });
  }

  placeBid(p: Property, auction: Auction) {
    this.userService.currentProperty = p;
    this.userService.currentAuction = auction;
    this.router.navigate(['/place-bid']);
  }
}
