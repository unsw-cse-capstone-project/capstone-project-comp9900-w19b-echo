import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {BtnCellRenderer} from "../btn-cell-renderer/btn-cell-renderer.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {
  @ViewChild('agGrid') agGrid: AgGridAngular;
  private gridApi;
  private gridColumnApi;

  columnDefs = [
    { field: 'date', sortable: true, filter: true, minWidth: 200},
    { field: 'from', sortable: true, filter: true, editable:true},
    { field: 'subject', sortable: true, filter: true, editable:true},
    { field: 'message', sortable: true, filter: true, editable:true},
    {
      field: 'messageId',
      headerName: 'Actions',
      cellRenderer: 'btnCellRenderer',
      cellRendererParams: {
        clicked: function (field: any) {
          alert(`${field} was clicked`);
        }
      },
      minWidth: 470
    },
  ];
  frameworkComponents: {
    btnCellRenderer: BtnCellRenderer,
  };
  rowData = [
    { date: '15/10/2020 2:00PM', from: 'Mike Jackson', subject: 'Property Enquiry', message: 'This is to ask you about the property detail'},
    { date: '14/10/2020 2:00PM', from: 'Tom Lee', subject: 'Bid Enquiry', message: 'May I check with you for the bid date?' },
    { date: '13/10/2020 2:00PM', from: 'Jerry Tom', subject: 'Property Enquiry', message: 'what is the details of the property?'  },
    { date: '10/09/2020 2:00PM', from: 'Andrew Gee', subject: 'Bid Enquiry', message: 'could the bid date be changed to 1/11/2020?'  }
  ];


  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.frameworkComponents = {
      btnCellRenderer: BtnCellRenderer,
    }
  }

  onGridReady(params) {

    /*    this.http
          .get(
            'https://raw.githubusercontent.com/ag-grid/ag-grid/master/grid-packages/ag-grid-docs/src/olympicWinnersSmall.json'
          )
          .subscribe(data => {
            this.rowData = data;
          });*/
    if (this.gridApi) {
      this.gridApi.sizeColumnsToFit();
    }
  }

  getSelectedRows() {
    const selectedNodes = this.agGrid.api.getSelectedNodes();
    const selectedData = selectedNodes.map(node => node.data );
    const selectedDataStringPresentation = selectedData.map(node => node.property + ' ' + node.status).join(', ');
    alert(`Selected nodes: ${selectedDataStringPresentation}`);
  }

  addProperty() {
    this.router.navigate(['/new-property', {}]);
  }
}
