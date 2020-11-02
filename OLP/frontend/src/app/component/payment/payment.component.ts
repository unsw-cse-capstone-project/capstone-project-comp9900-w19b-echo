import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {User} from "../../model/user.model";
import {PaymentDetail} from "../../model/payment-detail.model";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  paymentDetail: PaymentDetail = new PaymentDetail();
  isLoading: boolean = false;
  @Input() user: User;

  constructor(private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    this.getPaymentDetail();
  }

  getPaymentDetail() {
    const uid = this.userService.currentUser?.uid;
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

  save() {
    this.isLoading = true;
    let uri = this.paymentDetail.serial ? '/update-payment' : '/add-payment';
    this.paymentDetail.uid = this.userService.currentUser.uid;
    this.http.post(environment.baseEndpoint + uri, {paymentDetail: this.paymentDetail})
      .subscribe((u: User) => {
          this.showToast('success');
          this.getPaymentDetail();
          this.isLoading = false;
        }
      );
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `Payment Details - Updated`, { status });
  }

}
