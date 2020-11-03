import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
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

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService) { }

  ngOnInit(): void {
    const pid = this.route.snapshot.paramMap.get('pid');
    if(pid){
      this.property = this.userService.currentProperty;
    }else {
      this.property = new Property();
      this.property.propertyType = 0;
    }
  }

  getProperty(pid) {

  }

  save() {
    this.isLoading = true;
    this.property.owner = this.userService.user.uid;
    this.property.city = this.property.suburb;
    let uri = this.property.pid ? '/update-property' : '/add-property';
    let title = this.property.pid ? 'Property - Updated' : 'New property - Added';

    this.http.post(environment.baseEndpoint + uri, {property: this.property})
      .subscribe((p: Property) => {
          this.property = p;
          setTimeout(() => {
            this.showToast('success', title);
          }, 1000);
          this.isLoading = false;
          this.router.navigate(['/my-properties', {}]);
        }
      );
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, { status });
  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
  }

  onPropertyTypeChange(propertyType: number) {
    this.property.propertyType = propertyType;
  }
}
