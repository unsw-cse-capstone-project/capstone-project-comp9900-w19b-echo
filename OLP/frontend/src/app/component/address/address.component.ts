import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PaymentDetail} from "../../model/payment-detail.model";
import {User} from "../../model/user.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {NbComponentStatus} from "@nebular/theme/components/component-status";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent implements OnInit {
  isLoading: boolean = false;
  @Input() user: User;
  @Input() paymentDetail: PaymentDetail;
  @Output() save: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient, private userService: UserService, private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
  }

  saveAddress() {
    this.save.emit(this.paymentDetail);
  }

  showToast(status: NbComponentStatus) {
    this.toastrService.show(status, `Address Details - Updated`, { status });
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
}
