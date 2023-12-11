import { Component, OnInit } from '@angular/core';


import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user/user.service";
import { AuthService } from 'src/app/services/auth/auth.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import {AuthUser} from "../../common/auth.model/auth-user";
import {User} from "../../common/user.model/user";


@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {

  id!: number;
  user!: User;
  userId: number| undefined = this.storageService.getUser().id;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router) { }

  ngOnInit(): void {
    if(this.storageService.getUser().role === "ADMIN"){
      this.router.navigate(['/admin-orders']);
    }
    this.storageService.getUser();
     this.getUser();
  }



  logout() {
    this.authService.logout().subscribe(() => {
      this.storageService.clear();
      this.router.navigate(['']);
    });
  }

  editProfile() {
    this.router.navigate(['profile-edit-user']);
  }

  isLoggedIn() {
    return this.storageService.isLoggedIn();
  }

  getUser(){
    this.userService.getUserById(this.userId).subscribe(data => {
       this.user = data;
    });
  }


}
