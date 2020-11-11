import {Component, OnInit, Output} from '@angular/core';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {
  text: string = '';
  checked: boolean = false;

  @Output() search: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  searchProperty() {
    this.search.emit(this.text);
  }
}
