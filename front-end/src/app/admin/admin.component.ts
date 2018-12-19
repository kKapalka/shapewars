import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {ActivatedRoute} from "@angular/router";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  info: any;
  board: string;
  errorMessage: string;
  constructor(private userService: UserService, private token: TokenStorageService) { }

  ngOnInit() {
    if(!this.token.getAuthorities().includes("ROLE_ADMIN")){
      window.location.href = "/";
    }
  }
}
