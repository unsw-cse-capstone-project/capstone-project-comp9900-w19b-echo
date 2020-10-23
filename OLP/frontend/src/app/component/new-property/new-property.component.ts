import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Property} from "../../model/property.model";
import {environment} from "../../../environments/environment";
import {User} from "../../model/user.model";
import {NbComponentStatus} from "@nebular/theme/components/component-status";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {AddPropertyRequest} from "../../model/add-property-request.model";

@Component({
  selector: 'app-new-property',
  templateUrl: './new-property.component.html',
  styleUrls: ['./new-property.component.scss']
})
export class NewPropertyComponent implements OnInit {
  property: Property;
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) { }

  ngOnInit(): void {
    this.property = new Property();
    this.property.propertyType = 0;
  }

  save() {
    this.isLoading = true;
    this.property.owner = this.userService.user.uid;
    this.property.city = this.property.suburb;
    this.http.post(environment.baseEndpoint + '/add-property', {property: this.property})
      .subscribe((p: Property) => {
          this.property = p;
          this.showToast('success');
          this.isLoading = false;
          this.router.navigate(['/my-properties', {}]);
        }
      );
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `New property - Added`, { status });
  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
  }

  onPropertyTypeChange(propertyType: number) {
    this.property.propertyType = propertyType;
  }
}
