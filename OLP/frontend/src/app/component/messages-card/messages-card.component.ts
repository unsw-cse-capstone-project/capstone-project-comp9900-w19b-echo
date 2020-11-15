import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {environment} from "../../../environments/environment";
import {Message} from "../../model/message.model";

@Component({
  selector: 'app-messages-card',
  templateUrl: './messages-card.component.html',
  styleUrls: ['./messages-card.component.scss']
})
export class MessagesCardComponent implements OnInit {
  messages: Message[];

  constructor(private router: Router, private http: HttpClient, private userService: UserService) {
    this.http.post(environment.baseEndpoint+'/view-message', {uid: this.userService.user.uid})
      .subscribe((prop:Message[])=>{
        this.messages=prop;
      })
  }

  ngOnInit(): void {
  }

  getCount(flag : number) {
    return this.messages.filter(p => p.readFlag == flag).length;
  }
}
