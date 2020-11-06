import { Component, OnInit } from '@angular/core';
import {PropertyAuction} from "../../model/property-auction.model";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-interested-property-card',
  templateUrl: './interested-property-card.component.html',
  styleUrls: ['./interested-property-card.component.scss']
})
export class InterestedPropertyCardComponent implements OnInit {
  properties: PropertyAuction[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/view-favorite', {})
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
