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
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-interested-properties',
  templateUrl: './interested-properties.component.html',
  styleUrls: ['./interested-properties.component.scss']
})
export class InterestedPropertiesComponent implements OnInit {
  properties: PropertyAuction[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private toastrService: NbToastrService, private dialogService: NbDialogService, private commonService: CommonService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-property', {})
      .subscribe( (data : PropertyAuction[])=> {
          this.properties = data.filter(p => p.property.status == 1);
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

  edit(p: Property) {
    this.userService.currentProperty = p;
    this.router.navigate(['/new-property', {pid: p.pid}]);
  }

  delete(p: Property){
    this.dialogService.open(ConfirmationDialogComponent,{
      context: {
        title: 'Are you sure to remove this property from interested list?',
      },
    })
      .onClose.subscribe(data => {
      if(data == true) {
        this.properties = this.properties.filter(p1 => p1.property.pid != p.pid);
      }
    });
  }

  joinBid(p: Property, auction: Auction) {
    this.userService.currentProperty = p;
    this.userService.currentAuction = auction;
    this.router.navigate(['/join-bid']);
  }
}
