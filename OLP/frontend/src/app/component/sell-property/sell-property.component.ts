import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbComponentStatus, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {User} from "../../model/user.model";

@Component({
  selector: 'app-sell-property',
  templateUrl: './sell-property.component.html',
  styleUrls: ['./sell-property.component.scss']
})
export class SellPropertyComponent implements OnInit {
  property: Property;
  isLoading: boolean = false;
  auction: Auction;
  user: User;

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService) { }

  ngOnInit(): void {
    this.property = this.userService.currentProperty;
    this.auction = this.userService.currentAuction ? this.userService.currentAuction : new Auction();
    const email = this.userService.user?.email;
    if(email) {
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
          }
        );
    }
  }

  save() {
    this.isLoading = true;
    this.auction.pid = this.property.pid;
    this.auction.uid = this.user.uid;
    let uri = this.auction.aid ? '/update-auction' : '/add-auction';
    this.http.post(environment.baseEndpoint + uri, {auction: this.auction})
      .subscribe((p: Property) => {
          this.property = p;
          setTimeout(() => {
            this.showToast('success');
          }, 1000);
          this.isLoading = false;
          this.router.navigate(['/my-properties', {}]);
        }
      );
  }

  showToast(status: NbComponentStatus) {
    let title = this.auction.aid ? `Auction - auction is updated.` : 'Auction - property is published for auction.';
    this.toastrService.show(status, title, { status });
  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
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
