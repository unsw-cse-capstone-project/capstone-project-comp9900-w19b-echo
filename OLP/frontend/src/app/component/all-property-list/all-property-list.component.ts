import {Component, Input, OnInit}from '@angular/core';
import {Property} from "../../model/property.model";
import {Auction} from "../../model/auction.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {NbToastrService} from "@nebular/theme";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {PropertyAuction} from "../../model/property-auction.model";
@Component({
  selector: 'app-all-property-list',
  templateUrl: './all-property-list.component.html',
  styleUrls: ['./all-property-list.component.scss']
})
export class AllPropertyListComponent implements OnInit {
  @Input() properties: PropertyAuction[] = [];
  isLoading: boolean = false;
  auction: Auction = null;
  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService, private userService: UserService, private modalService: NgbModal) {}


  ngOnInit(): void {
  }


  detail(p: Property): void {
    this.userService.currentProperty = p;
    this.router.navigate(['/detail']);
  }

}
