import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {environment} from "../../../environments/environment";
import { Message } from "../../model/message.model";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {

  messages :Message[] = [];

  constructor(private router: Router, private http: HttpClient, private userService: UserService) {
    let uri = '/view-message';
    this.http.post(environment.baseEndpoint+uri, {uid: this.userService.user.uid})
    .subscribe((prop:Message[])=>{
      this.messages=prop;
    })
  }

  ngOnInit(): void {
  }

  sendEmail(): void {
    this.router.navigate(['/send-email']);
  }
}
