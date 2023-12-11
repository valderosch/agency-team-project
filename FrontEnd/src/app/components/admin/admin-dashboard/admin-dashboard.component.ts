import { Component, OnInit } from '@angular/core';
import {StorageService} from "../../../services/storage/storage.service";
import {AuthService} from "../../../services/auth/auth.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  constructor(private storageService: StorageService,
              private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }

  isLoggedIn() {
    return this.storageService.isLoggedIn();
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.storageService.clear();
      this.router.navigate(['']);
    });
  }

}
