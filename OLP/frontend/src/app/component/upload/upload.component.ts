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

  constructor(protected dialogRef: NbDialogRef<UploadComponent>, private http: HttpClient, private userService: UserService) { }

  ngOnInit(): void {
  }

  upload() {
    const endpoint = environment.baseEndpoint + '/upload-document';
    const formData: FormData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
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
