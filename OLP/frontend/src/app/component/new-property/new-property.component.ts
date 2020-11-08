import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Property} from "../../model/property.model";
import {environment} from "../../../environments/environment";
import {NbComponentStatus} from "@nebular/theme/components/component-status";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {NbDialogService, NbToastrService} from "@nebular/theme";
import {UploadComponent} from "../upload/upload.component";
import {FileDto} from "../../model/file.model";
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-new-property',
  templateUrl: './new-property.component.html',
  styleUrls: ['./new-property.component.scss']
})
export class NewPropertyComponent implements OnInit {
  property: Property;
  isLoading: boolean = false;
  fileToUpload: File = null;
  documents: FileDto[] = [];
  photos: FileDto[] = [];

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient, private userService: UserService,
              private toastrService: NbToastrService, private dialogService: NbDialogService) { }

  ngOnInit(): void {
    const pid = this.route.snapshot.paramMap.get('pid');
    if(pid){
      this.property = this.userService.currentProperty;
      this.getDocuments();
      this.getPhotos();
    }else {
      this.property = new Property();
      this.property.propertyType = 0;
    }
  }

  getProperty(pid) {

  }

  save() {
    this.isLoading = true;
    this.property.owner = this.userService.user.uid;
    this.property.city = this.property.suburb;
    let uri = this.property.pid ? '/update-property' : '/add-property';
    let title = this.property.pid ? 'Property - Updated' : 'New property - Added';

    this.http.post(environment.baseEndpoint + uri, {property: this.property})
      .subscribe((p: Property) => {
          this.property = p;
          setTimeout(() => {
            this.showToast('success', title);
          }, 1000);
          this.isLoading = false;
          this.router.navigate(['/my-properties', {}]);
        }
      );
  }

  showToast(status: NbComponentStatus, title: string) {
    this.toastrService.show(status, title, { status });
  }

  cancel() {
    this.router.navigate(['/my-properties', {}]);
  }

  onPropertyTypeChange(propertyType: number) {
    this.property.propertyType = propertyType;
  }

  upload(){
    this.dialogService.open(UploadComponent,{
      context: {
        uploadType: 'property-document',
        pid: this.property.pid,
      },
    }).onClose.subscribe(data => {
      if(data) {
        this.showToast('success', `File - uploaded`);
        this.getDocuments();
      }
    });
  }

  download(file: FileDto) {
    this.http.post(environment.baseEndpoint + '/document', file, {responseType: 'blob'})
      .subscribe(
        res => {
          this.saveFile(res, file.fileName);
        },
        error => {
          window.alert('Error downloading the file.');
        }
      )
  }

  saveFile(res: any, fileName:string) {
    const blob = new Blob([res], { type: 'application/octet-stream' });
    saveAs(blob, fileName);
  }

  delete(file: FileDto) {
    this.http.post(environment.baseEndpoint + '/delete-document', file)
      .subscribe(
        res => {
          this.showToast('success', `File - Deleted`);
          this.getDocuments();
          this.getPhotos();
        },
        error => {
          window.alert('Error delete the file.');
        }
      )
  }

  getDocuments() {
    if(this.property?.pid) {
      this.http.get(environment.baseEndpoint + '/property-document/' + this.property.pid)
        .subscribe((files: FileDto[]) => {
            this.documents = files;
          }
        );
    }
  }

  getPhotos() {
    if(this.property?.pid) {
      this.http.get(environment.baseEndpoint + '/property-photo/' + this.property.pid)
        .subscribe((files: FileDto[]) => {
            this.photos = files;
          }
        );
    }
  }

  uploadPhoto() {
    this.dialogService.open(UploadComponent,{
      context: {
        uploadType: 'property-photo',
        pid: this.property.pid,
      },
    }).onClose.subscribe(data => {
      if(data) {
        this.showToast('success', `Photo - uploaded`);
        this.getPhotos();
      }
    });
  }
}
