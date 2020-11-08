import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {UserService} from "../../service/user.service";
import {NbDialogService, NbToastrService} from "@nebular/theme";
import {NbComponentStatus} from "@nebular/theme/components/component-status";
import {UploadComponent} from "../upload/upload.component";
import {FileDto} from "../../model/file.model";
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  @Input() user: User;
  isLoading: boolean = false;
  fileToUpload: File = null;
  files: FileDto[] = [];

  constructor(private http: HttpClient, private userService: UserService, private toastrService: NbToastrService, private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getDocuments();
  }

  getProfile() {
    const email = this.userService.user?.email;
    if(email) {
      this.isLoading = true;
      this.http.get(environment.baseEndpoint + '/user?email=' + this.userService.user.email)
        .subscribe((u: User) => {
            this.user = u;
            this.isLoading = false;
          }
        );
    }
  }

  saveProfile() {
    this.isLoading = true;
    this.http.post(environment.baseEndpoint + '/user', this.user)
      .subscribe((u: User) => {
        this.user = u;
        this.showToast('success', `User Profile - Updated`);
        this.isLoading = false;
      }
    );
  }

  showToast(status: NbComponentStatus, title) {
    this.toastrService.show(status, title, { status });
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  upload(){
    this.dialogService.open(UploadComponent,{
      context: {
      },
    }).onClose.subscribe(data => {
      if(data) {
        this.showToast('success', `File - uploaded`);
        this.getDocuments();
      }
    });
  }

  getDocuments() {
    this.http.get(environment.baseEndpoint + '/documents')
      .subscribe((files: FileDto[]) => {
          this.files = files;
        }
      );
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
          this.showToast('success', `Document - Deleted`);
          this.getDocuments();
        },
        error => {
          window.alert('Error delete the file.');
        }
      )
  }
}
