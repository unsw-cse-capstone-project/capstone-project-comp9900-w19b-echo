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
  properties = [
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Active', bidStartDate: '', bidEndDate: ''},
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Inactive', bidStartDate: '1/9/2020 2:00PM', bidEndDate: '1/9/2020 3:00PM' },
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Sold', bidStartDate: '15/9/2020 9:00AM', bidEndDate: '5/9/2020 10:00AM' },
    {streetNumber: 1, streetName: 'George Street', suburb: 'Sydney', postcode: '2000', state: 'NSW',  country: 'Australia', noOfBedroom: 4, noOfBathroom: 2, noOfParking: 2, description: 'Great property', owner: '100001', propertyType: 1, status: 'Passed In', bidStartDate: '15/10/2020 10:00AM', bidEndDate: '15/10/2020 12:00AM'  },
  ];

  // messages: Message[]=[];

  messages = [
    {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
    {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
      {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
      {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
      {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
      {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0},
      {aid: 0, content: "test", pid: 0, readFlag: 0,
      sender: "test", serial: 0, subject: "test",
      uid: 0}
  ]
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
