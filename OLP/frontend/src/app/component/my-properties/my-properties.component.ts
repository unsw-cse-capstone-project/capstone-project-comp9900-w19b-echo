import {Component, OnInit, ViewChild} from '@angular/core';
import {AgGridAngular} from "ag-grid-angular";
import {BtnCellRenderer} from "../btn-cell-renderer/btn-cell-renderer.component";

@Component({
  selector: 'app-my-properties',
  templateUrl: './my-properties.component.html',
  styleUrls: ['./my-properties.component.scss']
})
export class MyPropertiesComponent implements OnInit {
  @ViewChild('agGrid') agGrid: AgGridAngular;
  private gridApi;
  private gridColumnApi;

    columnDefs = [
      { field: 'property', sortable: true, filter: true, minWidth: 300},
      { field: 'status' , sortable: true, filter: true, editable:true},
      { field: 'bidStartDate', sortable: true, filter: true, editable:true},
      { field: 'bidEndDate', sortable: true, filter: true, editable:true},
      {
        field: 'property',
        headerName: 'Action',
        cellRenderer: 'btnCellRenderer',
        cellRendererParams: {
          clicked: function (field: any) {
            alert(`${field} was clicked`);
          }
        }
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


  constructor() {
    this.frameworkComponents = {
      btnCellRenderer: BtnCellRenderer,
    }
  }

  ngOnInit(): void {
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
}
