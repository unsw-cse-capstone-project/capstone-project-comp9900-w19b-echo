import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Property} from "../../model/property.model";

@Component({
  selector: 'app-new-property',
  templateUrl: './new-property.component.html',
  styleUrls: ['./new-property.component.scss']
})
export class NewPropertyComponent implements OnInit {
  property: Property;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.property = new Property();
  }

  save() {
    this.router.navigate(['/my-properties', {}]);

  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
  }
}
