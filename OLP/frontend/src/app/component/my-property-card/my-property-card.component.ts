import { Component, OnInit } from '@angular/core';
import {PropertyAuction} from "../../model/property-auction.model";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-my-property-card',
  templateUrl: './my-property-card.component.html',
  styleUrls: ['./my-property-card.component.scss']
})
export class MyPropertyCardComponent implements OnInit {
  properties: PropertyAuction[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-property', {})
      .subscribe( (data : PropertyAuction[])=> {
          this.properties = data;
          this.isLoading = false;
        }
      );
  }

  getCount(status : number[]) {
    return this.properties.filter(p => status.includes(p.property.status)).length;
  }
}
