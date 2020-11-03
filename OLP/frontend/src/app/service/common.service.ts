import { Injectable } from '@angular/core';

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
}
