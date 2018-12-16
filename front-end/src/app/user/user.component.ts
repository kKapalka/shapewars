import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  board: object;
  errorMessage: string;

  constructor(private userService: UserService, private tokenService: TokenStorageService) { }

  ngOnInit() {
    let lastMessageType = localStorage.getItem("LastLogType");
    if(lastMessageType!=="WORKING"){
      window.location.href = "/error";
    }
    this.userService.getPlayerData(this.tokenService.getUsername()).subscribe(
      data => {
        console.log(data);
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
  }
}
