import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest, HTTP_INTERCEPTORS, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { LoginService } from "./login.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private login:LoginService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        // add the jwt token (localStorage) request
        // let authReq = req;
        const token = this.login.getToken();

        const authReq = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)});

        return next.handle(authReq);
    }   
}