import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  @Input() properties: Property[];

  constructor(private router: Router, private userService: UserService) { }

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

  edit(p: Property) {
    this.userService.currentProperty = p;
    this.router.navigate(['/new-property', {pid: p.pid}]);
  }
}
