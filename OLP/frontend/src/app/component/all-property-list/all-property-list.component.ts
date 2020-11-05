import {Component, Input, OnInit}from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {NbToastrService} from "@nebular/theme";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header">
      <h4 class="modal-title">Error</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <p>Please Register/Login Before Bid</p>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-primary" (click)="registerButton()">Register</button>
      <button type="button" class="btn btn-outline-dark" (click)="activeModal.close('Close click')">Close</button>
    </div>
  `
})
export class NgbdModalContent {
  @Input() name;

  constructor(private router: Router, public activeModal: NgbActiveModal) {}

  registerButton(){
    this.activeModal.close('Close click');
    this.router.navigate(['/auth/register']);

  }
}

@Component({
  selector: 'app-all-property-list',
  templateUrl: './all-property-list.component.html',
  styleUrls: ['./all-property-list.component.scss']
})
export class AllPropertyListComponent implements OnInit {
  @Input() properties: Property[] = [];
  isLoading: boolean = false;
  auction: Auction = null;
  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService, private userService: UserService, private modalService: NgbModal) {}


  ngOnInit(): void {
  }

  // joinBid(p: Property): void {
  //   this.userService.currentProperty = p;
  //   this.http.post(environment.baseEndpoint + '/view-auction', {"pid": p.pid})
  //     .subscribe( (data : Auction)=> {
  //         console.log(data)
  //         this.auction=data;
  //         this.isLoading = false;
  //         this.userService.currentAuction = this.auction[0];
  //         this.router.navigate(['/join-bid']);
  //       },
  //       (error)=>{
  //         if(error.status==401)
  //           this.router.navigate(['/auth/register']);
  //       }
  //     );
  // }



  open() {
    const modalRef = this.modalService.open(NgbdModalContent);
    modalRef.componentInstance.name = 'World';
  }


  detail(p: Property): void {
    this.userService.currentProperty = p;
    this.router.navigate(['/detail']);
  }

  address (p: Property) {
    return p.streetNumber + ' ' + p.streetName + ', ' + p.suburb + ' ' + p.state + ' ' + p.postcode;
  }

}
