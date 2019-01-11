import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {FightService} from "../services/fight.service";

@Component({
  selector: 'app-fight-window',
  templateUrl: './fight-window.component.html',
  styleUrls: ['./fight-window.component.css']
})
export class FightWindowComponent implements OnInit {

  you:any;
  opponent:any;
  currentFight:any;
  actionList:any[]=[];
  lastActionId:any;
  turn:any;
  interval:any;
  constructor(private service:UserService, private token:TokenStorageService,
              private fightService:FightService) { }

  ngOnInit() {
    this.turn=0;
    this.service.getPlayerData(this.token.getUsername()).subscribe(res=>{
      this.you=res;
      this.fightService.getCombatantsByUser(this.token.getUsername()).subscribe(res=>{
        this.you.allFighterList=res;
      });
    });
    this.service.getFightsByUser(this.token.getUsername()).subscribe(res=>{
      this.currentFight=res.find(fight=>fight.fightStatus==='IN_PROGRESS');
      let opponentName:string="";
      if(this.currentFight.playerOne===this.token.getUsername()){
        opponentName=this.currentFight.playerTwo;
      } else{
        opponentName=this.currentFight.playerOne;
      }
      this.service.getPlayerData(opponentName).subscribe(res=>{
        this.opponent=res;
        this.fightService.getCombatantsByUser(opponentName).subscribe(res=>{
          this.opponent.allFighterList=res;
          console.log(this.you);
          console.log(this.opponent);
          this.interval=setInterval(()=>{
            this.fightService.getActionListForFight(this.currentFight.id).subscribe(res=>{
              this.actionList=res;
              if(this.actionList.length==0 || this.actionList[this.actionList.length-1].nextActiveFighterId===0){
                let fighterSpeeds=this.you.allFighterList.map(fighter=>({
                  fighterId:fighter.id,
                    speed:fighter.speed
                }));
                fighterSpeeds=fighterSpeeds.merge(this.opponent.allFighterList.map(fighter=>({
                  fighterId:fighter.id,
                  speed:fighter.speed
                })));
                let fightCombatDto={
                  fightId:this.currentFight.id,
                  fighterSpeeds:fighterSpeeds
                };
                this.fightService.getTurnOrderForFightAndTurn(fightCombatDto,Math.floor(this.actionList.length)+1).subscribe(res=>{
                  console.log(res);
                })
              }
            })
          },3000);
        });
      });
    })
  }

}
