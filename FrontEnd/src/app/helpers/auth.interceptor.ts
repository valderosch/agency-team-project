import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { StorageService } from "../services/storage/storage.service";
import { Observable, catchError, throwError } from "rxjs";
import { AuthService } from "../services/auth/auth.service";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {
    constructor(
        private storageService: StorageService,
        private authService: AuthService,
        private router: Router
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const jwt = this.storageService.getToken();
        if (jwt != null) {
            req = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + jwt)});
        }

        return next.handle(req).pipe(
            catchError((error) => {
                let errorMessage = "other error";
                if (error instanceof HttpErrorResponse) {
                    if (error.error instanceof ErrorEvent) {
                        errorMessage = `client side error: ${error.error.message}`;
                    }
                    else {
                        errorMessage = `server error ${error.error.status}\n${error.error.message}`;
                        switch(error.status) {
                            case 401:
                                if (!req.url.includes('auth/authenticate')) {
                                    this.authService.logout().subscribe({
                                        next: () => {
                                            this.storageService.clear();
                                        },
                                        error: err => {
                                            console.error(err);
                                        }
                                    });
                                }
                                break;
                            case 403:
                                window.alert("У вас немає прав на здійснення цієї операції");
                                this.router.navigate(['']);
                                break;
                            case 404:
                                this.router.navigate(['']);
                                break;
                        }
                    }
                }
                console.error(errorMessage);
                return throwError(() => error);
            })
        );
    }
}
