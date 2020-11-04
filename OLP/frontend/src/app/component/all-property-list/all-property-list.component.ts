import {Component, Input, OnInit}from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {NbToastrService} from "@nebular/theme";

@Component({
  selector: 'app-all-property-list',
  templateUrl: './all-property-list.component.html',
  styleUrls: ['./all-property-list.component.scss']
})
export class AllPropertyListComponent implements OnInit {
  @Input() properties: Property[] = [];
  isLoading: boolean = false;
  auction: Auction = null;
  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService, private userService: UserService) {}


  ngOnInit(): void {
  }

  joinBid(p: Property): void {
    this.userService.currentProperty = p;
    this.http.post(environment.baseEndpoint + '/view-auction', {"pid": p.pid})
      .subscribe( (data : Auction)=> {
          console.log(data)
          this.auction=data;
          this.isLoading = false;
          this.userService.currentAuction = this.auction[0];
          this.router.navigate(['/join-bid']);
        }
      );
  }

  detail(p: Property): void {
    this.userService.currentProperty = p;
    this.router.navigate(['/detail']);
  }



}
