import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router } from "@angular/router";
import { StorageService } from "../services/storage/storage.service";

@Injectable({
    providedIn: 'root'
})
export class RoleGuard implements CanActivate {
    constructor(public storageService: StorageService, public router: Router) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {
        const expectedRole = route.data['expectedRole'];
        const user = this.storageService.getUser();

        if (!this.storageService.isLoggedIn() || user?.role !== expectedRole) {
            this.router.navigate(['login']);
            return false;
        }


        return true;
    }
}
