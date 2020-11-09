import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {environment} from "../../../environments/environment";
import {CommonService} from "../../service/common.service";
import {PropertyAuction} from "../../model/property-auction.model";

@Component({
  selector: 'app-property-card',
  templateUrl: './property-card.component.html',
  styleUrls: ['./property-card.component.scss']
})
export class PropertyCardComponent implements OnInit {
  @Input() propertyAuction: PropertyAuction;

  constructor(private commonService: CommonService) { }

  ngOnInit(): void {
  }

  getPicUrl(p: Property) {
    if(p && p.picUrl && p.picUrl.length > 0){
      return environment.baseResourceEndpoint + p.picUrl[0];
    }
  }

  getAddress (p: Property) {
    if(p) {
      return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb;
    }
    return '';
  }

  getPropertyType(propertyType: number) {
    return this.commonService.getPropertyType(propertyType);
  }
}
