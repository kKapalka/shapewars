import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-fight-window',
  templateUrl: './fight-window.component.html',
  styleUrls: ['./fight-window.component.css']
})
export class FightWindowComponent implements OnInit {

  you:any;
  opponent:any;

  constructor(private service:UserService, private token:TokenStorageService) { }

  ngOnInit() {
    console.log(this.token.getUsername());
    this.service.getPlayerData(this.token.getUsername()).subscribe(res=>{
      this.you=res;
      this.service.getCombatantsByUser(this.token.getUsername()).subscribe(res=>{
        this.you.allFighterList=res;
      });
    });
    this.service.getFightsByUser(this.token.getUsername()).subscribe(res=>{
      let currentFight=res.find(fight=>fight.fightStatus==='IN_PROGRESS');
      let opponentName:string="";
      if(currentFight.playerOne===this.token.getUsername()){
        opponentName=currentFight.playerTwo;
      } else{
        opponentName=currentFight.playerOne;
      }
      this.service.getPlayerData(opponentName).subscribe(res=>{
        this.opponent=res;
        this.service.getCombatantsByUser(opponentName).subscribe(res=>{
          this.opponent.allFighterList=res;
          console.log(this.you);
          console.log(this.opponent);
        });
      });
    })
  }

}
