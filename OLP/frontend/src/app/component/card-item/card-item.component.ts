import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-card-item',
  templateUrl: './card-item.component.html',
  styleUrls: ['./card-item.component.scss']
})
export class CardItemComponent implements OnInit {
  @Input() heading: string;
  @Input() value: string;
  @Input() styleClass: string;

  constructor() { }

  ngOnInit(): void {
  }

}
