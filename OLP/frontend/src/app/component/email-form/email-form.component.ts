import { Component, OnInit } from '@angular/core';
import { FormGroup,FormControl} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-email-form',
  templateUrl: './email-form.component.html',
  styleUrls: ['./email-form.component.scss']
})
export class EmailFormComponent implements OnInit {
  contactForm: FormGroup;

  constructor(private router: Router, private http: HttpClient) { 
    this.contactForm = new FormGroup({
      subject: new FormControl(),
      content: new FormControl(),
   });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    let uri = '/send-email';
    this.http.post(environment.baseEndpoint+uri, {content: this.contactForm.value.content, subject: this.contactForm.value.subject, to: "jason.lee@gmail.com"})
    .subscribe(res=>{
      console.log(res)
    })
  }

}
