import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../model/property.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {NbDialogService} from "@nebular/theme";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  @Input() properties: Property[];

  constructor(private router: Router, private userService: UserService, private dialogService: NbDialogService) { }

  ngOnInit(): void {
  }

  address (p: Property) {
    return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
  }

  status(status: number) {
    if(status == 0){
      return 'Active';
    }
    if(status == 1) {
      return 'Sold';
    }
    if(status == 2) {
      return 'Passed In';
    }
    return 'Inactive';
  }

  edit(p: Property) {
    this.userService.currentProperty = p;
    this.router.navigate(['/new-property', {pid: p.pid}]);
  }

  delete(p: Property){
    this.dialogService.open(ConfirmationDialogComponent,{
      context: {
        title: 'Are you sure to delete this property?',
      },
    })
      .onClose.subscribe(data => {
        if(data == true) {
          this.properties = this.properties.filter(p1 => p1.pid != p.pid);
        }
      });
  }
}
