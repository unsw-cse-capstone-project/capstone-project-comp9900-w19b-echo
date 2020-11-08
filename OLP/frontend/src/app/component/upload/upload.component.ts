import { Component, OnInit } from '@angular/core';
import {NbDialogRef, NbDialogService, NbToastrService} from "@nebular/theme";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {
  isLoading: boolean = false;
  fileToUpload: File = null;
  uploadType: string;
  pid: number = 0;

  constructor(protected dialogRef: NbDialogRef<UploadComponent>, private http: HttpClient, private userService: UserService) { }

  ngOnInit(): void {
  }

  upload() {
    let endpoint = environment.baseEndpoint + '/upload-'+ this.uploadType;
    const formData: FormData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    if(this.pid && this.pid > 0) {
      endpoint = endpoint + '/' + this.pid;
    }
    this.http.post(endpoint, formData, {}).subscribe( data =>
      {
        if(data) {
          this.dialogRef.close(true);
        }
      }
    );
  }

  cancel() {
    this.dialogRef.close(false);
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

}
