import {Component, OnInit, Output} from '@angular/core';
import { EventEmitter } from '@angular/core';
import {Property} from "../../model/property.model";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {
  searchCriteria = {text:'', propertyType: -1, state: '', suburb: '', bedroom: 0, bathroom: 0, carport: 0, }

  @Output() search: EventEmitter<any> = new EventEmitter();
  displayAdvanced: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  searchProperty() {
    this.search.emit(this.searchCriteria);
  }

}
