import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  board: string;
  errorMessage: string;
  constructor(private userService: UserService) { }

  ngOnInit() {
    let lastMessageType = localStorage.getItem("LastLogType");
    if(lastMessageType!=="WORKING"){
      window.location.href = "/error";
    }
  }
}
