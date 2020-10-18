import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-property',
  templateUrl: './new-property.component.html',
  styleUrls: ['./new-property.component.scss']
})
export class NewPropertyComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  save() {
    this.router.navigate(['/my-properties', {}]);

  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
  }
}
