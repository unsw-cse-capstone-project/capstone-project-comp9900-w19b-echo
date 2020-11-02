import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user.model";
import {NbAuthJWTToken, NbAuthService} from "@nebular/auth";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  @Input() user: User;
  isLoading: boolean = false;

  constructor(private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    //this.getProfile();
  }

  getProfile() {
    const email = this.userService.user?.email;
    if(email) {
      this.isLoading = true;
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
            this.isLoading = false;
          }
        );
    }
  }

  saveProfile() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/user', this.user)
      .subscribe((u: User) => {
        this.user = u;
        this.showToast('success');
        this.isLoading = false;
      }
    );
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `User Profile - Updated`, { status });
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
}
