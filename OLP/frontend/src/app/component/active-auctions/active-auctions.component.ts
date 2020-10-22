import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {Router} from "@angular/router";

@Component({
  selector: 'app-active-auctions',
  templateUrl: './active-auctions.component.html',
  styleUrls: ['./active-auctions.component.scss']
})
export class ActiveAuctionsComponent implements OnInit {
  properties = [
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Active', bidStartDate: '', bidEndDate: ''},
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Inactive', bidStartDate: '1/9/2020 2:00PM', bidEndDate: '1/9/2020 3:00PM' },
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Sold', bidStartDate: '15/9/2020 9:00AM', bidEndDate: '5/9/2020 10:00AM' },
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Passed In', bidStartDate: '15/10/2020 10:00AM', bidEndDate: '15/10/2020 12:00AM'  },
  ];

  constructor(private router: Router) {

  }

  ngOnInit(): void {
  }

  addProperty() {
    this.router.navigate(['/new-property', {}]);
  }
}
