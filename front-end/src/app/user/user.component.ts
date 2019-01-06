import {Component, OnDestroy, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessagesService} from "../services/messages.service";
import Message from "../dtos/message";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  user: object;
  profileUsername:string;
  messageToSend:string;
  currentUser:boolean=false;
  messages:any=[];
  interval:any;
  fighters:any=[];
  inventoryFighters:any=[];
  constructor(private token: TokenStorageService,private router:Router,
              private activatedRoute: ActivatedRoute,private service: UserService,
              private messageService:MessagesService) {
    this.retrieveUserData();
  }

  ngOnInit() {
    let lastMessageType = sessionStorage.getItem("LastLogType");
    if(lastMessageType && lastMessageType!=="WORKING"){
      window.location.href = "/error";
    }
    if(!this.checkIfThisPlayerProfile()) {
      this.interval = setInterval(() => {
        this.messageService.getMessagesByCallers([this.token.getUsername(), this.profileUsername])
          .subscribe(res => {
            if(this.messages!==res){
              this.messages=res;
            }
          })
      }, 1500);
    } else{
      this.service.getFightersByUser(this.token.getUsername()).subscribe(res=>{
        this.fighters=res;
        this.inventoryFighters=res.filter(fighter=>fighter.slot==="INVENTORY");
        console.log(res);
      })
    }
  }
  checkIfThisPlayerProfile():boolean{
    return this.profileUsername===null || this.profileUsername===this.token.getUsername()
}
  retrieveUserData(){
    this.profileUsername=this.activatedRoute.snapshot.paramMap.get('username');
    if(this.checkIfThisPlayerProfile()){
      this.currentUser=true;
      this.profileUsername=this.token.getUsername();
    }
    this.service.getPlayerData(this.profileUsername).subscribe(res=>{
      this.user=res;
    },console.log);
  }
  sendMessage(){
    let message:Message={
      sender:this.token.getUsername(),
      receiver:this.profileUsername,
      message:this.messageToSend
    };
    this.messageService.sendMessage(message).subscribe(console.log,console.log);
  }
  ngOnDestroy(): void {
    clearInterval(this.interval);
  }
  challenge():any{
    let challenge={
      playerOne:this.token.getUsername(),
      playerTwo:this.profileUsername,
      fightStatus:"INVITE_PENDING"
    };
    this.messageService.challenge(challenge).subscribe(res=>{
      console.log(res);
    },console.log)
  }
}
