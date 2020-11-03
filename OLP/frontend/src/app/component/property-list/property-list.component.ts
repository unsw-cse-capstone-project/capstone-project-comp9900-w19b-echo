import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {NbDialogService} from "@nebular/theme";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {PropertyAuction} from "../../model/property-auction.model";
import {Auction} from "../../model/auction.model";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  @Input() properties: PropertyAuction[];

  constructor(private router: Router, private userService: UserService, private dialogService: NbDialogService) { }

  ngOnInit(): void {
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
        title: 'Are you sure to delete this property?',
      },
    })
      .onClose.subscribe(data => {
        if(data == true) {
          this.properties = this.properties.filter(p1 => p1.property.pid != p.pid);
        }
      });
  }

  sellProperty(p: Property, auction: Auction) {
    this.userService.currentProperty = p;
    this.userService.currentAuction = auction;
    this.router.navigate(['/sell-property']);
  }


}
