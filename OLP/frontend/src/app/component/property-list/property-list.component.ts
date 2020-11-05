import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {NbDialogService} from "@nebular/theme";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {PropertyAuction} from "../../model/property-auction.model";
import {Auction} from "../../model/auction.model";
import {CommonService} from "../../service/common.service";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  @Input() properties: PropertyAuction[];
  @Output() delete: EventEmitter<any> = new EventEmitter();

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private dialogService: NbDialogService, private commonService: CommonService) { }

  ngOnInit(): void {
  }

  address (p: Property) {
    return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
  }

  edit(p: Property) {
    this.userService.currentProperty = p;
    this.router.navigate(['/new-property', {pid: p.pid}]);
  }

  deleteProperty(p: Property){
    this.dialogService.open(ConfirmationDialogComponent,{
      context: {
        title: 'Are you sure to delete this property?',
      },
    })
      .onClose.subscribe(data => {
        if(data == true) {
          this.delete.emit(p);
        }
      });
  }

  sellProperty(p: Property, auction: Auction) {
    this.userService.currentProperty = p;
    this.userService.currentAuction = auction;
    this.router.navigate(['/sell-property']);
  }


}
