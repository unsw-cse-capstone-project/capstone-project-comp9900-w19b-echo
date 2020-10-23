import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  @Input() properties: Property[];

  constructor() { }

  ngOnInit(): void {
  }

  address (p: Property) {
    return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
  }

  status(status: number) {
    if(status == 0){
      return 'Active';
    }
    return 'Inactive';
  }
}
