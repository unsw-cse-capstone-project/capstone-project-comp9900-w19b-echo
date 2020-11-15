import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {PropertyAuction} from "../../model/property-auction.model";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-my-properties',
  templateUrl: './my-properties.component.html',
  styleUrls: ['./my-properties.component.scss']
})
export class MyPropertiesComponent implements OnInit {
  properties: PropertyAuction[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-property', {})
      .subscribe( (data : PropertyAuction[])=> {
          this.properties = data;
          this.isLoading = false;
        }
      );
  }

  addProperty() {
    this.router.navigate(['/new-property', {}]);
  }

  deleteProperty($event) {
    let prop = $event;
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/remove-property', {property: prop})
      .subscribe((data: any) => {
          if(data) {
            this.showToast('success', 'Property - Property is removed.');
            this.properties = this.properties.filter(p1 => p1.property.pid != prop.pid);
            this.isLoading = false;
          }
         }
      );
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, { status });
  }
}
