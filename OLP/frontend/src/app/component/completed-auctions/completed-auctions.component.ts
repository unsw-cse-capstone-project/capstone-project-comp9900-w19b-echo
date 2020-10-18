import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {BtnCellRenderer} from "../btn-cell-renderer/btn-cell-renderer.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-completed-auctions',
  templateUrl: './completed-auctions.component.html',
  styleUrls: ['./completed-auctions.component.scss']
})
export class CompletedAuctionsComponent implements OnInit {
  @ViewChild('agGrid') agGrid: AgGridAngular;
  private gridApi;
  private gridColumnApi;

  columnDefs = [
    { field: 'property', sortable: true, filter: true, minWidth: 300},
    { field: 'status' , sortable: true, filter: true, editable:true, maxWidth:100},
    { field: 'bidStartDate', sortable: true, filter: true, editable:true},
    { field: 'bidEndDate', sortable: true, filter: true, editable:true},
    {
      field: 'property',
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
    { property: '1 George Street, Sydney NSW 2000', status: 'Active', bidStartDate: '', bidEndDate: ''},
    { property: '200 Martin Place, Sydney NSW 2000', status: 'Inactive', bidStartDate: '1/9/2020 2:00PM', bidEndDate: '1/9/2020 3:00PM' },
    { property: '666 George Street, Sydney NSW 2000', status: 'Sold', bidStartDate: '15/9/2020 9:00AM', bidEndDate: '5/9/2020 10:00AM'  },
    { property: '111 George Street, Sydney NSW 2000', status: 'Passed In', bidStartDate: '15/10/2020 10:00AM', bidEndDate: '15/10/2020 12:00AM'  }
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
