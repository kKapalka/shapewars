import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: object;
  currentUser:boolean=false;

  constructor(private token: TokenStorageService,private router:Router,
              private activatedRoute: ActivatedRoute,private service: UserService) { }

  ngOnInit() {
    let lastMessageType = localStorage.getItem("LastLogType");
    if(lastMessageType!=="WORKING"){
      window.location.href = "/error";
    }
    this.retrieveUserData();
  }
  retrieveUserData(){
    let userName:string=this.activatedRoute.snapshot.paramMap.get('username');
    if(userName===null || userName===this.token.getUsername()){
      this.currentUser=true;
      userName=this.token.getUsername();
    }
    console.log(userName);
    this.service.getPlayerData(userName).subscribe(res=>{
      this.user=res;
      console.log(res);
    },console.log);
  }
}
