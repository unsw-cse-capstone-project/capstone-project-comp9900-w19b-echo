import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {Property} from "../../model/property.model";

@Component({
  selector: 'app-my-properties',
  templateUrl: './my-properties.component.html',
  styleUrls: ['./my-properties.component.scss']
})
export class MyPropertiesComponent implements OnInit {
  properties: Property[] = [];
  isLoading: boolean = false;

  constructor(private router: Router, private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/my-property', {})
      .subscribe( (data : Property[])=> {
          this.properties = data;
          this.isLoading = false;
        }
      );
  }

  addProperty() {
    this.router.navigate(['/new-property', {}]);
  }
}
