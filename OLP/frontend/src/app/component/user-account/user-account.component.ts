import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import {User} from "../../model/user.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {PaymentDetail} from "../../model/payment-detail.model";
import {NbToastrService} from "@nebular/theme";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  user: User = new User();
  paymentDetail: PaymentDetail = new PaymentDetail();
  isLoading: boolean = false;

  constructor(private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {

  }

  ngOnInit(): void {
    this.getProfile();
    this.getPaymentDetail();
  }

  getProfile() {
    const email = this.userService.user?.email;
    if(email) {
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
            this.userService.currentUser = u;
          }
        );
    }
  }

  getPaymentDetail() {
    const uid = this.userService.user?.uid;
    if(uid) {
      this.isLoading = true;
      this.http.post(environment.baseEndpoint + '/view-payment', {uid: uid})
        .subscribe((paymentDetails: PaymentDetail[]) => {
            if (paymentDetails && paymentDetails.length > 0){
              this.paymentDetail = paymentDetails[0];
            }
            this.isLoading = false;
          }
        );
    }
  }

  save(updateType: string) {
    this.isLoading = true;
    let uri = this.paymentDetail.serial ? '/update-payment' : '/add-payment';
    this.paymentDetail.uid = this.userService.currentUser.uid;
    this.http.post(environment.baseEndpoint + uri, {paymentDetail: this.paymentDetail})
      .subscribe((u: User) => {
          this.showToast('success', updateType + ` Details - Updated`);
          this.getPaymentDetail();
          this.isLoading = false;
        }
      );
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, { status });
  }
}
