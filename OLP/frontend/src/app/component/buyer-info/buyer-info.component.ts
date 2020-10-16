import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-buyer-info',
  templateUrl: './buyer-info.component.html',
  styleUrls: ['./buyer-info.component.scss'],
})
// export class InputsShowcaseComponent {}
// export class CardShowcaseComponent {}
export class BuyerInfoComponent implements OnInit {
  selectedItem="2";
  constructor() { }

  ngOnInit(): void {
  }

}
