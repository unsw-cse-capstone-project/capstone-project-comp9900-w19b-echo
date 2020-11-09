import { Injectable } from '@angular/core';
import {Property} from "../model/property.model";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor() { }


  getPropertyStatus(status: number) {
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
    if(status == 5) {
      return 'Not Listed';
    }
    return '';
  }

  getAuctionStatus(status: number) {
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

  getPropertyType(propertyType: number) {
    if(propertyType == 0){
      return 'House';
    }
    if(propertyType == 1){
      return 'Unit';
    }
    if(propertyType == 2) {
      return 'Store';
    }
    return '';
  }

  getAddress (p: Property) {
    if(p) {
      return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
    }
    return '';
  }
}
