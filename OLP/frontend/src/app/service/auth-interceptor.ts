import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {catchError} from 'rxjs/operators';
import "rxjs-compat/add/operator/finally";
import {throwError} from "rxjs/internal/observable/throwError";
import {NbAuthService} from "@nebular/auth";
import {UserService} from "./user.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  isRefreshingToken = false;

  constructor(private authService: NbAuthService, private userService: UserService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const tokenizedReq = this.userService.token ? req.clone({
      headers: req.headers.set('Authorization', this.userService.token)
    }) : req;

    return next.handle(tokenizedReq).pipe(
      catchError(err => {
        if (err.status === 401) {
          //this.authService.showSessionExpiredDialog();
        }
        return throwError(err);
      })
    );
  }

}
