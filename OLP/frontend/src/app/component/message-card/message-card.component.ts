import { Component, OnInit, Input } from '@angular/core';
import {Router} from "@angular/router";
import {Message} from "../../model/message.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
@Component({
  selector: 'app-message-card',
  templateUrl: './message-card.component.html',
  styleUrls: ['./message-card.component.scss']
})
export class MessageCardComponent implements OnInit {
  @Input() message: Message;
  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }

  onDelete(m: Message){
    let uri = '/delete-message';
    this.http.post(environment.baseEndpoint+uri, {serial: m.serial})
    .subscribe(response=>{
      console.log(response)
    })
  }
}
