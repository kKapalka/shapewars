import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {
  @Input()
  info;
  searchUsername:string;
  friends:any;
  changelog:any;
  challengerNames:any;
  constructor(private service:UserService, private router:Router) { }

  ngOnInit() {
    this.service.findFriendsByUsername(this.info.username).subscribe(res=>{
      this.friends=res;
    })
    this.service.getChangelog().subscribe((res=>{
      this.changelog=res;
    }));
    this.service.getChallengesForUser(this.info.username).subscribe(res=>{
      this.challengerNames=[].concat.apply([],
        res.map(challenge=>challenge.playerNames));
    })
  }
  search(event){
    event.preventDefault();
    this.service.getPlayerData(this.searchUsername).subscribe(()=>{
      this.router.navigate(["user",this.searchUsername]);
    },console.log);
  }
  checkForChallenge(login:string):string{
    if(this.challengerNames.includes(login)){
      return "[CHALLENGE]";
    }
    return "";
  }
  initiateBotFight(){
    this.service.generateBotFight(this.info.username).subscribe(res=>{
      console.log(res);
    })
  }
}
