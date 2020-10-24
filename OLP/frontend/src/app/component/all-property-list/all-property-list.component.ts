import { Component, OnInit } from '@angular/core';
import {Property} from "../../model/property.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {NbToastrService} from "@nebular/theme";

@Component({
  selector: 'app-all-property-list',
  templateUrl: './all-property-list.component.html',
  styleUrls: ['./all-property-list.component.scss']
})
export class AllPropertyListComponent implements OnInit {

  properties: Property[] = [];
  isLoading: boolean = false;
  constructor(private router: Router, private http: HttpClient, private toastrService: NbToastrService) {}

  loadNext() {

    // if (this.loading) { return }
    //
    // this.loading = true;
    // this.placeholders = new Array(this.pageSize);
    // this.newsService.load(this.pageToLoadNext, this.pageSize)
    //   .subscribe(news => {
    //     this.placeholders = [];
    //     this.news.push(...news);
    //     this.loading = false;
    //     this.pageToLoadNext++;
    //   });
  }


  ngOnInit(): void {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/listAllProperty', {})
      .subscribe( (data : Property[])=> {
          this.properties = data;
          this.isLoading = false;
        }
      );

  }

}
