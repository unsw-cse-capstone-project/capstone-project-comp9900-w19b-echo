import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-seller-info',
  templateUrl: './seller-info.component.html',
  styleUrls: ['./seller-info.component.scss']
})
export class SellerInfoComponent implements OnInit {
  selectedItem="2";
  constructor() { }

  ngOnInit(): void {
  }

}
